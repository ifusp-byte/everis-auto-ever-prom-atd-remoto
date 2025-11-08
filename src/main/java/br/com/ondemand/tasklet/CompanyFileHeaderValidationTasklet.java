package br.com.ondemand.tasklet;

import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_HEADER_VALID;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_HEADER_VIOLATIONS;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_RESOURCE;
import static ondemand.constants.CompanyConstants.FILE_EXTENSION_XLS;
import static ondemand.constants.CompanyConstants.FILE_EXTENSION_XLSX;
import ondemand.dto.CompanyDto;
import ondemand.dto.ViolationDto;
import ondemand.validation.company.service.CompanyValidatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CompanyFileHeaderValidationTasklet implements Tasklet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final CompanyValidatorService companyValidatorService;

    public CompanyFileHeaderValidationTasklet(@Lazy CompanyValidatorService companyValidatorService) {
        this.companyValidatorService = companyValidatorService;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String absolutePathResource = String.valueOf(chunkContext.getStepContext().getJobExecutionContext().get(CONTEXT_COMPANY_FILE_RESOURCE));
        HashMap<String, String> allCompanyCellValues = new HashMap<>();
        int lastColmNum = ZERO_NUMBER;

        if (absolutePathResource.endsWith(FILE_EXTENSION_XLS)) {
            HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(new File(absolutePathResource));
            HSSFSheet sheet = workbook.getSheetAt(ZERO_NUMBER);
            HSSFRow row = sheet.getRow(ROW_NUMBER);
            lastColmNum = row.getLastCellNum();
            mapAllCompanyCellValues(lastColmNum, allCompanyCellValues, row, null);
        }

        if (absolutePathResource.endsWith(FILE_EXTENSION_XLSX)) {
            XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(new File(absolutePathResource));
            XSSFSheet sheet = workbook.getSheetAt(ZERO_NUMBER);
            XSSFRow row = sheet.getRow(ROW_NUMBER);
            lastColmNum = row.getLastCellNum();
            mapAllCompanyCellValues(lastColmNum, allCompanyCellValues, null, row);
        }

        if (lastColmNum != TWENTY_EIGHT) {
            chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_COMPANY_FILE_HEADER_VALID, true);
            chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_COMPANY_FILE_HEADER_VIOLATIONS, StringUtils.EMPTY);
            return RepeatStatus.FINISHED;
        }

        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        CompanyDto companyDto = mapper.convertValue(allCompanyCellValues, CompanyDto.class);

        List<ViolationDto> violations = companyValidatorService.validateHeader(companyDto);

        boolean violationStatus = false;
        violationStatus = violations.stream().anyMatch(violation -> Boolean.TRUE.equals(violation.getViolationStatus()));

        String headerViolations = null;

        try {
            headerViolations = mapper.writeValueAsString(violations);
        } catch (JsonProcessingException e) {
            throw new BusinessException(e);
        }

        chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_COMPANY_FILE_HEADER_VALID, violationStatus);
        chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_COMPANY_FILE_HEADER_VIOLATIONS, headerViolations);
        return RepeatStatus.FINISHED;
    }

    private void mapAllCompanyCellValues(int lastColmNum, HashMap<String, String> allCompanyCellValues, HSSFRow hssfRow, XSSFRow xssfRow) {
        AtomicInteger colm = new AtomicInteger(ZERO_NUMBER);

        if (hssfRow != null) {
            for (int colmNum = ZERO_NUMBER; colmNum < lastColmNum - TWO; colmNum++) {
                allCompanyCellValues.put(CompanyDto.class.getDeclaredFields()[colmNum + ONE].getName(), hssfRow.getCell(colm.incrementAndGet()).getStringCellValue());
            }
        }

        if (xssfRow != null) {
            for (int colmNum = ZERO_NUMBER; colmNum < lastColmNum - TWO; colmNum++) {
                allCompanyCellValues.put(CompanyDto.class.getDeclaredFields()[colmNum + ONE].getName(), xssfRow.getCell(colm.incrementAndGet()).getStringCellValue());
            }
        }
    }
}
