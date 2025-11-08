package com.empresa.immediate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.empresa.immediate.ImmediateApplication;

class ImmediateApplicationTest {

	@Test
	void testMain() throws InterruptedException {
		String[] mainMethod = new String[] { "args" };
		ImmediateApplication.main(mainMethod);
		Assertions.assertNotNull(mainMethod);
	}

}
