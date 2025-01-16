package br.gov.caixa.siavl.atendimentoremoto.controller.tokensms;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.dto.TokenSmsInputDto;

@SuppressWarnings("all")
class AtendimentoRemotoControllerTokenSmsTest extends ControllerTest {

	void identificacaoTokenSms() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/token-sms";

		TokenSmsInputDto registraNotaInputDto1 = mapper.readValue(
				new ClassPathResource("/tokensms/1tokenSmsInputDto.json").getFile(), TokenSmsInputDto.class);
		ResponseEntity<Object> response1 = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(registraNotaInputDto1), Object.class);

		TokenSmsInputDto registraNotaInputDto2 = mapper.readValue(
				new ClassPathResource("/tokensms/2tokenSmsInputDto.json").getFile(), TokenSmsInputDto.class);
		ResponseEntity<Object> response2 = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(registraNotaInputDto2), Object.class);

		TokenSmsInputDto registraNotaInputDto3 = mapper.readValue(
				new ClassPathResource("/tokensms/3tokenSmsInputDto.json").getFile(), TokenSmsInputDto.class);
		ResponseEntity<Object> response3 = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(registraNotaInputDto3), Object.class);

		Assertions.assertEquals(HttpStatus.CREATED, response1.getStatusCode());
		Assertions.assertEquals(HttpStatus.CREATED, response2.getStatusCode());
		Assertions.assertEquals(HttpStatus.CREATED, response3.getStatusCode());
	}

}
