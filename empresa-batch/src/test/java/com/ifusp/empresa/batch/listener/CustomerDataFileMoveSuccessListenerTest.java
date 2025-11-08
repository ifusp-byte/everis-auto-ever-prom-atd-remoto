package com.ifusp.empresa.batch.listener;

import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;

public class CustomerDataFileMoveSuccessListenerTest {
	
	@Mock
	Logger logger;
	
	@Mock
	CustomerDataFileMoveSuccessListener customerDataFileMoveSuccessListener;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testBeforeStep() {
		customerDataFileMoveSuccessListener.beforeStep(null);
	}
	
	@Test
	void testAfterStep() {
		ExitStatus result = customerDataFileMoveSuccessListener.afterStep(null);
		Assertions.assertEquals(null, result);
	}

}
