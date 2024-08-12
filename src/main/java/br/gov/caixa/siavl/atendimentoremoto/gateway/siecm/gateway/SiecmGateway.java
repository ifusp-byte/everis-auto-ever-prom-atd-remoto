package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.gateway;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.DossieDadosRequisicaoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.DossieInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@Validated
@SuppressWarnings("all")
public class SiecmGateway {

	private static final Logger LOG = Logger.getLogger(SiecmGateway.class.getName());
	private static String API_KEY = "apikey";

	@Value("${env.apimanager.key}")
	private String API_KEY_VALUE;

	@Value("${env.url.ged.api}")
	private String URL_GED_API;

	private static String SIECM_URL_BASE_DOSSIE = "/dossie";
	private static String SIECM_URL_BASE_DOCUMENTOS_INCLUIR = "/documentos/incluir";

	private static String DEFAULT_IP = "127.0.0.1";
	private static String DEFAULT_LOCAL_ARMAZENAMENTO = "OS_CAIXA";

	@Autowired
	RestTemplateUtils restTemplateUtils;

	private static ObjectMapper mapper = new ObjectMapper();

	public HttpHeaders newHttpHeaders(String token) {
		String sanitizedToken = StringUtils.normalizeSpace(token);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		headers.set(API_KEY, API_KEY_VALUE);
		return headers;
	}

	public HttpEntity<String> newRequestEntityDossie(String token, DossieInputDto dossieInputDto) {
		String request = null;
		try {
			request = mapper.writeValueAsString(dossieInputDto).replaceAll("\\u005C", "").replaceAll("\\n", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return new HttpEntity<>(request, newHttpHeaders(token));
	}

	public HttpEntity<String> newRequestEntityDocumentoIncluir(String token, String requestAnexarDocumento) {
		return new HttpEntity<>(requestAnexarDocumento, newHttpHeaders(token));
	}

	public SiecmOutputDto dossieCriar(@Valid String token, String cpfCnpj) throws Exception {

		DossieDadosRequisicaoInputDto dossieDadosRequisicaoInputDto = new DossieDadosRequisicaoInputDto();
		dossieDadosRequisicaoInputDto.setIpUsuarioFinal(DEFAULT_IP);
		dossieDadosRequisicaoInputDto.setLocalArmazenamento(DEFAULT_LOCAL_ARMAZENAMENTO);

		DossieInputDto dossieInputDto = new DossieInputDto();
		dossieInputDto.setIdDossie(cpfCnpj);
		dossieInputDto.setDadosRequisicao(dossieDadosRequisicaoInputDto);

		SiecmOutputDto siecmOutputDto = new SiecmOutputDto();
		ResponseEntity<String> response = null;
		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		JsonNode body;

		try {
			response = restTemplateDto.getRestTemplate().postForEntity(URL_GED_API + SIECM_URL_BASE_DOSSIE,
					newRequestEntityDossie(token, dossieInputDto), String.class);

			siecmOutputDto = SiecmOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).build();
		} catch (RestClientResponseException e) {
			body = mapper.readTree(e.getResponseBodyAsString());

			siecmOutputDto = SiecmOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.response(String.valueOf(Objects.requireNonNull(e.getResponseBodyAsString()))).build();
		} finally {
			restTemplateDto.getHttpClient().close();
		}
		return siecmOutputDto;
	}

	public SiecmOutputDto documentoIncluir(@Valid String token, String cpfCnpj, String requestAnexarDocumento)
			throws Exception {

		SiecmOutputDto siecmOutputDto = new SiecmOutputDto();
		ResponseEntity<String> response = null;
		JsonNode body;
		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {
			response = restTemplateDto.getRestTemplate().postForEntity(URL_GED_API + SIECM_URL_BASE_DOCUMENTOS_INCLUIR,
					newRequestEntityDocumentoIncluir(token, requestAnexarDocumento), String.class);

			body = mapper.readTree(String.valueOf(response.getBody()));
			String linkThumbnail = Objects.requireNonNull(body.path("documento").path("atributos").path("link"))
					.asText();
			String id = Objects.requireNonNull(body.path("documento").path("atributos").path("id")).asText();

			siecmOutputDto = SiecmOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.linkThumbnail(linkThumbnail).statusCreated(true).statusMessage("Documento gravado com sucesso")
					.dataCreated(formataDataSiecm(new Date())).id(id).build();

		} catch (RestClientResponseException e) {
			body = mapper.readTree(e.getResponseBodyAsString());

			siecmOutputDto = SiecmOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode()))).build();
		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}
			return siecmOutputDto;
		}

	}

	private String formataDataSiecm(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

}
