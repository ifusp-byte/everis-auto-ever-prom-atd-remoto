package ondemand.tasklet;

import static ondemand.constants.ProductConstants.FILE_EXTENSION_XLS;
import static ondemand.constants.ProductConstants.FILE_EXTENSION_XLSX;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_FILE_HEADER_VALID;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_FILE_HEADER_VIOLATIONS;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_FILE_RESOURCE;
import ondemand.dto.ProductDto;
import ondemand.dto.ViolationDto;
import ondemand.validation.product.service.ProductValidatorService;
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
public class ProductFileHeaderValidationTasklet implements Tasklet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ProductValidatorService productValidatorService;

    public ProductFileHeaderValidationTasklet(@Lazy ProductValidatorService productValidatorService) {
        this.productValidatorService = productValidatorService;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String absolutePathResource = String.valueOf(chunkContext.getStepContext().getJobExecutionContext().get(CONTEXT_PRODUCT_FILE_RESOURCE));
        HashMap<String, String> allProductCellValues = new HashMap<>();
        int lastColmNum = ZERO_NUMBER;

        if (absolutePathResource.endsWith(FILE_EXTENSION_XLS)) {
            HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(new File(absolutePathResource));
            HSSFSheet sheet = workbook.getSheetAt(ZERO_NUMBER);
            HSSFRow row = sheet.getRow(ZERO_NUMBER);
            lastColmNum = row.getLastCellNum();
            mapAllProductCellValues(lastColmNum, allProductCellValues, row, null);
        }

        if (absolutePathResource.endsWith(FILE_EXTENSION_XLSX)) {
            XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(new File(absolutePathResource));
            XSSFSheet sheet = workbook.getSheetAt(ZERO_NUMBER);
            XSSFRow row = sheet.getRow(ZERO_NUMBER);
            lastColmNum = row.getLastCellNum();
            mapAllProductCellValues(lastColmNum, allProductCellValues, null, row);
        }

        if (lastColmNum != SIX) {
            chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_PRODUCT_FILE_HEADER_VALID, true);
            chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_PRODUCT_FILE_HEADER_VIOLATIONS, StringUtils.EMPTY);
            return RepeatStatus.FINISHED;
        }

        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        ProductDto productDto = mapper.convertValue(allProductCellValues, ProductDto.class);

        List<ViolationDto> violations = productValidatorService.validateHeader(productDto);
        boolean violationStatus = false;
        violationStatus = violations.stream().anyMatch(violation -> Boolean.TRUE.equals(violation.getViolationStatus()));
        String headerViolations = null;

        try {
            headerViolations = mapper.writeValueAsString(violations);
        } catch (JsonProcessingException e) {
            throw new BusinessException(e);
        }

        chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_PRODUCT_FILE_HEADER_VALID, violationStatus);
        chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_PRODUCT_FILE_HEADER_VIOLATIONS, headerViolations);
        return RepeatStatus.FINISHED;
    }

    private void mapAllProductCellValues(int lastColmNum, HashMap<String, String> allProductCellValues, HSSFRow hssfRow, XSSFRow xssfRow) {
        AtomicInteger colm = new AtomicInteger(ZERO_NUMBER);

        if (hssfRow != null) {
            for (int colmNum = ZERO_NUMBER; colmNum < lastColmNum; colmNum++) {
                allProductCellValues.put(ProductDto.class.getDeclaredFields()[colmNum].getName(), hssfRow.getCell(colm.getAndIncrement()).getStringCellValue());
            }
        }

        if (xssfRow != null) {
            for (int colmNum = ZERO_NUMBER; colmNum < lastColmNum; colmNum++) {
                allProductCellValues.put(ProductDto.class.getDeclaredFields()[colmNum].getName(), xssfRow.getCell(colm.getAndIncrement()).getStringCellValue());
            }
        }
    }
}
