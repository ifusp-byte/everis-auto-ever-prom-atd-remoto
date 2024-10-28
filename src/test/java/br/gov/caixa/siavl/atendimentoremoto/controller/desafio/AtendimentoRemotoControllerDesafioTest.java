package br.gov.caixa.siavl.atendimentoremoto.controller.desafio;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioInputDTO;

@SuppressWarnings("all")
class AtendimentoRemotoControllerDesafioTest extends ControllerTest {

	void desafioValidar() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/desafio-validar/" + "10020030088";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, newRequestEntity(), Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	void desafioCriarPF() throws StreamReadException, DatabindException, IOException {		
		String BASE_URL = atdremotoUrl + "/desafio-criar/" + "10020030088";
		CriaDesafioInputDTO criaDesafioInputDTO1 = mapper.readValue(new ClassPathResource("/desafio/1criaDesafioInputDTO.json").getFile(), CriaDesafioInputDTO.class);
		ResponseEntity<Object> response1 = restTemplate.exchange(BASE_URL, HttpMethod.POST, newRequestEntity(criaDesafioInputDTO1), Object.class);			
		Assertions.assertEquals(HttpStatus.OK, response1.getStatusCode());
	}
	
	void desafioCriarPJ() throws StreamReadException, DatabindException, IOException {	
		String BASE_URL = atdremotoUrl + "/desafio-criar/" + "10020030088";
		CriaDesafioInputDTO criaDesafioInputDTO2 = mapper.readValue(new ClassPathResource("/desafio/2criaDesafioInputDTO.json").getFile(), CriaDesafioInputDTO.class);
		ResponseEntity<Object> response2 = restTemplate.exchange(BASE_URL, HttpMethod.POST, newRequestEntity(criaDesafioInputDTO2), Object.class);		
		Assertions.assertEquals(HttpStatus.OK, response2.getStatusCode());
	}
	
	void desafioResponderPF() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/desafio-responder/" + "123456";
		RespondeDesafioInputDTO respondeDesafioInputDTO = mapper.readValue(new ClassPathResource("/desafio/1respondeDesafioInputDTO.json").getFile(), RespondeDesafioInputDTO.class);
		ResponseEntity<Object> response1 = restTemplate.exchange(BASE_URL, HttpMethod.POST, newRequestEntity(respondeDesafioInputDTO), Object.class);			
		Assertions.assertEquals(HttpStatus.OK, response1.getStatusCode());
	}
}
