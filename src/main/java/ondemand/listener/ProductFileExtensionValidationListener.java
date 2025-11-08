package ondemand.listener;

import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_FILE_EXTENSION_VALID;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_FILE_RESOURCE;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_ID_FILE;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_JOB_SUCCESS_STATUS;
import ondemand.constants.ProductConstants;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ProductFileExtensionValidationListener implements StepExecutionListener {
    FileImportAuditService fileImportAuditService;
    private final MessageSource messageSource;

    public ProductFileExtensionValidationListener(FileImportAuditService fileImportAuditService, MessageSource messageSource) {
        this.fileImportAuditService = fileImportAuditService;
        this.messageSource = messageSource;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        Long idFile = Long.parseLong(String.valueOf(stepExecution.getJobParameters().getParameters().get(CONTEXT_PRODUCT_ID_FILE)));
        fileImportAuditService.jobExecutionContextContinuousAudit(idFile, FileImportStatusEnum.IN_PROGRESS, LocalDateTime.now());
        String fileResource = String.valueOf(stepExecution.getJobParameters().getParameters().get(CONTEXT_PRODUCT_FILE_RESOURCE));
        stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_PRODUCT_FILE_RESOURCE, fileResource);
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        Long idFile = Long.parseLong(String.valueOf(stepExecution.getJobParameters().getParameters().get(CONTEXT_PRODUCT_ID_FILE)));
        Boolean companyFileExtensionValid = Boolean.parseBoolean(String.valueOf(stepExecution.getExecutionContext().get(CONTEXT_PRODUCT_FILE_EXTENSION_VALID)));

        if (Boolean.TRUE.equals(companyFileExtensionValid)) {
            stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_PRODUCT_JOB_SUCCESS_STATUS, true);
            return ExitStatus.COMPLETED;
        } else {
            stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_PRODUCT_JOB_SUCCESS_STATUS, false);
            fileImportAuditService.jobExecutionContextContinuousAudit(idFile, FileImportStatusEnum.FAILED, Util.getMessageSource(ProductConstants.VIOLATION_MESSAGE_FILE_EXTENSION, messageSource), LocalDateTime.now());
            return ExitStatus.FAILED;
        }
    }
}
