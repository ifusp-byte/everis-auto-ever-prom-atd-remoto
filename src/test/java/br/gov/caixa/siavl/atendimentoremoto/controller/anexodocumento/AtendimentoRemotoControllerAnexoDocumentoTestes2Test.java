package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumento;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.ANEXA_DOCUMENTO_OPCIONAL;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerAnexoDocumentoTestes2Test extends AtendimentoRemotoControllerAnexoDocumentoTest {

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

	@Test
	@Tag("anexoDocumento")
	void anexoDocumentoOpcionalTest() throws StreamReadException, DatabindException, IOException {
		anexoDocumento(ANEXA_DOCUMENTO_OPCIONAL);
	}

}
