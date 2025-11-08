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
class AtendimentoRemotoControllerAnexoDocumentoSimtrTestes2Test
		extends AtendimentoRemotoControllerAnexoDocumentoSimtrTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao1(HttpStatus.OK.value(), "simtrDocumentoByIdSucesso.json", "1837034", HttpStatus.OK.value(),
				"simtrTokenSucesso.json");
	}

	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	@Test
	@Tag("consultaDocumentoSimtr")
	void consultaDocumentoSimtrTest() throws StreamReadException, DatabindException, IOException {
		consultaDocumentoSimtr();
	}

}
