package com.ifusp.empresa.batch.tasklet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.core.io.Resource;

public class CustomerDataFileMoveSuccessTaskletTest {
	
	@Mock
	Logger logger;
	
	@Mock
	Resource inputDirectory;
	
	@Mock
	Resource errorDirectory;
	
	@Mock
	CustomerDataFileMoveErrorTasklet customerDataFileMoveSuccessTasklet;
	
	@Mock
	StepContribution stepContributionMock;
	
	@Mock
	ChunkContext chunkContextMock; 
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testExecute() throws IOException {
		
		customerDataFileMoveSuccessTasklet.execute(stepContributionMock, chunkContextMock);
		assertNotNull(customerDataFileMoveSuccessTasklet);
	}
	

}
