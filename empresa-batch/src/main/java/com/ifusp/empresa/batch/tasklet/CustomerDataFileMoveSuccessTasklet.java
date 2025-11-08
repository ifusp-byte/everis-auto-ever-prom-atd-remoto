package com.ifusp.empresa.batch.tasklet;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataFileMoveSuccessTasklet implements Tasklet {

	Logger logger = Logger.getLogger(CustomerDataFileMoveSuccessTasklet.class.getName());

	Resource inputDirectory = null;
	Resource successDiretory = null;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		inputDirectory = new FileSystemResource(
				chunkContext.getStepContext().getJobExecutionContext().get("file_step_resource4").toString());
		inputDirectory = new FileSystemResource(
				chunkContext.getStepContext().getJobExecutionContext().get("input_success_resource").toString());

		File from = new File(inputDirectory.getFile().getPath());
		File toSuccess = new File(
				successDiretory.getFile().getAbsolutePath() + "\\" + inputDirectory.getFile().getName());

		logger.log(Level.INFO, "Start moving " + "");

		try {

			Files.move(from.toPath(), toSuccess.toPath(), StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {

			logger.log(Level.WARNING, e.toString());
		}

		logger.log(Level.INFO, "File" + "" + "moved to " + successDiretory);

		chunkContext.getStepContext().getStepExecution().setExitStatus(ExitStatus.COMPLETED);

		return RepeatStatus.FINISHED;
	}

}
