package br.gov.caixa.siavl.atendimentoremoto.controller.tokensms;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.TOKEN_VALIDO;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.BASE_URL;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@SuppressWarnings("all")
@RequestMapping(BASE_URL)
@AutoConfigureWireMock(port = 0)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "env.url.ged.api=http://localhost:6060" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ControllerTest {

	public static final String AUDITORIA_URL = "/plataforma-unificada/trilha/v1/registros";

	static WireMockServer wireMockServer;

	@LocalServerPort
	public int port;

	public String defaultUrl = "http://localhost";
	public static RestTemplate restTemplate;
	public static ObjectMapper mapper;
	public String atdremotoUrl = null;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	public void setUpTest() {
		atdremotoUrl = defaultUrl.concat(":").concat(port + "").concat(BASE_URL);
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibility(
				VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.DEFAULT));
	}

	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	public void setupIntegracao() {
		wireMockServer = new WireMockServer(wireMockConfig().dynamicPort().port(6060).bindAddress("localhost"));
		wireMockServer.start();
		WireMock.configureFor("localhost", wireMockServer.port());

		stubFor(WireMock.post(urlPathMatching(AUDITORIA_URL))
				.willReturn(aResponse().withStatus(200)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("sucessoAuditoriaPnc.json")));
	}

	public void tearDownIntegracao() throws Exception {
		wireMockServer.stop();
	}

	public HttpEntity<?> newRequestEntity(Object object) {
		return new HttpEntity<Object>(object, newHttpHeaders());
	}

	public HttpEntity<?> newRequestEntity() {
		return new HttpEntity<String>(newHttpHeaders());
	}

	public HttpHeaders newHttpHeaders() {
		String sanitizedToken = StringUtils.normalizeSpace(TOKEN_VALIDO);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		return headers;
	}

}
