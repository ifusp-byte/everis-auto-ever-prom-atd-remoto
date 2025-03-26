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
class AtendimentoRemotoControllerAnexoDocumentoSimtrTestes6Test
		extends AtendimentoRemotoControllerAnexoDocumentoSimtrTest {

	@BeforeEach
	public void setUp2() {
		setUpTest();
		setupIntegracao1(HttpStatus.OK.value(), "simtrDocumentosSucesso5.json", "10020030088", HttpStatus.OK.value(),
				"simtrTokenSucesso.json");
	}

	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	@Test
	@Tag("consultaDossieSimtr")
	void consultaDossieSimtrTest() throws StreamReadException, DatabindException, IOException {
		consultaDossieSimtr();
	}

}
