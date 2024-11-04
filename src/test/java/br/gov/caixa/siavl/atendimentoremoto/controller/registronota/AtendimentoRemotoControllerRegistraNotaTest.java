package br.gov.caixa.siavl.atendimentoremoto.controller.registronota;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;

@SuppressWarnings("all")
class AtendimentoRemotoControllerRegistraNotaTest extends ControllerTest {

	void registraNotaPF() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/nota/9484";
		RegistraNotaInputDto registraNotaInputDto = mapper.readValue(
				new ClassPathResource("/registraNota/1registraNotaInputDto.json").getFile(),
				RegistraNotaInputDto.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(registraNotaInputDto), Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	void registraNotaPJ() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/nota/9484";
		RegistraNotaInputDto registraNotaInputDto = mapper.readValue(
				new ClassPathResource("/registraNota/2registraNotaInputDto.json").getFile(),
				RegistraNotaInputDto.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(registraNotaInputDto), Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	void enviaNotaPJ1() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/nota/137984";

		EnviaClienteInputDto enviaClienteInputDto = mapper.readValue(
				new ClassPathResource("/registraNota/3enviaClienteInputDto.json").getFile(),
				EnviaClienteInputDto.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT,
				newRequestEntity(enviaClienteInputDto), Object.class);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	void enviaNotaPJ2() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/nota/137984";

		EnviaClienteInputDto enviaClienteInputDto = mapper.readValue(
				new ClassPathResource("/registraNota/4enviaClienteInputDto.json").getFile(),
				EnviaClienteInputDto.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT,
				newRequestEntity(enviaClienteInputDto), Object.class);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	void enviaNotaPJ3() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/nota/138042";

		EnviaClienteInputDto enviaClienteInputDto = mapper.readValue(
				new ClassPathResource("/registraNota/5enviaClienteInputDto.json").getFile(),
				EnviaClienteInputDto.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT,
				newRequestEntity(enviaClienteInputDto), Object.class);

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

}
