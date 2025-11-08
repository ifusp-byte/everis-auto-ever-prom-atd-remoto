package com.ifusp.empresa.batch.listener;

import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;

public class CustomerDataFileValidateListenerTest {
	
	@Mock
	Logger logger;
	
	@InjectMocks
	CustomerDataFileValidateListener customerDataFileValidateListener;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testBeforeStep() {
		customerDataFileValidateListener.beforeStep(null);
	}
	
	@Test
	void testAfterStep() {
		ExitStatus result = customerDataFileValidateListener.afterStep(null);
		Assertions.assertEquals(null, result);
	}
	
}
