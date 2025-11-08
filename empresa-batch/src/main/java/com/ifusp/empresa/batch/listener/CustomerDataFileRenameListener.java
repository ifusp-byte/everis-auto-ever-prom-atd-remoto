package com.ifusp.empresa.batch.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataFileRenameListener implements StepExecutionListener {

	Logger logger = Logger.getLogger(CustomerDataFileRenameListener.class.getName());

	@Override
	public void beforeStep(StepExecution stepExecution) {

		logger.log(Level.INFO, "Setting Parameters Before Executing step: [dataFileRenameStep]...");
		stepExecution.getJobExecution().getExecutionContext().get("file_step_resource2").toString();
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		logger.log(Level.INFO, "Setting Parameters After Executing step: [dataFileRenameStep]...");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String fileStepResource4 = stepExecution.getExecutionContext().getString("file_step_resource4").toString();
		stepExecution.getJobExecution().getExecutionContext().put("file_step_resource4", fileStepResource4);

		return ExitStatus.COMPLETED;
	}

}
