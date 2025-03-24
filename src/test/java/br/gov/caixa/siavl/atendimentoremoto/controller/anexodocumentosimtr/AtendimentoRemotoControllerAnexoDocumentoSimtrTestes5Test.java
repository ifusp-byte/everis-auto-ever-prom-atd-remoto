package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumentosimtr;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerAnexoDocumentoSimtrTestes5Test
		extends AtendimentoRemotoControllerAnexoDocumentoSimtrTest {

	@BeforeEach
	public void setUp1() {
		setUpTest();
		setupIntegracaoToken(HttpStatus.OK.value(), "simtrTokenSucesso.json");
	}

	@BeforeEach
	public void setUp2() {
		setUpTest();
		setupIntegracao(HttpStatus.OK.value(), "simtrDocumentosSucesso4.json", "10020030088");
	}

	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracaoDossie();
	}

	@Test
	@Tag("consultaDossieSimtr")
	void consultaDossieSimtrTest() throws StreamReadException, DatabindException, IOException {
		consultaDossieSimtr();
	}

}
