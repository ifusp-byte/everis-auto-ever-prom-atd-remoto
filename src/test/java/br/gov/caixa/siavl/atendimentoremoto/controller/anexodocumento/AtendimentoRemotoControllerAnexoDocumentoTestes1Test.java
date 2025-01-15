package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumento;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.ANEXA_DOCUMENTO_ACEITE;
import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.ANEXA_DOCUMENTO_OBRIGATORIO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.ANEXA_DOCUMENTO_OPCIONAL;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerAnexoDocumentoTestes1Test extends AtendimentoRemotoControllerAnexoDocumentoTest {

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
	void anexoDocumentoOpcionalTest() throws StreamReadException, DatabindException, IOException {
		anexoDocumento(ANEXA_DOCUMENTO_OPCIONAL);
	}
	
	@Test
	void anexoDocumentoObrigatorioTest() throws StreamReadException, DatabindException, IOException {
		anexoDocumento(ANEXA_DOCUMENTO_OBRIGATORIO);
	}
	
	@Test
	void anexoDocumentoAceiteTest() throws StreamReadException, DatabindException, IOException {
		anexoDocumento(ANEXA_DOCUMENTO_ACEITE);
	}
	
	@Test
	void tipoDocumentoPFPJTest() throws StreamReadException, DatabindException, IOException {
		tipoDocumento();
	}
	
	@Test
	void tipoDocumentoCamposTest() throws IOException, URISyntaxException {
		tipoDocumentoCampos();
	}

}
