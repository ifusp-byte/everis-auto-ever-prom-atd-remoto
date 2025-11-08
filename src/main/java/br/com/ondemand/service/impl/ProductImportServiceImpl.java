package br.com.ondemand.service.impl;

import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_DATE;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_FILE_RESOURCE;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_ID_FILE;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_JOB_SUCCESS_STATUS;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.summary.Product;
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

import br.com.ondemand.constants.ProductConstants;
import br.com.ondemand.dto.ProductDto;
import br.com.ondemand.dto.ProductItemDto;
import br.com.ondemand.dto.SavedItemDto;
import br.com.ondemand.dto.ViolationDto;
import br.com.ondemand.service.ProductImportService;
import br.com.ondemand.validation.product.service.ProductValidatorService;
import bsh.util.Util;

@Service
public class ProductImportServiceImpl implements ProductImportService {
	private static final Logger LOG = LoggerFactory.getLogger(ProductImportServiceImpl.class);
	private final ProductValidatorService productValidatorService;
	private final FileImportAuditService fileImportAuditService;
	private final ProductRepository productRepository;
	private final ProdutoTipoRepository produtoTipoRepository;
	private final NcmRepository ncmRepository;
	private final MeasurementUnitRepository measurementUnitRepository;
	private final PriceTableRepository priceTableRepository;
	private final PriceTableItemRepository priceTableItemRepository;
	private List<ProductDto> allProductItems;
	private List<ProductItemDto> violationsAll;
	private List<SavedItemDto> allSavedItems;
	private final MessageSource messageSource;
	private final JobLauncher productJobLauncher;

	@Qualifier("productJob")
	private final Job productJob;

	public ProductImportServiceImpl(JobLauncher productJobLauncher, @Lazy Job productJob,
			@Lazy ProductValidatorService productValidatorService, ProductRepository productRepository,
			MessageSource messageSource, ProdutoTipoRepository produtoTipoRepository, NcmRepository ncmRepository,
			MeasurementUnitRepository measurementUnitRepository, PriceTableRepository priceTableRepository,
			PriceTableItemRepository priceTableItemRepository, FileImportAuditService fileImportAuditService) {
		this.productValidatorService = productValidatorService;
		this.productRepository = productRepository;
		this.productJobLauncher = productJobLauncher;
		this.productJob = productJob;
		this.messageSource = messageSource;
		this.produtoTipoRepository = produtoTipoRepository;
		this.ncmRepository = ncmRepository;
		this.measurementUnitRepository = measurementUnitRepository;
		this.priceTableRepository = priceTableRepository;
		this.priceTableItemRepository = priceTableItemRepository;
		this.fileImportAuditService = fileImportAuditService;
	}

	@Override
	public Boolean productJob(String userEmail, String userPersistenceUnit, String urlFileAmazon, Long idFileAmazon) {

		boolean statusReturn = false;

		try {
			URL url = new URL(urlFileAmazon);
			File fileExcelFromS3 = new File(url.getFile());
			File fileExcelToRead = new File(ProductConstants.FILE_EXCEL_TO_READ_PATH + fileExcelFromS3.getName());
			FileUtils.copyURLToFile(url, fileExcelToRead);
			JobParameters jobParameters = new JobParametersBuilder()
					.addString(CONTEXT_PRODUCT_FILE_RESOURCE, fileExcelToRead.getAbsolutePath())
					.addDate(CONTEXT_PRODUCT_DATE, new Date()).addLong(CONTEXT_PRODUCT_ID_FILE, idFileAmazon)
					.toJobParameters();
			var job = productJobLauncher.run(productJob, jobParameters);
			statusReturn = Boolean
					.parseBoolean(String.valueOf(job.getExecutionContext().get(CONTEXT_PRODUCT_JOB_SUCCESS_STATUS)));

			boolean statusDelete = fileExcelToRead.delete();
			LOG.info(Util.getMessageSource(ProductConstants.DELETED_FILE, messageSource) + statusDelete);

			if (statusReturn) {
				validateProductItems(userPersistenceUnit, idFileAmazon);
			}

		} catch (IOException | JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
			throw new BusinessException(e);
		}
		return statusReturn;
	}

	public void allProductItems(List<ProductDto> productList) {
		allProductItems = new ArrayList<>();
		allProductItems = productList;
	}

	private void validateProductItems(String userPersistenceUnit, Long idFileAmazon) {

		AtomicInteger lineNumber = new AtomicInteger(ONE);
		violationsAll = new ArrayList<>();
		allSavedItems = new ArrayList<>();

		if (!allProductItems.isEmpty()) {
			allProductItems.stream().forEach(item -> {
				item.setUserUnitPersistence(userPersistenceUnit);
				List<ViolationDto> violations = productValidatorService.validate(item);

				ProductItemDto productItemDto = new ProductItemDto();
				productItemDto.setViolations(violations);
				productItemDto.setLineNumber(lineNumber.incrementAndGet());

				if (violations.isEmpty()) {
					saveProductItems(item, idFileAmazon, allSavedItems);
				}

				if (!violations.isEmpty()) {
					violationsAll.add(productItemDto);
				}
			});
		}
		if (allProductItems.isEmpty()) {
			fileImportAuditService.jobExecutionContextContinuousAudit(idFileAmazon, FileImportStatusEnum.FAILED,
					Util.getMessageSource(ProductConstants.PROCCESS_PRODUCT_IMPORT_SUCCESS, messageSource)
							+ writeValueAsString(allSavedItems),
					LocalDateTime.now());
		}
		if (!violationsAll.isEmpty()) {
			fileImportAuditService.jobExecutionContextContinuousAudit(idFileAmazon, FileImportStatusEnum.FAILED,
					Util.getMessageSource(ProductConstants.VIOLATION_MESSAGE_FILE_BODY, messageSource)
							+ writeValueAsString(violationsAll)
							+ Util.getMessageSource(ProductConstants.PROCCESS_PRODUCT_IMPORT_SUCCESS, messageSource)
							+ writeValueAsString(allSavedItems),
					LocalDateTime.now());
		}
	}

	private void saveProductItems(ProductDto item, Long idFileAmazon, List<SavedItemDto> allSavedItems) {

		Product product = new Product();
		product.setSelecionar(false);
		product.setBloqueado(false);
		product.setAtivo(true);
		product.setUnidadePersistencia(Long.parseLong(item.getUserUnitPersistence()));
		product.setDescricao(item.getDescricao());
		Optional.ofNullable(item.getCodInterno()).ifPresent(product::setCodigoInterno);
		Optional.ofNullable(item.getCodBarras()).ifPresent(product::setCodigoBarra);
		produtoTipoRepository.findById((long) ONE).ifPresent(product::setProdutoTipo);

		if (StringUtils.isNotBlank(item.getNcm())) {
			ncmRepository.findFirstByCodigo(item.getNcm()).map(Ncm::getId).ifPresent(product::setIdNcm);
		}

		if (StringUtils.isNotBlank(item.getUnidade())) {
			measurementUnitRepository
					.findFirstByUnidadePersistenciaAndUnidade(Long.parseLong(item.getUserUnitPersistence()),
							item.getUnidade())
					.ifPresent(product::setUnidEntrada);
		}

		Double unitPrice1 = StringUtils.isNotBlank(item.getPrecoUnitario())
				? Double.parseDouble(Objects.requireNonNull(item.getPrecoUnitario()))
				: null;
		BigDecimal unitPrice2 = unitPrice1 != null ? BigDecimalUtil.newBigDecimal(unitPrice1) : null;
		Optional.ofNullable(unitPrice1 != null ? BigDecimalUtil.newBigDecimal(unitPrice1) : null)
				.ifPresent(product::setPrecoMaxCompra);

		product = productRepository.save(product);

		Optional<List<PriceTable>> priceTable = priceTableRepository
				.findByDescriptionAndPersistenceUnit(Long.parseLong(item.getUserUnitPersistence()));

		if (priceTable.isPresent()) {
			PriceTableItem priceTableItem = new PriceTableItem();
			priceTableItem.setUnidadePersistencia(Long.parseLong(item.getUserUnitPersistence()));
			priceTableItem.setProduto(product);
			priceTableItem.setTabelaPreco(priceTable.get().get(ZERO_NUMBER));
			Optional.ofNullable(unitPrice2).ifPresent(priceTableItem::setPrecoVenda);

			priceTableItemRepository.save(priceTableItem);
		}

		SavedItemDto savedItemDto = new SavedItemDto();
		savedItemDto.setId(String.valueOf(product.getId()));
		allSavedItems.add(savedItemDto);

		fileImportAuditService.jobExecutionContextContinuousAudit(idFileAmazon, FileImportStatusEnum.COMPLETED,
				Util.getMessageSource(ProductConstants.PROCCESS_PRODUCT_IMPORT_SUCCESS, messageSource)
						+ writeValueAsString(allSavedItems),
				LocalDateTime.now());
	}
}
