package ondemand.config;

import ondemand.dto.ProductDto;
import ondemand.listener.ProductFileExtensionValidationListener;
import ondemand.listener.ProductFileHeaderValidationListener;
import ondemand.listener.ProductJobListener;
import ondemand.listener.ProductReaderListener;
import ondemand.tasklet.ProductFileExtensionValidationTasklet;
import ondemand.tasklet.ProductFileHeaderValidationTasklet;
import ondemand.validation.product.service.ProductValidatorService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class ProductConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ProductValidatorService productValidatorService;
    private final FileImportAuditService fileImportAuditService;
    private final MessageSource messageSource;
    private static final String JOB_PRODUCT = "productJob";
    private static final int ROW_PARTITIONING = 10;
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_FAILED = "FAILED";
    private static final String FLOW_PRODUCT_FILE_EXTENSION_VALIDATION = "productFileExtensionValidationFlow";
    private static final String FLOW_PRODUCT_FILE_HEADER_VALIDATION = "productFileHeaderValidationFlow";
    private static final String STEP_PRODUCT_FILE_EXTENSION_VALIDATION = "productFileExtensionValidationStep";
    private static final String STEP_PRODUCT_FILE_HEADER_VALIDATION = "productFileHeaderValidationStep";
    private static final String STEP_PRODUCT_READ_WRITE = "productReadWriteStep";
    private static final String QUALIFIER_PRODUCT_ITEM_READER = "productItemReader";
    private static final String QUALIFIER_PRODUCT_ITEM_WRITER = "productItemWriter";

    public ProductConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, @Lazy ProductValidatorService productValidatorService, MessageSource messageSource, FileImportAuditService fileImportAuditService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.productValidatorService = productValidatorService;
        this.fileImportAuditService = fileImportAuditService;
        this.messageSource = messageSource;
    }

    @Bean(name = JOB_PRODUCT)
    public Job productJob(Flow productFileExtensionValidationFlow, Flow productFileHeaderValidationFlow) {
        return jobBuilderFactory
                .get(JOB_PRODUCT)
                .listener(new ProductJobListener(messageSource))
                .start(productFileExtensionValidationFlow)
                .on(STATUS_COMPLETED).to(productFileHeaderValidationFlow).from(productFileExtensionValidationFlow)
                .on(STATUS_FAILED)
                .end()
                .end()
                .build();
    }

    @Bean
    public Flow productFileExtensionValidationFlow() {
        return new FlowBuilder<Flow>(FLOW_PRODUCT_FILE_EXTENSION_VALIDATION)
                .start(productFileExtensionValidationStep(stepBuilderFactory))
                .build();
    }

    @Bean
    public Flow productFileHeaderValidationFlow(@Qualifier(QUALIFIER_PRODUCT_ITEM_READER) PoiItemReader<ProductDto> itemReader,
                                                @Qualifier(QUALIFIER_PRODUCT_ITEM_WRITER) ItemWriter<ProductDto> itemWriter) {
        return new FlowBuilder<Flow>(FLOW_PRODUCT_FILE_HEADER_VALIDATION)
                .start(productFileHeaderValidationStep(stepBuilderFactory))
                .on(STATUS_COMPLETED).to(productReadWriteStep(itemReader, itemWriter)).from(productFileHeaderValidationStep(stepBuilderFactory))
                .on(STATUS_FAILED)
                .end()
                .build();
    }

    @Bean
    public Step productReadWriteStep(@Qualifier(QUALIFIER_PRODUCT_ITEM_READER) PoiItemReader<ProductDto> itemReader,
                                     @Qualifier(QUALIFIER_PRODUCT_ITEM_WRITER) ItemWriter<ProductDto> itemWriter) {
        return stepBuilderFactory
                .get(STEP_PRODUCT_READ_WRITE)
                .<ProductDto, ProductDto>chunk(ROW_PARTITIONING)
                .reader(itemReader)
                .writer(itemWriter)
                .listener(new ProductReaderListener(fileImportAuditService))
                .build();
    }

    @Bean
    public Step productFileExtensionValidationStep(StepBuilderFactory stepBuilders) {
        return stepBuilders.get(STEP_PRODUCT_FILE_EXTENSION_VALIDATION)
                .tasklet(new ProductFileExtensionValidationTasklet())
                .listener(new ProductFileExtensionValidationListener(fileImportAuditService, messageSource))
                .build();
    }

    @Bean
    public Step productFileHeaderValidationStep(StepBuilderFactory stepBuilders) {
        return stepBuilders.get(STEP_PRODUCT_FILE_HEADER_VALIDATION)
                .tasklet(new ProductFileHeaderValidationTasklet(productValidatorService))
                .listener(new ProductFileHeaderValidationListener(fileImportAuditService, messageSource))
                .build();
    }

}
