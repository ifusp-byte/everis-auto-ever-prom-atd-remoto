package br.gov.caixa.siavl.atendimentoremoto.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

@SuppressWarnings("all")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@RequestMapping(AtendimentoRemotoController.BASE_URL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "env.url.ged.api=http://localhost:6065" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AtendimentoRemotoControllerTest {

	public static final String BASE_URL = "/v1/atendimento-remoto";

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
		mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.DEFAULT));
	}

	public HttpEntity<?> newRequestEntity() {
		return new HttpEntity<String>(newHttpHeaders());
	}

	public HttpHeaders newHttpHeaders() {
		String sanitizedToken = StringUtils.normalizeSpace("123456897");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		return headers;
	}

	@Test
	void tipoDocumentoCamposTest() throws IOException, URISyntaxException {
		String BASE_URL = atdremotoUrl + "/documento/tipo/campos/" + "CNH";
		ResponseEntity<Object> response = restTemplate.getForEntity(BASE_URL, Object.class);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

}
