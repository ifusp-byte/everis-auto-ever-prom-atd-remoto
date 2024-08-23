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
class AtendimentoRemotoControllerProtocoloPF3Test extends AtendimentoRemotoControllerProtocoloTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.OK.value(), "sucessoAuditoriaPnc.json", "sucessoConsultaSicliPF3.json",
				"11871923840");
	}
	
	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	@Test
	void geraProtocoloTestPF3Test() throws StreamReadException, DatabindException, IOException {
		geraProtocolo(GERA_PROTOCOLO_PF);
	}

}
