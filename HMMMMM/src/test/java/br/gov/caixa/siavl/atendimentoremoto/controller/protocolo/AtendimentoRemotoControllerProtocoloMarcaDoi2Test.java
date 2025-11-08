package br.gov.caixa.siavl.atendimentoremoto.controller.protocolo;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.GERA_PROTOCOLO_PF;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerProtocoloMarcaDoi2Test extends AtendimentoRemotoControllerProtocoloTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.OK.value(), "sucessoAuditoriaPnc.json", "sicliMarcaDoiN.json",
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
