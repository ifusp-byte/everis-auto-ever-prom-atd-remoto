package com.ifusp.empresa.batch.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataFileMoveErrorListener implements StepExecutionListener {

	Logger logger = Logger.getLogger(CustomerDataFileMoveErrorListener.class.getName());

	@Override
	public void beforeStep(StepExecution stepExcution) {

		logger.log(Level.INFO, "Setting Parameters Before Executing step: [dataFileMoveStep]...");

		String inputSuccess = "files\\Error\\";
		String fileStepResource4 = stepExcution.getJobExecution().getExecutionContext().get("file_step_resource4")
				.toString();

		stepExcution.getJobExecution().getExecutionContext().put("file_step_resource4", fileStepResource4);
		stepExcution.getExecutionContext().put("input_error_resource", inputSuccess);
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		logger.log(Level.INFO, "Setting Paramters After Executing step: [dataFileMoveStep]...");

		return ExitStatus.COMPLETED;

	}

}
