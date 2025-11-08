package br.gov.caixa.siavl.atendimentoremoto.controller.contratanota;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
class AtendimentoRemotoControllerContrataNotaTestesTest extends AtendimentoRemotoControllerContrataNotaTest {

	@BeforeEach
	public void beforeEach() {
		setUpTest();
	}

	@Test
	@Tag("contratanota")
	void contrataNotaPF1Test() throws Exception {
		contrataNotaPF1();
	}

	@Test
	@Tag("contratanota")
	void contrataNotaPF2Test() throws Exception {
		contrataNotaPF2();
	}
}
