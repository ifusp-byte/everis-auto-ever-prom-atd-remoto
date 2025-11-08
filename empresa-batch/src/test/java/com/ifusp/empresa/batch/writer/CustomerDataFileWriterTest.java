package com.ifusp.empresa.batch.writer;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ifusp.empresa.batch.repository.CustomerDataFileRepository;

public class CustomerDataFileWriterTest {
	
	@Mock
	CustomerDataFileRepository repository;
	
	@Mock
	CustomerDataFileWriter customerDataFileWriter;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	/*
	@Test
	void testCustomerFileDataItemWriter() {
		ItemWriter<CustomerDataFileDTO> result = customerDataFileWriter.customerFileDataItemWriter();
		Assertions.assertEquals(null, result);
	}
*/
}
