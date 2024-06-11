package br.gov.caixa.siavl.atendimentoremoto.siecm.gateway;

import java.util.logging.Logger;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.DossieDadosRequisicaoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.DossieInputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

public class SiecmGateway {

	private final static Logger LOG = Logger.getLogger(SiecmGateway.class.getName());

	private static String API_KEY = "apikey";
	private static String API_KEY_VALUE = "l7xx2b6f4c64f3774870b0b9b399a77586f5";

	private static String SIECM_URL_BASE = "http://siecm.des.caixa/siecm-web/ECM/v1/";
	private static String SIECM_URL_BASE_DOSSIE = "dossie";
	private static String SIECM_URL_BASE_LISTAR_DOCUMENTOS = "listar-documentos";
	private static String SIECM_URL_BASE_DOCUMENTOS_INCLUIR = "documentos/incluir";

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

	public HttpEntity<String> newRequestEntityDossieCriar(String token, DossieInputDto dossieInputDto) {
		String request = null;
		try {
			request = mapper.writeValueAsString(dossieInputDto).replaceAll("\\u005C", "").replaceAll("\\n", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return new HttpEntity<>(request, newHttpHeaders(token));
	}

	public SiecmOutputDto dossieCriar(@Valid String token, String cpfCnpj) throws Exception {

		String cpfCnpjDossie = cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim();

		DossieDadosRequisicaoInputDto dossieDadosRequisicaoInputDto = new DossieDadosRequisicaoInputDto();
		dossieDadosRequisicaoInputDto.setIpUsuarioFinal(DEFAULT_IP);
		dossieDadosRequisicaoInputDto.setLocalArmazenamento(DEFAULT_LOCAL_ARMAZENAMENTO);

		DossieInputDto dossieInputDto = new DossieInputDto();
		dossieInputDto.setIdDossie(cpfCnpjDossie);
		dossieInputDto.setDadosRequisicao(dossieDadosRequisicaoInputDto);

		SiecmOutputDto siecmOutputDto = new SiecmOutputDto();
		ResponseEntity<String> response = null;
		JsonNode jsonNode;
		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {
			response = restTemplateDto.getRestTemplate().postForEntity(SIECM_URL_BASE + SIECM_URL_BASE_DOSSIE,
					newRequestEntityDossieCriar(token, dossieInputDto), String.class);
		} catch (RestClientResponseException e) {
			jsonNode = mapper.readTree(e.getResponseBodyAsString());
		} finally {
			restTemplateDto.getHttpClient().close();
		}
		return siecmOutputDto;
	}
	
	
	/*
	public SiecmOutputDto dossieListar(@Valid String token, String cpfCnpj) throws Exception {

		String cpfCnpjDossie = cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim();

		DossieDadosRequisicaoInputDto dossieDadosRequisicaoInputDto = new DossieDadosRequisicaoInputDto();
		dossieDadosRequisicaoInputDto.setIpUsuarioFinal(DEFAULT_IP);
		dossieDadosRequisicaoInputDto.setLocalArmazenamento(DEFAULT_LOCAL_ARMAZENAMENTO);

		DossieInputDto dossieInputDto = new DossieInputDto();
		dossieInputDto.setIdDossie(cpfCnpjDossie);
		dossieInputDto.setDadosRequisicao(dossieDadosRequisicaoInputDto);

		SiecmOutputDto siecmOutputDto = new SiecmOutputDto();
		ResponseEntity<String> response = null;
		JsonNode jsonNode;
		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {
			response = restTemplateDto.getRestTemplate().postForEntity(SIECM_URL_BASE + SIECM_URL_BASE_LISTAR_DOCUMENTOS,
					newRequestEntityDossieCriar(token, dossieInputDto), String.class);
		} catch (RestClientResponseException e) {
			jsonNode = mapper.readTree(e.getResponseBodyAsString());
		} finally {
			restTemplateDto.getHttpClient().close();
		}
		return siecmOutputDto;
	}
	
	*/

}
