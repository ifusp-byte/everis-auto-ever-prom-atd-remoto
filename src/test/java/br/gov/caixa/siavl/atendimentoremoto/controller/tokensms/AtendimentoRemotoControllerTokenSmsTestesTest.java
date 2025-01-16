package br.gov.caixa.siavl.atendimentoremoto.controller.tokensms;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerTokenSmsTestesTest extends AtendimentoRemotoControllerTokenSmsTest {

	@BeforeEach
	public void beforeEach() {
		setUpTest();
		setupIntegracao();
	}

	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	@Test
	void identificacaoTokenSmsTest() throws StreamReadException, DatabindException, IOException {
		identificacaoTokenSms();
	}

}
