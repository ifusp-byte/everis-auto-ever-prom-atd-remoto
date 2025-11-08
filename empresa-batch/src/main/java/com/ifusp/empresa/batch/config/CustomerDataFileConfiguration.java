package com.ifusp.empresa.batch.config;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ifusp.empresa.batch.dto.CustomerDataFileDTO;
import com.ifusp.empresa.batch.listener.CustomerDataFileJobListener;
import com.ifusp.empresa.batch.listener.CustomerDataFileMoveErrorListener;
import com.ifusp.empresa.batch.listener.CustomerDataFileMoveSuccessListener;
import com.ifusp.empresa.batch.listener.CustomerDataFileReaderListener;
import com.ifusp.empresa.batch.listener.CustomerDataFileRenameListener;
import com.ifusp.empresa.batch.listener.CustomerDataFileValidateListener;
import com.ifusp.empresa.batch.listener.CustomerDataFileWatcherListener;
import com.ifusp.empresa.batch.tasklet.CustomerDataFileMoveErrorTasklet;
import com.ifusp.empresa.batch.tasklet.CustomerDataFileMoveSuccessTasklet;
import com.ifusp.empresa.batch.tasklet.CustomerDataFileRenameTasklet;
import com.ifusp.empresa.batch.tasklet.CustomerDataFileValidateTasklet;
import com.ifusp.empresa.batch.tasklet.CustomerDataFileWatcherTasklet;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class CustomerDataFileConfiguration {


	@Autowired
	public JobBuilderFactory jobBuilderFactory;


	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	Logger logger = Logger.getLogger(CustomerDataFileConfiguration.class.getName());

	@Bean
	public Flow flow1(ItemReader<CustomerDataFileDTO> dataFileItemReader,
			ItemWriter<CustomerDataFileDTO> dataFileItemWriter) {

		return new FlowBuilder<Flow>("flow1").start(dataFileWatcherStep(stepBuilderFactory)).on("COMPLETED")
				.to(dataFileReadStep(dataFileItemReader, dataFileItemWriter)).build();
	}

	@Bean
	public Flow flow2() {

		return new FlowBuilder<Flow>("flow2").start((dataFileRenameStep(stepBuilderFactory))).on("COMPLTED")
				.to(dataFileMoveSuccessStep(stepBuilderFactory)).build();

	}

	@Bean
	public Flow flow3() {

		return new FlowBuilder<Flow>("flow3").start((dataFileRenameStep(stepBuilderFactory))).on("COMPLTED")
				.to(dataFileMoveErrorStep(stepBuilderFactory)).build();
	}


	@Bean
	public Job customerJob(Flow flow1, Flow flow2, Flow flow3) throws IOException {

		return jobBuilderFactory.get("customerJob").listener(new CustomerDataFileJobListener()).start(flow1).on("COMPLTED").to(flow2).from(flow1)
				.on("FAILED").to(flow3).end().build();
	}

	
	@Bean
	public Step dataFileReadStep(ItemReader<CustomerDataFileDTO> dataFileItemReader,
			ItemWriter<CustomerDataFileDTO> dataFileItemWriter) {

		return stepBuilderFactory.get("dataFileReadStep").<CustomerDataFileDTO, CustomerDataFileDTO>chunk(100000).reader(dataFileItemReader)
				.writer(dataFileItemWriter).listener(new CustomerDataFileReaderListener()).build();
	}

	
	@Bean
	public Step dataFileRenameStep(StepBuilderFactory stepBuilders) {

		return stepBuilders.get("dataFileRenameStep").tasklet(new CustomerDataFileRenameTasklet()).listener(new CustomerDataFileRenameListener())
				.build();
	}

	
	@Bean
	public Step dataFileWatcherStep(StepBuilderFactory stepBuilders) {

		return stepBuilders.get("dataFileWatcherStep").tasklet(new CustomerDataFileWatcherTasklet()).listener(new CustomerDataFileWatcherListener())
				.build();
	}

	
	@Bean
	public Step dataFileValidatedStep(StepBuilderFactory stepBuilders) {

		return stepBuilders.get("dataFileValidatedStep").tasklet(new CustomerDataFileValidateTasklet())
				.listener(new CustomerDataFileValidateListener()).build();
	}

	
	@Bean
	public Step dataFileMoveSuccessStep(StepBuilderFactory stepBuilders) {

		return stepBuilders.get("dataFileMoveSuccessStep").tasklet(new CustomerDataFileMoveSuccessTasklet())
				.listener(new CustomerDataFileMoveSuccessListener()).build();
	}

	
	@Bean
	public Step dataFileMoveErrorStep(StepBuilderFactory stepBuilders) {

		return stepBuilders.get("dataFileMoveErrorStep").tasklet(new CustomerDataFileMoveErrorTasklet())
				.listener(new CustomerDataFileMoveErrorListener()).build();
	}

}
