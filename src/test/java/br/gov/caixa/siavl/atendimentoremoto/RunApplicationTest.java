package br.gov.caixa.siavl.atendimentoremoto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RunApplicationTest {

	@Test
	void testMain() throws InterruptedException {
		String[] mainMethod = new String[] { "args" };
		RunApplication.main(mainMethod);
		Assertions.assertNotNull(mainMethod);
	}
}
