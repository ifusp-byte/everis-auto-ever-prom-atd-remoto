package br.com.ondemand.config;

import java.util.concurrent.Flow;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath.Step;

import br.com.ondemand.dto.CompanyDto;
import br.com.ondemand.listener.CompanyFileExtensionValidationListener;
import br.com.ondemand.listener.CompanyFileHeaderValidationListener;
import br.com.ondemand.listener.CompanyReaderListener;
import br.com.ondemand.tasklet.CompanyFileExtensionValidationTasklet;
import br.com.ondemand.tasklet.CompanyFileHeaderValidationTasklet;
import br.com.ondemand.validation.company.service.CompanyValidatorService;
import src.main.java.br.com.ondemand.listener.CompanyJobListener;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class CompanyConfig {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final CompanyValidatorService companyValidatorService;
	private final FileImportAuditService fileImportAuditService;
	private final MessageSource messageSource;
	private static final String JOB_COMPANY = "companyJob";
	private static final int ROW_PARTITIONING = 10;
	private static final String STATUS_COMPLETED = "COMPLETED";
	private static final String STATUS_FAILED = "FAILED";
	private static final String FLOW_COMPANY_FILE_EXTENSION_VALIDATION = "companyFileExtensionValidationFlow";
	private static final String FLOW_COMPANY_FILE_HEADER_VALIDATION = "companyFileHeaderValidationFlow";
	private static final String STEP_COMPANY_FILE_EXTENSION_VALIDATION = "companyFileExtensionValidationStep";
	private static final String STEP_COMPANY_FILE_HEADER_VALIDATION = "companyFileHeaderValidationStep";
	private static final String STEP_COMPANY_READ_WRITE = "companyReadWriteStep";
	private static final String QUALIFIER_COMPANY_ITEM_READER = "companyItemReader";
	private static final String QUALIFIER_COMPANY_ITEM_WRITER = "companyItemWriter";

	public CompanyConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			@Lazy CompanyValidatorService companyValidatorService, MessageSource messageSource,
			FileImportAuditService fileImportAuditService) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.companyValidatorService = companyValidatorService;
		this.fileImportAuditService = fileImportAuditService;
		this.messageSource = messageSource;
	}

	@Bean(name = JOB_COMPANY)
	public Job companyJob(Flow companyFileExtensionValidationFlow, Flow companyFileHeaderValidationFlow) {
		return jobBuilderFactory.get(JOB_COMPANY).listener(new CompanyJobListener(messageSource))
				.start(companyFileExtensionValidationFlow).on(STATUS_COMPLETED).to(companyFileHeaderValidationFlow)
				.from(companyFileExtensionValidationFlow).on(STATUS_FAILED).end().end().build();
	}

	@Bean
	public Flow companyFileExtensionValidationFlow() {
		return new FlowBuilder<Flow>(FLOW_COMPANY_FILE_EXTENSION_VALIDATION)
				.start(companyFileExtensionValidationStep(stepBuilderFactory)).build();
	}

	@Bean
	public Flow companyFileHeaderValidationFlow(
			@Qualifier(QUALIFIER_COMPANY_ITEM_READER) PoiItemReader<CompanyDto> itemReader,
			@Qualifier(QUALIFIER_COMPANY_ITEM_WRITER) ItemWriter<CompanyDto> itemWriter) {
		return new FlowBuilder<Flow>(FLOW_COMPANY_FILE_HEADER_VALIDATION)
				.start(companyFileHeaderValidationStep(stepBuilderFactory)).on(STATUS_COMPLETED)
				.to(companyReadWriteStep(itemReader, itemWriter))
				.from(companyFileHeaderValidationStep(stepBuilderFactory)).on(STATUS_FAILED).end().build();
	}

	@Bean
	public Step companyReadWriteStep(@Qualifier(QUALIFIER_COMPANY_ITEM_READER) PoiItemReader<CompanyDto> itemReader,
			@Qualifier(QUALIFIER_COMPANY_ITEM_WRITER) ItemWriter<CompanyDto> itemWriter) {
		return stepBuilderFactory.get(STEP_COMPANY_READ_WRITE).<CompanyDto, CompanyDto>chunk(ROW_PARTITIONING)
				.reader(itemReader).writer(itemWriter).listener(new CompanyReaderListener(fileImportAuditService))
				.build();
	}

	@Bean
	public Step companyFileExtensionValidationStep(StepBuilderFactory stepBuilders) {
		return stepBuilders.get(STEP_COMPANY_FILE_EXTENSION_VALIDATION)
				.tasklet(new CompanyFileExtensionValidationTasklet())
				.listener(new CompanyFileExtensionValidationListener(fileImportAuditService, messageSource)).build();
	}

	@Bean
	public Step companyFileHeaderValidationStep(StepBuilderFactory stepBuilders) {
		return stepBuilders.get(STEP_COMPANY_FILE_HEADER_VALIDATION)
				.tasklet(new CompanyFileHeaderValidationTasklet(companyValidatorService))
				.listener(new CompanyFileHeaderValidationListener(fileImportAuditService, messageSource)).build();
	}

}
