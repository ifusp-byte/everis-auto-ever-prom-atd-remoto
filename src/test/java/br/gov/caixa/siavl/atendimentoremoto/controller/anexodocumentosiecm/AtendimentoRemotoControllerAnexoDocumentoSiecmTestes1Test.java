package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumentosiecm;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.ANEXA_DOCUMENTO_ACEITE;
import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.ANEXA_DOCUMENTO_OBRIGATORIO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.ANEXA_DOCUMENTO_OPCIONAL;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerAnexoDocumentoSiecmTestes1Test extends AtendimentoRemotoControllerAnexoDocumentoSiecmTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.BAD_REQUEST.value(),  HttpStatus.CREATED.value(), HttpStatus.BAD_REQUEST.value(), "erroDossieSiecm.json", "sucessoDocumentoIncluirSiecm.json", "siecmDocumentoConsultarErro.json");
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
	
	@Test
	@Tag("anexoDocumento")
	void anexoDocumentoObrigatorioTest() throws StreamReadException, DatabindException, IOException {
		anexoDocumento(ANEXA_DOCUMENTO_OBRIGATORIO);
	}
	
	@Test
	@Tag("anexoDocumento")
	void anexoDocumentoAceiteTest() throws StreamReadException, DatabindException, IOException {
		anexoDocumento(ANEXA_DOCUMENTO_ACEITE);
	}
	
	@Test
	@Tag("anexoDocumento")
	void tipoDocumentoPFPJTest() throws StreamReadException, DatabindException, IOException {
		tipoDocumento();
	}
	
	@Test
	@Tag("anexoDocumento")
	void tipoDocumentoCamposTest() throws IOException, URISyntaxException {
		tipoDocumentoCampos();
	}

}
