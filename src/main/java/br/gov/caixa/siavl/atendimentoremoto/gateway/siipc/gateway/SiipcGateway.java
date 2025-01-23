package br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.gateway;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.ValidaDesafioDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.enums.SiipcUrlEnum;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@SuppressWarnings("all")
public class SiipcGateway {

	@Autowired
	public
	DataUtils dataUtils;

	@Autowired
	public
	MetodosUtils metodosUtils;

	@Autowired
	ValidateGateway validateGateway;

	@Autowired
	public
	RestTemplateUtils restTemplateUtils;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	private static final Logger LOG = Logger.getLogger(SiipcGateway.class.getName());

	private static String API_KEY = "APIKey";

	@Value("${env.apimanager.key}")
	private String API_KEY_VALUE;

	@Value("${env.apimanager.url}")
	private String URL_BASE;

	public HttpEntity<HashMap<String, String>> newRequestEntityDesafioValidar(String token,
			HashMap<String, String> validaDesafioMap) {
		return new HttpEntity<HashMap<String, String>>(validaDesafioMap, newHttpHeaders(token));
	}

	public HttpEntity<HashMap<String, String>> newRequestEntityDesafioCriar(String token,
			HashMap<String, String> criaDesafioMap) {
		return new HttpEntity<HashMap<String, String>>(criaDesafioMap, newHttpHeaders(token));
	}

	public HttpEntity<String> newRequestEntityDesafioResponder(String token,
			RespondeDesafioInputDTO respondeDesafioInputDTO) {

		HashMap<String, Object> respondeDesafioMap = new HashMap<String, Object>();

		String request = null;
		respondeDesafioMap.put("listaResposta", respondeDesafioInputDTO.getListaResposta());
		request = metodosUtils.writeValueAsString(respondeDesafioMap).replaceAll("\\u005C", "").replaceAll("\\n", "");
		return new HttpEntity<>(request, newHttpHeaders(token));
	}

	public HttpHeaders newHttpHeaders(String token) {

		String sanitizedToken = StringUtils.normalizeSpace(token);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		headers.set(API_KEY, API_KEY_VALUE);

		return headers;
	}

	public ValidaDesafioDTO desafioValidar(@Valid String token, HashMap<String, String> validaDesafioMap) {

		ValidaDesafioDTO validaDesafioDTO = new ValidaDesafioDTO();
		ResponseEntity<String> response = null;
		JsonNode body;
		String codigo422 = null;

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			response = restTemplateDto.getRestTemplate().postForEntity(
					URL_BASE + SiipcUrlEnum.DESAFIO_VALIDAR_URL_BASE_1.getUrl(),
					newRequestEntityDesafioCriar(token, validaDesafioMap), String.class);

			body = metodosUtils.readTree(String.valueOf(response.getBody()));

			String canal = String.valueOf(Objects.requireNonNull(body.path("canal")).asText());
			String tsAtualizacao = String.valueOf(Objects.requireNonNull(body.path("tsAtualizacao")).asText());
			String status = String.valueOf(Objects.requireNonNull(body.path("status")).asText());

			LOG.info("Identificação Positiva - Desafio Validar - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(response));

			String statusMessage = validateGateway.validateGatewayStatusDesafioCriar(
					Objects.requireNonNull(response.getStatusCodeValue()), StringUtils.EMPTY);

			validaDesafioDTO = ValidaDesafioDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).canal(canal)
					.tsAtualizacao(tsAtualizacao).status(status).statusMessage(statusMessage).statusCreated(true)
					.dataCreated(dataUtils.formataData(new Date())).build();

			LOG.info("Identificação Positiva - Desafio Validar - Resposta View "
					+ metodosUtils.writeValueAsString(validaDesafioDTO));

		} catch (RestClientResponseException e) {

			String statusMessage = null;

			body = metodosUtils.readTree(e.getResponseBodyAsString());

			LOG.info("Identificação Positiva - Desafio Validar - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(body));

			codigo422 = Objects.requireNonNull(body.path("codigo").asText());

			statusMessage = validateGateway
					.validateGatewayStatusDesafioCriar(Objects.requireNonNull(e.getRawStatusCode()), codigo422);

			validaDesafioDTO = ValidaDesafioDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.statusMessage(statusMessage).statusCreated(false).dataCreated(dataUtils.formataData(new Date()))
					.build();

			LOG.info("Identificação Positiva - Desafio Validar - Resposta View "
					+ metodosUtils.writeValueAsString(validaDesafioDTO));

		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}

			return validaDesafioDTO;
		}

	}

	public CriaDesafioOutputDTO desafioCriar(@Valid String token, HashMap<String, String> criaDesafioMap, Long cpfSocio,
			Long protocolo) {

		CriaDesafioOutputDTO criaDesafioOutputDTO = new CriaDesafioOutputDTO();
		ResponseEntity<String> response = null;
		JsonNode jsonNode;
		String codigo422 = null;

		if (cpfSocio != null) {
			Optional<AtendimentoCliente> atendimentoClienteOpt = atendimentoClienteRepository
					.findByProtocolo(protocolo);

			if (atendimentoClienteOpt.isPresent()) {
				AtendimentoCliente atendimentoCliente = atendimentoClienteOpt.get();
				atendimentoCliente.setCpfCliente(cpfSocio);
				atendimentoClienteRepository.save(atendimentoCliente);
			}

		}

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			response = restTemplateDto.getRestTemplate().postForEntity(
					URL_BASE + SiipcUrlEnum.DESAFIO_CRIAR_URL_BASE_1.getUrl(),
					newRequestEntityDesafioCriar(token, criaDesafioMap), String.class);

			LOG.info("Identificação Positiva - Desafio Criar - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(response));

			String statusMessage = validateGateway.validateGatewayStatusDesafioCriar(
					Objects.requireNonNull(response.getStatusCodeValue()), StringUtils.EMPTY);

			criaDesafioOutputDTO = CriaDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).statusMessage(statusMessage)
					.statusCreated(true).dataCreated(dataUtils.formataData(new Date())).build();

			LOG.info("Identificação Positiva - Desafio Criar - Resposta View "
					+ metodosUtils.writeValueAsString(criaDesafioOutputDTO));

		} catch (RestClientResponseException e) {

			String statusMessage = null;

			jsonNode = metodosUtils.readTree(e.getResponseBodyAsString());

			LOG.info("Identificação Positiva - Desafio Criar - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(jsonNode));

			codigo422 = Objects.requireNonNull(jsonNode.path("codigo").asText());

			statusMessage = validateGateway
					.validateGatewayStatusDesafioCriar(Objects.requireNonNull(e.getRawStatusCode()), codigo422);

			criaDesafioOutputDTO = CriaDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.statusMessage(statusMessage).statusCreated(false).dataCreated(dataUtils.formataData(new Date()))
					.build();

			LOG.info("Identificação Positiva - Desafio Criar - Resposta View "
					+ metodosUtils.writeValueAsString(criaDesafioOutputDTO));

		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}

			return criaDesafioOutputDTO;
		}

	}

	public RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio,
			RespondeDesafioInputDTO respondeDesafioInputDTO) {

		RespondeDesafioOutputDTO respondeDesafioOutputDTO = new RespondeDesafioOutputDTO();
		ResponseEntity<String> response = null;
		JsonNode jsonNode;
		String codigo422 = null;
		
		Optional<AtendimentoCliente> atendimentoClienteOpt = atendimentoClienteRepository
				.findByProtocolo(Long.parseLong(respondeDesafioInputDTO.getProtocolo()));

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			response = restTemplateDto.getRestTemplate()
					.postForEntity(URL_BASE + SiipcUrlEnum.DESAFIO_RESPONDER_URL_BASE_1.getUrl()
							+ Integer.parseInt(idDesafio.trim()) + SiipcUrlEnum.DESAFIO_RESPONDER_URL_BASE_2.getUrl(),
							newRequestEntityDesafioResponder(token, respondeDesafioInputDTO), String.class);

			LOG.info("Identificação Positiva - Desafio Responder - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(response));

			String statusMessage = validateGateway.validateGatewayStatusDesafioResponder(
					Objects.requireNonNull(response.getStatusCodeValue()), StringUtils.EMPTY);

			respondeDesafioOutputDTO = RespondeDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).statusMessage(statusMessage)
					.statusCreated(true).dataCreated(dataUtils.formataData(new Date())).build();

			if (atendimentoClienteOpt.isPresent()) {
				AtendimentoCliente atendimentoCliente = atendimentoClienteOpt.get();
				atendimentoCliente.setSituacaoIdPositiva(1L);
				atendimentoCliente.setDescricaoIdentificacaoPositiva("SUCESSO");
				atendimentoCliente.setDataIdentificacaoPositiva(dataUtils.formataDataBanco());
				atendimentoCliente.setDataValidacaoPositiva(dataUtils.formataDataBanco());
				atendimentoClienteRepository.save(atendimentoCliente);
			}

			LOG.info("Identificação Positiva - Desafio Responder - Resposta View "
					+ metodosUtils.writeValueAsString(respondeDesafioOutputDTO));

		} catch (RestClientResponseException e) {

			jsonNode = metodosUtils.readTree(e.getResponseBodyAsString());

			LOG.info("Identificação Positiva - Desafio Responder - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(jsonNode));

			codigo422 = Objects.requireNonNull(jsonNode.path("codigo").asText());
			String statusMessage = validateGateway
					.validateGatewayStatusDesafioResponder(Objects.requireNonNull(e.getRawStatusCode()), codigo422);

			respondeDesafioOutputDTO = RespondeDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.response(Objects.requireNonNull(e.getResponseBodyAsString())).statusMessage(statusMessage)
					.statusCreated(false).dataCreated(dataUtils.formataData(new Date())).build();

			if (atendimentoClienteOpt.isPresent()) {
				AtendimentoCliente atendimentoCliente = atendimentoClienteOpt.get();
				atendimentoCliente.setSituacaoIdPositiva(2L);
				atendimentoCliente.setDescricaoIdentificacaoPositiva("BLOQUEADO");
				atendimentoCliente.setDataIdentificacaoPositiva(dataUtils.formataDataBanco());
				atendimentoCliente.setDataValidacaoPositiva(dataUtils.formataDataBanco());
				atendimentoClienteRepository.save(atendimentoCliente);
			}

			LOG.info("Identificação Positiva - Desafio Responder - Resposta View "
					+ metodosUtils.writeValueAsString(respondeDesafioOutputDTO));

		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}

			return respondeDesafioOutputDTO;
		}

	}

}
