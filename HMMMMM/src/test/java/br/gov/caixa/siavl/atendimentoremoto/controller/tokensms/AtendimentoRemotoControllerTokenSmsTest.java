package br.gov.caixa.siavl.atendimentoremoto.controller.tokensms;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.TokenSmsInputDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.NotaNegociacaoRepositoryImpl;

@SuppressWarnings("all")
class AtendimentoRemotoControllerTokenSmsTest extends ControllerTest {
	
	@MockitoSpyBean
	NotaNegociacaoRepositoryImpl notaNegociacaoRepositoryImpl;

	void identificacaoTokenSms() throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/token-sms";

		TokenSmsInputDto tokenSmsInputDto1 = mapper.readValue(
				new ClassPathResource("/tokensms/1tokenSmsInputDto.json").getFile(), TokenSmsInputDto.class);
		ResponseEntity<Object> response1 = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(tokenSmsInputDto1), Object.class);

		TokenSmsInputDto tokenSmsInputDto2 = mapper.readValue(
				new ClassPathResource("/tokensms/2tokenSmsInputDto.json").getFile(), TokenSmsInputDto.class);
		ResponseEntity<Object> response2 = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(tokenSmsInputDto2), Object.class);

		TokenSmsInputDto tokenSmsInputDto3 = mapper.readValue(
				new ClassPathResource("/tokensms/3tokenSmsInputDto.json").getFile(), TokenSmsInputDto.class);
		ResponseEntity<Object> response3 = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(tokenSmsInputDto3), Object.class);
		
		TokenSmsInputDto tokenSmsInputDto4 = mapper.readValue(
				new ClassPathResource("/tokensms/4tokenSmsInputDto.json").getFile(), TokenSmsInputDto.class);
		ResponseEntity<Object> response4 = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(tokenSmsInputDto4), Object.class);

		Assertions.assertEquals(HttpStatus.OK, response1.getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, response2.getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, response3.getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, response4.getStatusCode());
	}
	
	void mockNotasByProtocolo() {

		List<NotasByProtocoloOutputDTO> notasByProtocolo = new ArrayList<>();
		notasByProtocolo.add(new NotasByProtocoloOutputDTO((long) 123456, "nomeCliente", (long) 1234556, (long) 1234556,
				"produto", "situacaoNota", null, null));
		notasByProtocolo.add(new NotasByProtocoloOutputDTO((long) 123456, "nomeCliente", (long) 1234556, (long) 1234556,
				"produto", "situacaoNota", null, null));
		notasByProtocolo.add(new NotasByProtocoloOutputDTO((long) 123457, "nomeCliente", (long) 1234556, (long) 1234556,
				"produto", "situacaoNota", null, null));
		when(notaNegociacaoRepositoryImpl.notasByProtocolo(any())).thenReturn(notasByProtocolo);
		when(notaNegociacaoRepositoryImpl.notasByProtocoloTokenSms(any())).thenReturn(notasByProtocolo);

	}

}
