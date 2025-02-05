package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumento;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerAnexoDocumentoTestes3Test extends AtendimentoRemotoControllerAnexoDocumentoTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value(), "erroDossieSiecm.json",
				"siecmDocumentoIncluirErro.json", "siecmDocumentoConsultarErro.json");
	}

	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	//@Test
	@Tag("anexoDocumento")
	void consultaDocumentoErroTest() throws StreamReadException, DatabindException, IOException {
		consultaDocumento();
	}

}
