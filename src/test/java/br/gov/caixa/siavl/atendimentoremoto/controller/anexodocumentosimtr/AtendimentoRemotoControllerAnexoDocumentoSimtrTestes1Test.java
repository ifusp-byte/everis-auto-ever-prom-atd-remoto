package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumentosimtr;

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
class AtendimentoRemotoControllerAnexoDocumentoSimtrTestes1Test extends AtendimentoRemotoControllerAnexoDocumentoSimtrTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.OK.value(), HttpStatus.OK.value(), HttpStatus.OK.value(), "simtrTokenSucesso.json", "simtrDocumentosSucesso", "simtrDocumentoByIdSucesso", "10020030088", "1837034");
	}
	
	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}
	
	@Test
	@Tag("anexodocumentosimtr")
	void anexoDocumentoOpcionalTest() throws StreamReadException, DatabindException, IOException {
		anexoDocumentoSimtr();
	}
	
	

}
