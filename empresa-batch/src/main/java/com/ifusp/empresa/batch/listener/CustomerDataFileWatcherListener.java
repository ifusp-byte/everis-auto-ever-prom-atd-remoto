package com.ifusp.empresa.batch.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataFileWatcherListener implements StepExecutionListener {

	Logger logger = Logger.getLogger(CustomerDataFileWatcherListener.class.getName());

	@Override
	public void beforeStep(StepExecution stepExecution) {

		logger.log(Level.INFO, "Setting Paramters Before Executing step: [dataFileWatcherStep]...");
		String inputFolder = "files\\";
		stepExecution.getExecutionContext().put("file_step_resource1", inputFolder);
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		logger.log(Level.INFO, "Setting Paramters After Executing step: [dataFileWatcherStep]...");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (stepExecution.getStatus() == BatchStatus.COMPLETED) {

			String fileStepResource2 = stepExecution.getExecutionContext().getString("file_step_resource2").toString();
			stepExecution.getJobExecution().getExecutionContext().put("file_step_resource2", fileStepResource2);

			return ExitStatus.COMPLETED;
		}

		return ExitStatus.FAILED;
	}

}
