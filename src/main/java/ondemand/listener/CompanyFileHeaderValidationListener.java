package ondemand.listener;

import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_HEADER_VALID;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_HEADER_VIOLATIONS;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_RESOURCE;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_ID_FILE;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_JOB_SUCCESS_STATUS;
import ondemand.constants.CompanyConstants;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class CompanyFileHeaderValidationListener implements StepExecutionListener {
    FileImportAuditService fileImportAuditService;
    private final MessageSource messageSource;

    public CompanyFileHeaderValidationListener(FileImportAuditService fileImportAuditService, MessageSource messageSource) {
        this.fileImportAuditService = fileImportAuditService;
        this.messageSource = messageSource;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String fileResource = String.valueOf(stepExecution.getJobParameters().getParameters().get(CONTEXT_COMPANY_FILE_RESOURCE));
        stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_COMPANY_FILE_RESOURCE, fileResource);
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        Long idFile = Long.parseLong(String.valueOf(stepExecution.getJobParameters().getParameters().get(CONTEXT_COMPANY_ID_FILE)));
        Boolean companyFileHeaderValid = Boolean.parseBoolean(String.valueOf(stepExecution.getExecutionContext().get(CONTEXT_COMPANY_FILE_HEADER_VALID)));
        String companyFileHeaderViolations = String.valueOf(stepExecution.getExecutionContext().get(CONTEXT_COMPANY_FILE_HEADER_VIOLATIONS));

        if (Boolean.FALSE.equals(companyFileHeaderValid)) {
            stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_COMPANY_JOB_SUCCESS_STATUS, true);
            return ExitStatus.COMPLETED;
        } else {
            fileImportAuditService.jobExecutionContextContinuousAudit(idFile, FileImportStatusEnum.FAILED, Util.getMessageSource(CompanyConstants.VIOLATION_MESSAGE_FILE_HEADER, messageSource) + companyFileHeaderViolations, LocalDateTime.now());
            stepExecution.getJobExecution().getExecutionContext().put(CONTEXT_COMPANY_JOB_SUCCESS_STATUS, false);
            return ExitStatus.FAILED;
        }
    }
}
