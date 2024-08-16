package br.gov.caixa.siavl.atendimentoremoto.controller;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.CLASSE_DOCUMENTOS;
import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.TOKEN_VALIDO;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.BASE_URL;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;

@SuppressWarnings("all")
@RequestMapping(BASE_URL)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "env.url.ged.api=http://localhost:6065" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AtendimentoRemotoControllerTest {

	@LocalServerPort
	private int port;

	private String defaultUrl = "http://localhost";
	private static RestTemplate restTemplate;
	private static ObjectMapper mapper;
	private String atdremotoUrl = null;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		atdremotoUrl = defaultUrl.concat(":").concat(port + "").concat(BASE_URL);
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibility(
				VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.DEFAULT));
	}

	public HttpEntity<?> newRequestEntity() {
		return new HttpEntity<String>(newHttpHeaders());
	}

	public HttpEntity<?> newRequestEntity(ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO) {
		return new HttpEntity<ModeloNotaDinamicoInputDTO>(modeloNotaDinamicoInputDTO, newHttpHeaders());
	}

	public HttpHeaders newHttpHeaders() {
		String sanitizedToken = StringUtils.normalizeSpace(TOKEN_VALIDO);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		return headers;
	}

	@Test
	void tipoDocumentoCamposTest() throws IOException, URISyntaxException {
		Arrays.asList(CLASSE_DOCUMENTOS).stream().forEach(documento -> {
			String BASE_URL = atdremotoUrl + "/documento/tipo/campos/" + documento;
			ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
			Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		});
	}

	@Test
	void consultaModeloNotaTest() {
		String BASE_URL = atdremotoUrl + "/modelo-nota";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	/*
	@Test
	void consultaModeloNotaMaisUtilizadaTest() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-mais-utilizada";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	*/

	@Test
	void consultaModeloNotaFavoritaTest() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-favorita";
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void adicionaModeloNotaFavorita() {
		String BASE_URL = atdremotoUrl + "/modelo-nota-favorita/" + 8787;
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, newRequestEntity(),
				Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void modeloNotaDinamico() throws StreamReadException, DatabindException, IOException {

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
