package com.ifusp.empresa.batch.tasklet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CustomerDataFileRenameTasklet implements Tasklet {

	Logger logger = Logger.getLogger(CustomerDataFileRenameTasklet.class.getName());

	File file2 = null;
	Resource r = null;

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss_Z");
	String currentDateTime = sdf.format(new Date()).toString();

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {

		r = new FileSystemResource(
				chunkContext.getStepContext().getJobExecutionContext().get("file_step_resource2").toString());
		file2 = new File(r.getFile().getPath().toString().replace(".txt", "") + "_" + currentDateTime + ".txt");

		if (r.getFile().toPath().toFile().renameTo(file2)) {

			logger.log(Level.INFO, r.getFile().toPath().getFileName().toString() + " successfuly renamed to: "
					+ file2.toPath().getFileName().toString());
		}

		chunkContext.getStepContext().getStepExecution().getExecutionContext().put("file_step_resource4",
				file2.getAbsolutePath().replace("\\", "\\\\"));
		chunkContext.getStepContext().getStepExecution().setExitStatus(ExitStatus.COMPLETED);

		return RepeatStatus.FINISHED;
	}

}
