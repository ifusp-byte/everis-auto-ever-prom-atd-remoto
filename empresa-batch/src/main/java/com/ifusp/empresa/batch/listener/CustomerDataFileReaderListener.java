package com.ifusp.empresa.batch.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataFileReaderListener implements StepExecutionListener {

	Logger logger = Logger.getLogger(CustomerDataFileReaderListener.class.getName());

	@Override
	public void beforeStep(StepExecution stepExcution) {

		logger.log(Level.INFO, "Setting Parameters Before Executing step: [customerReadStep]...");

		String fileStepResource3 = stepExcution.getJobExecution().getExecutionContext().get("file_step_resource2")
				.toString();

		stepExcution.getJobExecution().getExecutionContext().put("file_step_resource3", fileStepResource3);
		stepExcution.getExecutionContext().put("input_success_resource3", fileStepResource3);

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		logger.log(Level.INFO, "Setting Paramters After Executing step: [customerReadStep]...");

		try {

			Thread.sleep(1000);

		} catch (InterruptedException e) {

			e.printStackTrace();

		}

		return ExitStatus.COMPLETED;

	}

}
