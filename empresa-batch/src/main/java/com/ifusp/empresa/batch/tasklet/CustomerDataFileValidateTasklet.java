package com.ifusp.empresa.batch.tasklet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataFileValidateTasklet implements Tasklet {
	
	Path input = Path.of("files\\");
	Path error = Path.of("files\\Error\\");
	
	Logger logger = Logger.getLogger(CustomerDataFileRenameTasklet.class.getName()); 
	
	public static String fileName = null;
	public static File folder = null;
	public static List<File> listOfFiles = null;
	

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		folder = new File(input.toString());
		listOfFiles = new ArrayList<>(Arrays.asList(folder.listFiles()));
		listOfFiles.removeIf(f -> f.getName().contains("Error") || f.getName().contains("Success"));
		
		while(!listOfFiles.isEmpty()) {
			
			listOfFiles.stream().forEach(file -> {
				
				try {
					
					Path fullPathFile = input.resolve(file.toPath());
					
					if(!Files.probeContentType(fullPathFile).equals("text/plain")) {
						
						fileName = fullPathFile.getFileName().toString();
						
					}
				} catch (IOException e) {
					
					e.printStackTrace();
					logger.log(Level.WARNING, e.toString());
					
				}
					
			});
			
		}
		
		return RepeatStatus.FINISHED;
		
	}

}
