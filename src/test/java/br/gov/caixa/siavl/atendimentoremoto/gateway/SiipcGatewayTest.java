package br.gov.caixa.siavl.atendimentoremoto.gateway;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.TOKEN_VALIDO;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.ValidaDesafioDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.gateway.SiipcGateway;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.gateway.ValidateGateway;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@SuppressWarnings("all")
@SpringBootTest
class SiipcGatewayTest {

	@Mock
	private DataUtils dataUtils;

	@Mock
	private MetodosUtils metodosUtils;

	@InjectMocks
	private SiipcGateway siipcGateway;

	@Mock
	private ValidateGateway validateGateway;

	@MockBean
	private RestTemplateDto restTemplateDto;

	@Mock
	private RestTemplateUtils restTemplateUtils;

	@Mock
	AtendimentoClienteRepository mockAtendimentoClienteRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		siipcGateway = new SiipcGateway();
		siipcGateway.dataUtils = dataUtils;
		siipcGateway.metodosUtils = metodosUtils;
		siipcGateway.restTemplateUtils = restTemplateUtils;
	}

	@Test
	@Tag("desafio")
	void siipcGateway1Test() throws IOException {
		HashMap<String, String> validaDesafioMap = new HashMap<>();
		RestClientResponseException exception = mock(RestClientResponseException.class);
		RestTemplateDto restTemplateDto = mock(RestTemplateDto.class);
		RestTemplate mockRestTemplate = mock(RestTemplate.class);
		CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
		when(restTemplateUtils.newRestTemplate()).thenReturn(restTemplateDto);
		when(restTemplateDto.getRestTemplate()).thenReturn(mockRestTemplate);
		when(restTemplateDto.getHttpClient()).thenReturn(mockHttpClient);
		when(mockRestTemplate.postForEntity(anyString(), any(), eq(String.class))).thenThrow(exception);
		JsonNode mockJsonNode = mock(JsonNode.class);
		when(metodosUtils.readTree(anyString())).thenReturn(mockJsonNode);
		ValidaDesafioDTO result = siipcGateway.desafioValidar(TOKEN_VALIDO, validaDesafioMap);
		verify(mockHttpClient, times(1)).close();
		assertNotNull(result);
		assertFalse(Boolean.parseBoolean(result.getStatusCode()));
	}

	@Test
	@Tag("desafio")
	void siipcGateway2Test() throws IOException {
		HashMap<String, String> validaDesafioMap = new HashMap<>();
		RestTemplateDto restTemplateDto = mock(RestTemplateDto.class);
		RestTemplate mockRestTemplate = mock(RestTemplate.class);
		CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
		when(restTemplateUtils.newRestTemplate()).thenReturn(restTemplateDto);
		when(restTemplateDto.getRestTemplate()).thenReturn(mockRestTemplate);
		when(restTemplateDto.getHttpClient()).thenReturn(mockHttpClient);
		when(mockRestTemplate.postForEntity(anyString(), any(), eq(String.class)))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));
		siipcGateway.desafioValidar(TOKEN_VALIDO, validaDesafioMap);
		verify(mockHttpClient, times(1)).close();
	}

}