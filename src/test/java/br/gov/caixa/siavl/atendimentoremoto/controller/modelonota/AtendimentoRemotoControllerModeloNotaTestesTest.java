package br.gov.caixa.siavl.atendimentoremoto.controller.modelonota;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerModeloNotaTestesTest extends AtendimentoRemotoControllerModeloNotaTest {

	@BeforeEach
	public void beforeEach() {
		setUpTest();
	}
		
	@Test
	@Tag("modeloNota")
	void consultaTipoNotaTest() {
		consultaTipoNota();
	}

	//@Test
	//@Tag("modeloNota")
	void consultaModeloNotaTest() {
		consultaModeloNota();
	}

	//@Test
	@Tag("modeloNota")
	void consultaModeloNotaMaisUtilizadaTest() {
		consultaModeloNotaMaisUtilizada();
	}

	//@Test //teste 
	//@Tag("modeloNota")
	void consultaModeloNotaFavoritaTest() {
		consultaModeloNotaFavorita();
	}

	//@Test
	//@Tag("modeloNota")
	void adicionaModeloNotaFavoritaTest() {
		adicionaModeloNotaFavorita();
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaDinamico1Test() throws StreamReadException, DatabindException, IOException {
		modeloNotaDinamico1();
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaDinamico2Test() throws StreamReadException, DatabindException, IOException {
		modeloNotaDinamico2();
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaDinamico3Test() throws StreamReadException, DatabindException, IOException {
		modeloNotaDinamico3();
	}
}
