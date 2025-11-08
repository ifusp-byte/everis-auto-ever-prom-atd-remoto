package com.ifusp.empresa.batch.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomerDataFileValidateListener implements StepExecutionListener {

	Logger logger = Logger.getLogger(CustomerDataFileValidateListener.class.getName());

	@Override
	public void beforeStep(StepExecution stepExecution) {

		logger.log(Level.INFO, "Setteing Parameters Before Executing step: [dataFileValidateStep]...");
	}

	public ExitStatus afterStep(StepExecution stepExecution) {

		logger.log(Level.INFO, "Setteing Parameters After Executing step: [dataFileValidateStep]...");
		return null;

	}

}
