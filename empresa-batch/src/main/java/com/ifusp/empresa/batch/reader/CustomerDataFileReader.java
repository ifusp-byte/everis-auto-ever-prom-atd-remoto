package com.ifusp.empresa.batch.reader;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.ifusp.empresa.batch.dto.CustomerDataFileDTO;
import com.ifusp.empresa.batch.mapper.CustomerDataFileMapper;
import com.ifusp.empresa.batch.range.CustomerDataFileRange;
import com.ifusp.empresa.batch.token.CustomerDataFileToken;

@Configuration
public class CustomerDataFileReader {
	
	@Autowired
	CustomerDataFileRange customerFileDataRange;
	
	@Autowired
	CustomerDataFileToken customerFileDataToken;
	
	@Autowired
	CustomerDataFileMapper customerFileDataMapper;
	
	Logger logger = Logger.getLogger(CustomerDataFileReader.class.getName());
	
	Resource customerResource = null;
	Path customerInputDirectory = null;
	
    /*
	@Value("file:files/*txt")
	Resource[] customerResources;
	
	@Bean
	@Primary
	@StepScope
	
	public MultiResourceItemReader<CustomerDataFileDTO> customerDataFileMultiItemReader(@Value("#{jobParameters['inputResources']}") Resource[] inputResources) throws UnexpectInputException, ParseException, Exception {
	
	MultiResourceItemReader<CustomerDataFileDTO> resourceItemReader = new MultiResourceItemReader<CustomerDataFileDTO>();
	resourceItemReader.setResources(inputResources);
	resourceItemReader.setDelegate(customerDataFileItemReader(null));
	resourceItemReader.setStrict(false);
	resourceItemReader.setSaveState(false);
	
	return resourceItemReader;
	}
	*/
	 
	
	@Bean
	@StepScope 
	public FlatFileItemReader<CustomerDataFileDTO> customerDataFileItemReader(@Value("#{jobExecutionContext['file_step_resource3']}") String absolutePathResource) throws UnexpectedInputException, ParseException, Exception {
		
		customerResource = new FileSystemResource(absolutePathResource);
		
		FlatFileItemReader<CustomerDataFileDTO> itemReader = new FlatFileItemReader<>();
		
		itemReader.setStrict(false);
		itemReader.setResource(customerResource);
		itemReader.setLineMapper(customerFileDataLineMapper());
		itemReader.open(new ExecutionContext());
		itemReader.close();
		
		return itemReader;
	}
	
	@Bean
	public LineMapper<CustomerDataFileDTO> customerFileDataLineMapper() {
		
		DefaultLineMapper<CustomerDataFileDTO> lineMapper = new DefaultLineMapper<>();
		FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
		
		tokenizer.setNames(customerFileDataToken.customerDataFileToken());
		tokenizer.setColumns(customerFileDataRange.customerDataFileRange());
		
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(customerFileDataMapper);
		
		return lineMapper;
		
	}
	
	@Bean
	public FieldSetMapper<CustomerDataFileDTO> customerFileDatafieldSetMapper() {
		
		BeanWrapperFieldSetMapper<CustomerDataFileDTO> mapper = new BeanWrapperFieldSetMapper<>();
		
		mapper.setTargetType(CustomerDataFileDTO.class);
		mapper.setStrict(false);
		
		return mapper;
	}
	

}
