package com.ifusp.empresa.batch.tasklet;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
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
public class CustomerDataFileWatcherTasklet implements Tasklet {

	Resource inputDirectory = null;
	WatchService watchService = null;
	WatchKey watchKey = null;
	Path pathFolder = null;

	Logger logger = Logger.getLogger(CustomerDataFileWatcherTasklet.class.getName());

	static String absoluteFilePath = null;
	static File file = null;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		pathFolder = Path.of(chunkContext.getStepContext().getStepExecution().getExecutionContext().get("file_step_resource1").toString());
		inputDirectory = new FileSystemResource(pathFolder);
		watchService = FileSystems.getDefault().newWatchService();
		watchKey = inputDirectory.getFile().toPath().register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		while (true) {
			for (WatchEvent<?> eventWatch : watchKey.pollEvents()) {

				@SuppressWarnings("unchecked")
				WatchEvent<Path> eventPath = (WatchEvent<Path>) eventWatch;
				Path fileName = eventPath.context();
				WatchEvent.Kind<?> eventKind = eventWatch.kind();

				if (eventKind == StandardWatchEventKinds.ENTRY_CREATE) {
					logger.log(Level.INFO, "Novo arquivo: ", fileName);

					absoluteFilePath = pathFolder.toString().replace("\\", "\\\\") + "\\\\" + fileName.toString();

					file = new File(absoluteFilePath);

					watchKey.cancel();
				}
			}

			if (!watchKey.reset()) {
				break;
			}
		}

		watchService.close();

		chunkContext.getStepContext().getStepExecution().setExitStatus(ExitStatus.COMPLETED);
		chunkContext.getStepContext().getStepExecution().getExecutionContext().put("file_step_resource2", absoluteFilePath);

		return RepeatStatus.FINISHED;
	}

}
