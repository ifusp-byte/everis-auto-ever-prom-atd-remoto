package br.gov.caixa.siavl.atendimentoremoto.controller;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;

@Ignore
@SuppressWarnings("all")
class AtendimentoRemotoControllerTest extends ControllerTest {

	@BeforeEach
	public void beforeEach() {
		setUpTest();
	}

	//@Test
	void consultaModeloNotaTest() {
		String BASE_URL = atdremotoUrl + "/modelo-nota/10020030088";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	//@Test
	void consultaModeloNotaMaisUtilizadaTest() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-mais-utilizada/10020030088";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	//@Test
	void consultaModeloNotaFavoritaTest() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-favorita/10020030088";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	//@Test
	void adicionaModeloNotaFavoritaTest() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-favorita/" + 8787;
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	//@Test
	void modeloNotaDinamicoTest() throws StreamReadException, DatabindException, IOException {

		String BASE_URL_1 = atdremotoUrl + "/modelo-nota-dinamico/" + 8704;
		String BASE_URL_2 = atdremotoUrl + "/modelo-nota-dinamico/" + 3123;
		String BASE_URL_3 = atdremotoUrl + "/modelo-nota-dinamico/" + 3124;

		ModeloNotaDinamicoInputDTO odeloNotaDinamicoInputDTO1 = mapper.readValue(
				new ClassPathResource("/modeloNotaDinamico/1modeloNotaDinamicoInputDTO.json").getFile(),
				ModeloNotaDinamicoInputDTO.class);

		ModeloNotaDinamicoInputDTO odeloNotaDinamicoInputDTO2 = mapper.readValue(
				new ClassPathResource("/modeloNotaDinamico/2modeloNotaDinamicoInputDTO.json").getFile(),
				ModeloNotaDinamicoInputDTO.class);

		ModeloNotaDinamicoInputDTO odeloNotaDinamicoInputDTO3 = mapper.readValue(
				new ClassPathResource("/modeloNotaDinamico/3modeloNotaDinamicoInputDTO.json").getFile(),
				ModeloNotaDinamicoInputDTO.class);

		ResponseEntity<Object> response1 = restTemplate.exchange(BASE_URL_1, HttpMethod.POST,
				newRequestEntity(odeloNotaDinamicoInputDTO1), Object.class);

		ResponseEntity<Object> response2 = restTemplate.exchange(BASE_URL_2, HttpMethod.POST,
				newRequestEntity(odeloNotaDinamicoInputDTO2), Object.class);

		ResponseEntity<Object> response3 = restTemplate.exchange(BASE_URL_3, HttpMethod.POST,
				newRequestEntity(odeloNotaDinamicoInputDTO3), Object.class);

		Assertions.assertEquals(HttpStatus.CREATED, response1.getStatusCode());
		Assertions.assertEquals(HttpStatus.CREATED, response2.getStatusCode());
		Assertions.assertEquals(HttpStatus.CREATED, response3.getStatusCode());
	}
}
