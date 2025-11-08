package com.ifusp.empresa.batch.listener;

import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;

public class CustomerDataFileWatcherListenerTest {
	
	@Mock
	Logger logger;
	
	@Mock
	CustomerDataFileWatcherListener customerDataFileWatcherListener;
	
	@BeforeEach
	void set() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testBeforeStep() {
		customerDataFileWatcherListener.beforeStep(null);
	}
	
	@Test
	void testAfterStep() {
		ExitStatus result = customerDataFileWatcherListener.afterStep(null);
		Assertions.assertEquals(null, result);
	}

}
