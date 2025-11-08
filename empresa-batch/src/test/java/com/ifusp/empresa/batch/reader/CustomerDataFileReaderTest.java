package com.ifusp.empresa.batch.reader;

import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.Resource;

import com.ifusp.empresa.batch.dto.CustomerDataFileDTO;
import com.ifusp.empresa.batch.mapper.CustomerDataFileMapper;
import com.ifusp.empresa.batch.range.CustomerDataFileRange;
import com.ifusp.empresa.batch.token.CustomerDataFileToken;

public class CustomerDataFileReaderTest {

	@Mock
	CustomerDataFileRange customerDataFileRange;

	@Mock
	CustomerDataFileToken customerFileDataToken;

	@Mock
	CustomerDataFileMapper customerFileDataMapper;
	
	@Mock
	Logger logger;

	@Mock
	Resource customerResource;

	@Mock
	Path customerInputDirectory;

	@Mock
	CustomerDataFileReader customerDataFileReader;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test 
	void testCustomerDataFileItemReader() throws UnexpectedInputException, ParseException, Exception {
		when(customerDataFileRange.customerDataFileRange()).thenReturn(new Range[] {null});
		when(customerFileDataToken.customerDataFileToken()).thenReturn(new String [] {"customerDataFileTokenResponse"});
		
		FlatFileItemReader<CustomerDataFileDTO> result = customerDataFileReader.customerDataFileItemReader("absolutePathResource");
		Assertions.assertEquals(null, result);
	}

}
