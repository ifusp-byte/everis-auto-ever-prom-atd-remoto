package br.gov.caixa.siavl.atendimentoremoto.controller.protocolo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
class AtendimentoRemotoControllerProtocoloContaAtendimentoTest extends AtendimentoRemotoControllerProtocoloTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
	}

	@Test
	void contaAtendimentoTest() throws Exception {
		contaAtendimento();
	}

}
