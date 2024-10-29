package br.gov.caixa.siavl.atendimentoremoto.controller.modelonota;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
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
	void consultaModeloNotaTest() {
		consultaModeloNota();
	}

	@Test
	void consultaModeloNotaMaisUtilizadaTest() {
		consultaModeloNotaMaisUtilizada();
	}

	@Test
	void consultaModeloNotaFavoritaTest() {
		consultaModeloNotaFavorita();
	}

	@Test
	void adicionaModeloNotaFavoritaTest() {
		adicionaModeloNotaFavorita();
	}

	@Test
	void modeloNotaDinamico1Test() throws StreamReadException, DatabindException, IOException {
		modeloNotaDinamico1();
	}

	@Test
	void modeloNotaDinamico2Test() throws StreamReadException, DatabindException, IOException {
		modeloNotaDinamico2();
	}

	@Test
	void modeloNotaDinamico3Test() throws StreamReadException, DatabindException, IOException {
		modeloNotaDinamico3();
	}
}
