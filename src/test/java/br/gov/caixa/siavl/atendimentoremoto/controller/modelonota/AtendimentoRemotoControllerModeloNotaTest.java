package br.gov.caixa.siavl.atendimentoremoto.controller.modelonota;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;

@SuppressWarnings("all")
class AtendimentoRemotoControllerModeloNotaTest extends ControllerTest {

	void consultaTipoNota() {
		String BASE_URL = atdremotoUrl + "/tipo-nota";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	void consultaModeloNota() {
		String BASE_URL = atdremotoUrl + "/modelo-nota/10020030088";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	void consultaModeloNotaMaisUtilizada() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-mais-utilizada/10020030088";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	void consultaModeloNotaFavorita() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-favorita/10020030088";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	void adicionaModeloNotaFavorita() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-favorita/" + 8787;
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	void modeloNotaDinamico1() throws StreamReadException, DatabindException, IOException {

		String BASE_URL_1 = atdremotoUrl + "/modelo-nota-dinamico/" + 8704;

		ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO1 = mapper.readValue(
				new ClassPathResource("/modeloNotaDinamico/1modeloNotaDinamicoInputDTO.json").getFile(),
				ModeloNotaDinamicoInputDTO.class);

		ResponseEntity<Object> response1 = restTemplate.exchange(BASE_URL_1, HttpMethod.POST,
				newRequestEntity(modeloNotaDinamicoInputDTO1), Object.class);

		Assertions.assertEquals(HttpStatus.CREATED, response1.getStatusCode());

	}

	void modeloNotaDinamico2() throws StreamReadException, DatabindException, IOException {

		String BASE_URL_2 = atdremotoUrl + "/modelo-nota-dinamico/" + 3123;

		ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO2 = mapper.readValue(
				new ClassPathResource("/modeloNotaDinamico/2modeloNotaDinamicoInputDTO.json").getFile(),
				ModeloNotaDinamicoInputDTO.class);

		ResponseEntity<Object> response2 = restTemplate.exchange(BASE_URL_2, HttpMethod.POST,
				newRequestEntity(modeloNotaDinamicoInputDTO2), Object.class);

		Assertions.assertEquals(HttpStatus.CREATED, response2.getStatusCode());

	}

	void modeloNotaDinamico3() throws StreamReadException, DatabindException, IOException {

		String BASE_URL_3 = atdremotoUrl + "/modelo-nota-dinamico/" + 3124;

		ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO3 = mapper.readValue(
				new ClassPathResource("/modeloNotaDinamico/3modeloNotaDinamicoInputDTO.json").getFile(),
				ModeloNotaDinamicoInputDTO.class);

		ResponseEntity<Object> response3 = restTemplate.exchange(BASE_URL_3, HttpMethod.POST,
				newRequestEntity(modeloNotaDinamicoInputDTO3), Object.class);

		Assertions.assertEquals(HttpStatus.CREATED, response3.getStatusCode());
	}

}
