package ondemand.listener;

import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_FILE_RESOURCE;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_ID_FILE;
import static ondemand.constants.ProductConstants.CONTEXT_PRODUCT_JOB_SUCCESS_STATUS;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ProductReaderListener implements StepExecutionListener {
    FileImportAuditService fileImportAuditService;

    public ProductReaderListener() {
        super();
    }

    public ProductReaderListener(FileImportAuditService fileImportAuditService) {
        this.fileImportAuditService = fileImportAuditService;
    }

    private static final int INTERVAL_BETWEEN_STEP = 1000;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        String fileResource = String.valueOf(stepExecution.getJobParameters().getParameters().get(CONTEXT_PRODUCT_FILE_RESOURCE));
        stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_PRODUCT_FILE_RESOURCE, fileResource);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        Long idFile = Long.parseLong(String.valueOf(stepExecution.getJobParameters().getParameters().get(CONTEXT_PRODUCT_ID_FILE)));
        try {
            Thread.sleep(INTERVAL_BETWEEN_STEP);
        } catch (InterruptedException e) {
            fileImportAuditService.jobExecutionContextContinuousAudit(idFile, FileImportStatusEnum.FAILED, LocalDateTime.now());
            Thread.currentThread().interrupt();
            stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_PRODUCT_JOB_SUCCESS_STATUS, false);
            return ExitStatus.FAILED;
        }
        stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_PRODUCT_JOB_SUCCESS_STATUS, true);
        return ExitStatus.COMPLETED;
    }
}
