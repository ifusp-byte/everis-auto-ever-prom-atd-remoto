package com.ifusp.empresa.batch.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobOperator;

public class CustomerDataFileJobListenerTest {
	
	@Mock
	JobExecution status;
	
	@Mock 
	JobOperator jobOperator;
	
	@Mock
	CustomerDataFileJobListener customerDataFileJobListener;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testBeforeJob() {
		customerDataFileJobListener.beforeJob(null);
	}
	
	@Test
	void testAfterJob() {
		customerDataFileJobListener.afterJob(null);
	}

}
