package br.com.ondemand.service.impl;

import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_DATE;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_RESOURCE;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_ID_FILE;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_JOB_SUCCESS_STATUS;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.ondemand.constants.CompanyConstants;
import br.com.ondemand.dto.CompanyDto;
import br.com.ondemand.dto.CompanyItemDto;
import br.com.ondemand.dto.SavedItemDto;
import br.com.ondemand.dto.ViolationDto;
import br.com.ondemand.service.CompanyImportService;
import br.com.ondemand.validation.company.service.CompanyValidatorService;
import bsh.util.Util;

@Service
public class CompanyImportServiceImpl implements CompanyImportService {
	private static final Logger LOG = LoggerFactory.getLogger(CompanyImportServiceImpl.class);
	private final CompanyValidatorService companyValidatorService;
	private final FileImportAuditService fileImportAuditService;
	private final CompanyRepository companyRepository;
	private List<CompanyDto> allCompanyItems;
	private List<CompanyItemDto> violationsAll;
	private List<SavedItemDto> allSavedItems;
	private final MessageSource messageSource;
	private final JobLauncher companyJobLauncher;

	@Qualifier("companyJob")
	private final Job companyJob;

	public CompanyImportServiceImpl(JobLauncher companyJobLauncher, @Lazy Job companyJob,
			@Lazy CompanyValidatorService companyValidatorService, CompanyRepository companyRepository,
			MessageSource messageSource, FileImportAuditService fileImportAuditService) {
		this.companyValidatorService = companyValidatorService;
		this.companyRepository = companyRepository;
		this.companyJobLauncher = companyJobLauncher;
		this.companyJob = companyJob;
		this.messageSource = messageSource;
		this.fileImportAuditService = fileImportAuditService;
	}

	@Override
	public Boolean companyJob(String userEmail, String userPersistenceUnit, String urlFileAmazon, Long idFileAmazon) {

		boolean statusReturn = false;

		try {
			URL url = new URL(urlFileAmazon);
			File fileExcelFromS3 = new File(url.getFile());
			File fileExcelToRead = new File(CompanyConstants.FILE_EXCEL_TO_READ_PATH + fileExcelFromS3.getName());
			FileUtils.copyURLToFile(url, fileExcelToRead);
			JobParameters jobParameters = new JobParametersBuilder()
					.addString(CONTEXT_COMPANY_FILE_RESOURCE, fileExcelToRead.getAbsolutePath())
					.addDate(CONTEXT_COMPANY_DATE, new Date()).addLong(CONTEXT_COMPANY_ID_FILE, idFileAmazon)
					.toJobParameters();
			var job = companyJobLauncher.run(companyJob, jobParameters);
			statusReturn = Boolean
					.parseBoolean(String.valueOf(job.getExecutionContext().get(CONTEXT_COMPANY_JOB_SUCCESS_STATUS)));

			boolean statusDelete = fileExcelToRead.delete();
			LOG.info(Util.getMessageSource(CompanyConstants.DELETED_FILE, messageSource) + statusDelete);

			if (statusReturn) {
				validateCompanyItems(userPersistenceUnit, idFileAmazon);
			}

		} catch (IOException | JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
			throw new BusinessException(e);
		}
		return statusReturn;
	}

	@Override
	public void allCompanyItems(List<CompanyDto> companyList) {
		allCompanyItems = new ArrayList<>();
		allCompanyItems = companyList;
	}

	private void validateCompanyItems(String userPersistenceUnit, Long idFileAmazon) {

		AtomicInteger lineNumber = new AtomicInteger(FIVE);
		violationsAll = new ArrayList<>();
		allSavedItems = new ArrayList<>();

		if (!allCompanyItems.isEmpty()) {
			allCompanyItems.stream().forEach(item -> {
				item.setUserUnitPersistence(userPersistenceUnit);
				List<ViolationDto> violations = companyValidatorService.validate(item);

				CompanyItemDto companyItemDto = new CompanyItemDto();
				companyItemDto.setViolations(violations);
				companyItemDto.setLineNumber(lineNumber.incrementAndGet());

				if (violations.isEmpty()) {
					saveCompanyItems(item, idFileAmazon, allSavedItems);
				}
				if (!violations.isEmpty()) {
					violationsAll.add(companyItemDto);
				}
			});
		}
		if (allCompanyItems.isEmpty()) {
			fileImportAuditService.jobExecutionContextContinuousAudit(idFileAmazon, FileImportStatusEnum.FAILED,
					Util.getMessageSource(CompanyConstants.PROCCESS_COMPANY_IMPORT_SUCCESS, messageSource)
							+ writeValueAsString(allSavedItems),
					LocalDateTime.now());
		}
		if (!violationsAll.isEmpty()) {
			fileImportAuditService.jobExecutionContextContinuousAudit(idFileAmazon, FileImportStatusEnum.FAILED,
					Util.getMessageSource(CompanyConstants.VIOLATION_MESSAGE_FILE_BODY, messageSource)
							+ writeValueAsString(violationsAll)
							+ Util.getMessageSource(CompanyConstants.PROCCESS_COMPANY_IMPORT_SUCCESS, messageSource)
							+ writeValueAsString(allSavedItems),
					LocalDateTime.now());
		}
	}

	private void saveCompanyItems(CompanyDto item, Long idFileAmazon, List<SavedItemDto> allSavedItems) {

		Company empresa = new Company();

		empresa.setCnpj(StringUtils.isNotBlank(item.getCnpj()) ? item.getCnpj() : item.getCpf());
		empresa.setRazaoSocial(item.getRazaoSocial());
		empresa.setNomeFantasia(item.getFantasia());
		empresa.setIe(item.getInscricaoEstadual());
		empresa.setIm(item.getInscricaoMunicipal());
		empresa.setClienteFinal(YES.equalsIgnoreCase(item.getIsClienteFinal()));
		empresa.setEmail(item.getEmail());
		empresa.setTelefone1Tipo(TipoTelefoneEnum.CELULAR);
		empresa.setDd1(item.getDddCelular());
		empresa.setTelefone1(item.getTelefoneCelular().trim());
		empresa.setTelefone2Tipo(TipoTelefoneEnum.COMERCIAL);
		empresa.setDd2(item.getDddComercial());
		empresa.setTelefone2(item.getTelefoneComercial().trim());
		empresa.setEndereco(item.getEndereco());
		empresa.setNumero(item.getNumero());
		empresa.setComplemento(item.getComplemento());
		empresa.setBairro(item.getBairro());
		empresa.setCidade(item.getCidade());
		empresa.setUf(item.getSiglaEstado());
		empresa.setPais(COUNTRY);
		empresa.setCep(item.getCep());
		empresa.setObservacoes(item.getObservacoes());
		empresa.setTipoPessoa(StringUtils.isNotBlank(item.getCnpj()) ? PessoaTipoEnum.JURIDICA : PessoaTipoEnum.FISICA);
		empresa.setUnidadePersistencia(Long.valueOf(item.getUserUnitPersistence()));

		empresa = companyRepository.save(empresa);

		SavedItemDto savedItemDto = new SavedItemDto();
		savedItemDto.setId(String.valueOf(empresa.getId()));
		allSavedItems.add(savedItemDto);

		fileImportAuditService.jobExecutionContextContinuousAudit(idFileAmazon, FileImportStatusEnum.COMPLETED,
				Util.getMessageSource(CompanyConstants.PROCCESS_COMPANY_IMPORT_SUCCESS, messageSource)
						+ writeValueAsString(allSavedItems),
				LocalDateTime.now());
	}
}
