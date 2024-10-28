package br.gov.caixa.siavl.atendimentoremoto.controller.desafio;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerDesafioTestes3Test extends AtendimentoRemotoControllerDesafioTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.BAD_REQUEST.value(), "siipcDesafioValidarErro1.json");
	}

	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	@Test
	void desafioValidarErroSiipcTest() throws StreamReadException, DatabindException, IOException {
		desafioValidar();
	}

}
