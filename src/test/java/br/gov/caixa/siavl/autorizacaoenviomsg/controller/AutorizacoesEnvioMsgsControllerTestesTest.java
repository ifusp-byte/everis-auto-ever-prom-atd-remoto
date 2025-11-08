package br.gov.caixa.siavl.autorizacaoenviomsg.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
class AutorizacaoEnvioMsgControllerTestesTest extends AutorizacaoEnvioMsgControllerTest {

	@BeforeEach
	public void beforeEach() {
		setUpTest();
	}

	@Test
	@Tag("autorizacao")
	void autorizacaoTest() throws Exception {
		autorizacao();
	}

	@Test
	@Tag("creditos")
	void creditosTest() throws Exception {
		creditos();
	}

	@Test
	@Tag("saldo")
	void saldoTest() throws Exception {
		saldo();
	}

}
