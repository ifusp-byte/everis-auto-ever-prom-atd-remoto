package br.gov.caixa.siavl.atendimentoremoto.controller.protocolo;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerProtocoloMarcaDoi3Test extends AtendimentoRemotoControllerProtocoloTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.BAD_REQUEST.value(), "erro.json", "erro.json",
				"10020030088");
	}
	
	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	@Test
	void marcaDoiSTest() throws StreamReadException, DatabindException, IOException {
		marcaDoi();
	}

}
