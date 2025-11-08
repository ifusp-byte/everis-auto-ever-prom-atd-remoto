package br.gov.caixa.siavl.atendimentoremoto.controller.modelos;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("all")
class AtendimentoRemotoControllerModeloNotaTest extends ControllerTest {

	void modelosNotaSemProduto() {
		String BASE_URL = atdremotoUrl + "/modelos";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	void detalhesModelo1() {
		String BASE_URL = atdremotoUrl + "/modelos/2923";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	
	void detalhesModelo2() {
		String BASE_URL = atdremotoUrl + "/modelos/2803";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
