package com.ifusp.empresa.batch.listener;

import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;

public class CustomerDataFileReaderListenerTest {
	
	@Mock
	Logger logger;
	
	@Mock
	CustomerDataFileReaderListener customerDataFileReaderListener;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testBeforeStep() {
		customerDataFileReaderListener.beforeStep(null);
	}
	
	@Test
	void testAfterStep() {
		ExitStatus result = customerDataFileReaderListener.afterStep(null);
		Assertions.assertEquals(null, result);
	}

}
