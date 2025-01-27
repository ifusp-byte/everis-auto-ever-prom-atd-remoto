package br.gov.caixa.siavl.atendimentoremoto.controller.registronota;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.CampoModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaFavoritoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.NotaNegociacaoRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.service.impl.RegistroNotaServiceImpl;

@SuppressWarnings("all")
class AtendimentoRemotoControllerRegistraNotaTest extends ControllerTest {

	@SpyBean
	NotaNegociacaoRepositoryImpl notaNegociacaoRepositoryImpl;

	@Mock
	private ModeloNotaFavoritoRepository modeloNotaFavoritoRepository;

	@Mock
	private CampoModeloNotaRepository campoModeloNotaRepository;

	@Mock
	private NotaNegociacaoRepository notaNegociacaoRepository;

	@InjectMocks
	private RegistroNotaServiceImpl service;

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

	@Test
	@Tag("registraNota")
	void atualizaXml1Test() throws Exception {
		Long numeroModeloNota = 1L;
		Long numeroNota = 1L;
		RegistraNotaInputDto registraNotaInputDto = mock(RegistraNotaInputDto.class);
		JsonNode relatorioNota = mock(JsonNode.class);
		when(registraNotaInputDto.getRelatorioNota()).thenReturn(relatorioNota);
		Object[] dinamico1 = { 1, 2, 3, "campo1", "1", "1", "1", "espaco", "DATA", "String", "descricao", "255", "255", "255" };
		Object[] dinamico2 = { 4, 5, 6, "campo2", "0", "1", "0", "espaco", "TEXTO", "String", "descricao2", "255", "255", "255" };
		List<Object[]> modeloNota = Arrays.asList(dinamico1, dinamico2);
		when(modeloNotaFavoritoRepository.modeloNotaDinamico(numeroModeloNota)).thenReturn(modeloNota);
		Object[] campo1 = { 1, "descricao1" };
		Object[] campo2 = { 2, "descricao2" };
		List<Object[]> campos = Arrays.asList(campo1, campo2);
		when(campoModeloNotaRepository.modeloNotaDinamicoCampos(anyLong())).thenReturn(campos);
		doNothing().when(notaNegociacaoRepository).updateXmlDataById(eq(numeroNota), anyString());
		service.atualizarXML(registraNotaInputDto, numeroModeloNota, numeroNota);
		verify(notaNegociacaoRepository).updateXmlDataById(eq(numeroNota), anyString());
	}

	@Test
	@Tag("registraNota")
	void atualizaXml2Test() throws Exception {
		Long numeroModeloNota = 1L;
		Long numeroNota = 1L;
		RegistraNotaInputDto registraNotaInputDto = mock(RegistraNotaInputDto.class);
		JsonNode relatorioNota = mock(JsonNode.class);
		when(registraNotaInputDto.getRelatorioNota()).thenReturn(relatorioNota);
		when(relatorioNota.get(anyString())).thenReturn(null);
		Object[] dinamico1 = { 1, 2, 3, "campo1", "1", "1", "1", "espaco", "DATA", "String", "descricao", "255", "255", "255" };
		Object[] dinamico2 = { 4, 5, 6, "campo2", "0", "1", "0", "espaco", "TEXTO", "String", "descricao2", "255", "255", "255" };
		List<Object[]> modeloNota = Arrays.asList(dinamico1, dinamico2);
		when(modeloNotaFavoritoRepository.modeloNotaDinamico(numeroModeloNota)).thenReturn(modeloNota);
		Object[] campo1 = { 1, "descricao1" };
		Object[] campo2 = { 2, "descricao2" };
		List<Object[]> campos = Arrays.asList(campo1, campo2);
		when(campoModeloNotaRepository.modeloNotaDinamicoCampos(anyLong())).thenReturn(campos);
		doNothing().when(notaNegociacaoRepository).updateXmlDataById(anyLong(), anyString());
		service.atualizarXML(registraNotaInputDto, numeroModeloNota, numeroNota);
		verify(notaNegociacaoRepository).updateXmlDataById(eq(numeroNota), anyString());
	}

	@Test
	@Tag("registraNota")
	void atualizaXml3Test() throws Exception {
		Long numeroModeloNota = 1L;
		Long numeroNota = 1L;
		RegistraNotaInputDto registraNotaInputDto = mock(RegistraNotaInputDto.class);
		JsonNode relatorioNota = mock(JsonNode.class);
		when(registraNotaInputDto.getRelatorioNota()).thenReturn(relatorioNota);
		Object[] dinamico1 = { 1, 2, 3, "campo1", "1", "1", "1", "espaco", "DATA", "String", "descricao", "255", "255", "255" };
		Object[] dinamico2 = { 4, 5, 6, "campo2", "0", "1", "0", "espaco", "TEXTO", "String", "descricao2", "255", "255", "255" };
		List<Object[]> modeloNota = Arrays.asList(dinamico1, dinamico2);
		when(modeloNotaFavoritoRepository.modeloNotaDinamico(numeroModeloNota)).thenReturn(modeloNota);
		Object[] campo1 = { 1, "descricao1" };
		Object[] campo2 = { 2, "descricao2" };
		List<Object[]> campos = Arrays.asList(campo1, campo2);
		when(campoModeloNotaRepository.modeloNotaDinamicoCampos(anyLong())).thenReturn(campos);
		doThrow(new RuntimeException(new JAXBException("Erro no JAXB"))).when(notaNegociacaoRepository).updateXmlDataById(anyLong(), anyString());

		try {
			service.atualizarXML(registraNotaInputDto, numeroModeloNota, numeroNota);
			fail("Deveria ter lançado uma exceção JAXBException");
		} catch (RuntimeException e) {

			assertTrue(e.getCause() instanceof JAXBException);
			assertEquals("Erro no JAXB", e.getCause().getMessage());
		}
		verify(notaNegociacaoRepository).updateXmlDataById(eq(numeroNota), anyString());
	}
}
