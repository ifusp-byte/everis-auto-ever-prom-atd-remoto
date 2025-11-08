package br.gov.caixa.siavl.autorizacaoenviomsg.controller;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("all")
class AutorizacaoEnvioMsgControllerTest extends ControllerTest {

	void autorizacao() throws Exception {
		String BASE_URL = autorizacaoenviomsg + "/autorizacao/123456";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	void creditos() throws Exception {
		String BASE_URL = autorizacaoenviomsg + "/creditos";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	void saldo() throws Exception {
		String BASE_URL = autorizacaoenviomsg + "/saldo";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
