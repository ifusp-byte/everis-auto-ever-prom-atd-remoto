package com.empresa.immediate.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.empresa.immediate.dto.ViolacaoDTO;
import com.empresa.immediate.exception.ImmediateCreateException;
import com.empresa.immediate.service.ImmediateCreateValidatorService;

class ImmediateCreateExceptionTest {

	@Mock
	ImmediateCreateValidatorService immediateCreateValidatorService;

	@Mock
	ImmediateCreateException immediateCreateException;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testInputDataValidationBuild1() {

		immediateCreateException.inputDataValidationBuild(List.of("noMethod"),

				List.of(new ViolacaoDTO("razao", "propriedade")));

		NoSuchMethodException thrown = assertThrows(NoSuchMethodException.class, () -> {

			immediateCreateValidatorService.getClass().getDeclaredMethod("noMethod" + "ArgumentValidation");

		});

		assertTrue(thrown.getMessage().contains("noMethod"));

	}
}
