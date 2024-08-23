package br.gov.caixa.siavl.atendimentoremoto.controller;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.GERA_PROTOCOLO_PJ;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerProtocoloPJPF0Test extends AtendimentoRemotoControllerProtocoloTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.UNAUTHORIZED.value(), "sucessoAuditoriaPnc.json", "erroConsultaSicliPJPF.json",
				"12402627000158");
	}
	
	@Test
	void geraProtocoloTestPJPF1Test() throws StreamReadException, DatabindException, IOException {
		geraProtocolo(GERA_PROTOCOLO_PJ);
	}

}
