package br.gov.caixa.siavl.atendimentoremoto.controller.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
class AtendimentoRemotoControllerModeloNotaTestesTest extends AtendimentoRemotoControllerModeloNotaTest {

	@BeforeEach
	public void beforeEach() {
		setUpTest();
	}

	@Test
	@Tag("modelos")
	void modelosNotaSemProdutoTest() {
		modelosNotaSemProduto();
	}

	@Test
	@Tag("detalhesModelo")
	void detalhesModelo1Test() {
		detalhesModelo1();
	}

	
	@Test
	@Tag("detalhesModelo")
	void detalhesModelo2Test() {
		detalhesModelo2();
	}
}
