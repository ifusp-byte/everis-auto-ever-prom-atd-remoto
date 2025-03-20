package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumentosimtr;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerAnexoDocumentoSimtrTest extends ControllerTest {

	void consultaDossieSimtr() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/documentos/" + "10020030088";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
