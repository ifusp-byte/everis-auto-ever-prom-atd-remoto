package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumentosiecm;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.CLASSE_DOCUMENTOS;

import java.io.IOException;
import java.net.URISyntaxException;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaDocumentoInputDto;

@SuppressWarnings("all")
class AtendimentoRemotoControllerAnexoDocumentoSiecmTest extends ControllerTest {

	void anexoDocumento(String anexoDocumentoInputDtoFile) throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/documento/" + "10020030088";
		EnviaDocumentoInputDto enviaDocumentoInputDto = mapper.readValue(new ClassPathResource("/anexoDocumento/" + anexoDocumentoInputDtoFile + ".json").getFile(), EnviaDocumentoInputDto.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, newRequestEntity(enviaDocumentoInputDto), Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	void consultaDocumento() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/documento/" + "A0C86494-0000-CA11-AC4E-FA7EA5CF49BD";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, newRequestEntity(), Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	void tipoDocumento() throws StreamReadException, DatabindException, IOException {
		String BASE_URL_1 = atdremotoUrl + "/documento/tipo/" + "10020030088";
		String BASE_URL_2 = atdremotoUrl + "/documento/tipo/" + "13003498000198";
		ResponseEntity<Object> response1 = restTemplate.exchange(BASE_URL_1, HttpMethod.GET, newRequestEntity(), Object.class);
		ResponseEntity<Object> response2 = restTemplate.exchange(BASE_URL_2, HttpMethod.GET, newRequestEntity(), Object.class);
		Assertions.assertEquals(HttpStatus.OK, response1.getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, response2.getStatusCode());
	}

	void tipoDocumentoCampos() throws IOException, URISyntaxException {
		Arrays.asList(CLASSE_DOCUMENTOS).stream().forEach(documento -> {
			String BASE_URL = atdremotoUrl + "/documento/tipo/campos/" + documento;
			ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
			Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		});
	}

}
