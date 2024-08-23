package br.gov.caixa.siavl.atendimentoremoto.controller.protocolo;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;

@SuppressWarnings("all")
class AtendimentoRemotoControllerProtocoloTest extends ControllerTest {

	void geraProtocolo(String geraProtocoloInpuDtoFile) throws StreamReadException, DatabindException, IOException {
		String BASE_URL = atdremotoUrl + "/protocolo";
		GeraProtocoloInputDTO geraProtocoloInputDTO = mapper.readValue(
				new ClassPathResource("/geraProtocolo/"+geraProtocoloInpuDtoFile+".json").getFile(),
				GeraProtocoloInputDTO.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(geraProtocoloInputDTO), Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
