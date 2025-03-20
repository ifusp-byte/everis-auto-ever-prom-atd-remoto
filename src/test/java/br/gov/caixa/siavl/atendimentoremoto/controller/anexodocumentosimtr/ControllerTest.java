package br.gov.caixa.siavl.atendimentoremoto.controller.anexodocumentosimtr;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.TOKEN_VALIDO;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.BASE_URL;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
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
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "env.url.ged.api=http://localhost:6060" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ControllerTest {

	private static String SIMTR_URL_BASE_DOCUMENTOS_CPF = "/negocio/v1/dossie-cliente/cpf/";
	private static String SIMTR_URL_BASE_DOCUMENTOS_CNPJ = "/negocio/v1/dossie-cliente/cnpj/";
	private static String SIMTR_URL_BASE_DOCUMENTO_ID = "/negocio/v2/documento/";
	private static String SIMTR_URL_BASE_DOCUMENTO_ID_FLAG_BINARIO = "\u003F" + "binario=true";
	private static String SIMTR_SIAVL_TOKEN_URL = "http://localhost:6061/auth/realms/intranet/protocol/openid-connect/token";
	static WireMockServer wireMockServer;
	static WireMockServer wireMockServerToken;
	static WireMockServer wireMockServerDocumento;

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

	public void setupIntegracao(int statusDocumentos, String simtrDocumentosRetorno, String cpfCnpj) {
		wireMockServer = new WireMockServer(wireMockConfig().dynamicPort().port(6068).bindAddress("localhost"));
		wireMockServer.start();
		WireMock.configureFor("localhost", wireMockServer.port());

		stubFor(WireMock.get(urlPathMatching(SIMTR_URL_BASE_DOCUMENTOS_CPF + cpfCnpj))
				.willReturn(aResponse().withStatus(statusDocumentos)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).withFixedDelay(500)
						.withBodyFile(simtrDocumentosRetorno)));

		stubFor(WireMock.get(urlPathMatching(SIMTR_URL_BASE_DOCUMENTOS_CNPJ + cpfCnpj))
				.willReturn(aResponse().withStatus(statusDocumentos)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile(simtrDocumentosRetorno)));

	}

	public void setupIntegracaoToken(int statusToken, String simtrTokenRetorno) {
		wireMockServerToken = new WireMockServer(wireMockConfig().dynamicPort().port(6061).bindAddress("localhost"));
		wireMockServerToken.start();
		WireMock.configureFor("localhost", wireMockServerToken.port());

		stubFor(WireMock.post(urlPathMatching(SIMTR_SIAVL_TOKEN_URL))
				.willReturn(aResponse().withStatus(statusToken)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile(simtrTokenRetorno)));

	}

	public void setupIntegracaoDocumento(int statusDocumentoById, String simtrDocumentoByIdRetorno,
			String idDocumento) {

		wireMockServerDocumento = new WireMockServer(
				wireMockConfig().dynamicPort().port(6068).bindAddress("localhost"));
		wireMockServerDocumento.start();
		WireMock.configureFor("localhost", wireMockServerDocumento.port());

		stubFor(WireMock
				.get(urlPathMatching(
						SIMTR_URL_BASE_DOCUMENTO_ID + idDocumento + SIMTR_URL_BASE_DOCUMENTO_ID_FLAG_BINARIO))
				.willReturn(aResponse().withStatus(statusDocumentoById)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile(simtrDocumentoByIdRetorno)));

	}

	public void tearDownIntegracaoDocumento() throws Exception {
		wireMockServerDocumento.stop();
		wireMockServerToken.stop();
	}

	public void tearDownIntegracaoDossie() throws Exception {
		wireMockServer.stop();
		wireMockServerToken.stop();
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
