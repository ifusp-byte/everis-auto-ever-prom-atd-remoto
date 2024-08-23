package br.gov.caixa.siavl.atendimentoremoto.controller.protocolo;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.GERA_PROTOCOLO_ERRO_CPFCPNPJ_NAO_INFORMADO;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerProtocoloPJPF1Test extends AtendimentoRemotoControllerProtocoloTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
	}

	@Test
	void geraProtocoloTestPJPF1Test() throws StreamReadException, DatabindException, IOException {
		geraProtocolo(GERA_PROTOCOLO_ERRO_CPFCPNPJ_NAO_INFORMADO);
	}

}
