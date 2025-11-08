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

public class CustomerDataFileMoveErrorTaskletTest {
	
	@Mock
	Logger logger;
	
	@Mock
	Resource inputDirectory;
	
	@Mock
	Resource errorDirectory;
	
	@Mock
	CustomerDataFileMoveErrorTasklet customerDataFileMoveErrorTasklet;
	
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
		
		customerDataFileMoveErrorTasklet.execute(stepContributionMock, chunkContextMock);
		assertNotNull(customerDataFileMoveErrorTasklet);
	}
	

}
