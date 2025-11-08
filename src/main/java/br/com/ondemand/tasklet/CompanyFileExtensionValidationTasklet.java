package br.com.ondemand.tasklet;

import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_EXTENSION_VALID;
import static ondemand.constants.CompanyConstants.CONTEXT_COMPANY_FILE_RESOURCE;
import static ondemand.constants.CompanyConstants.FILE_EXTENSION_XLS;
import static ondemand.constants.CompanyConstants.FILE_EXTENSION_XLSX;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CompanyFileExtensionValidationTasklet implements Tasklet {

	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		String absolutePathResource = String
				.valueOf(chunkContext.getStepContext().getJobExecutionContext().get(CONTEXT_COMPANY_FILE_RESOURCE));
		chunkContext.getStepContext().getStepExecution().getExecutionContext().put(CONTEXT_COMPANY_FILE_EXTENSION_VALID,
				String.valueOf(absolutePathResource.endsWith(FILE_EXTENSION_XLS)
						|| absolutePathResource.endsWith(FILE_EXTENSION_XLSX)));
		return RepeatStatus.FINISHED;
	}
}
