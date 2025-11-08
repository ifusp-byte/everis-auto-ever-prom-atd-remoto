package com.empresa.immediate.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.empresa.immediate.dto.DevedorDTO;
import com.empresa.immediate.service.impl.ImmediateCreateServiceImpl;

class ImmediateCreateServiceImplTest {

	@InjectMocks
	ImmediateCreateServiceImpl immediateCreateServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testDevedorBuild() {
		DevedorDTO result = immediateCreateServiceImpl.devedorBuild(new DevedorDTO("", "cpf", "nome"));
		Assertions.assertNotNull(result);
	}

}
