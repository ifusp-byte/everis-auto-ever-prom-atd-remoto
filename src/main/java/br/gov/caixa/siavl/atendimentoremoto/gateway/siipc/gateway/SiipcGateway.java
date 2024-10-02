package br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.gateway;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.constants.IdentificacaoPositivaGatewayMessages;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.ValidaDesafioOutptDTO;
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
	DataUtils dataUtils;

	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	RestTemplateUtils restTemplateUtils;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	private static final Logger LOG = Logger.getLogger(SiipcGateway.class.getName());

	private static String API_KEY = "APIKey";

	@Value("${env.apimanager.key}")
	private String API_KEY_VALUE;

	@Value("${env.apimanager.url}")
	private String URL_BASE;

	private static String CODIGO_422_0 = "0";
	private static String CODIGO_422_1 = "1";
	private static String CODIGO_422_2 = "2";
	private static String CODIGO_422_3 = "3";
	private static String CODIGO_422_4 = "4";
	private static String CODIGO_422_5 = "5";
	private static String CODIGO_422_6 = "6";

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

	public ValidaDesafioOutptDTO desafioValidar(@Valid String token, HashMap<String, String> validaDesafioMap) {

		ValidaDesafioOutptDTO validaDesafioOutptDTO = new ValidaDesafioOutptDTO();
		ResponseEntity<String> response = null;
		JsonNode body;
		String codigo422 = null;

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			response = restTemplateDto.getRestTemplate().postForEntity(
					URL_BASE + SiipcUrlEnum.DESAFIO_VALIDAR_URL_BASE_1.getUrl(),
					newRequestEntityDesafioCriar(token, validaDesafioMap), String.class);

			body = metodosUtils.readTree(String.valueOf(response.getBody()));

			//String id = Objects.requireNonNull(body.path("id")).asText();
			String canal = String.valueOf(Objects.requireNonNull(body.path("canal")).asText());
			String tsAtualizacao = String.valueOf(Objects.requireNonNull(body.path("tsAtualizacao")).asText());
			String status = String.valueOf(Objects.requireNonNull(body.path("status")).asText());

			LOG.info("Identificação Positiva - Desafio Validar - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(response));

			String statusMessage = validateGatewayStatusDesafioCriar(
					Objects.requireNonNull(response.getStatusCodeValue()), StringUtils.EMPTY);

			validaDesafioOutptDTO = ValidaDesafioOutptDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).canal(canal)
					.tsAtualizacao(tsAtualizacao).status(status).statusMessage(statusMessage).statusCreated(true)
					.dataCreated(dataUtils.formataData(new Date())).build();

			LOG.info("Identificação Positiva - Desafio Validar - Resposta View "
					+ metodosUtils.writeValueAsString(validaDesafioOutptDTO));

		} catch (RestClientResponseException e) {

			String statusMessage = null;

			try {

				body = metodosUtils.readTree(e.getResponseBodyAsString());

				LOG.info("Identificação Positiva - Desafio Validar - Resposta SIIPC "
						+ metodosUtils.writeValueAsString(body));

				codigo422 = Objects.requireNonNull(body.path("codigo").asText());

				statusMessage = validateGatewayStatusDesafioCriar(Objects.requireNonNull(e.getRawStatusCode()),
						codigo422);

			} catch (Exception e1) {

				validaDesafioOutptDTO = ValidaDesafioOutptDTO.builder()
						.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
						.statusMessage(statusMessage).statusCreated(false)
						.dataCreated(dataUtils.formataData(new Date())).build();

				LOG.info("Identificação Positiva - Desafio Validar - Erro " + statusMessage);

			}

			validaDesafioOutptDTO = ValidaDesafioOutptDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.statusMessage(statusMessage).statusCreated(false).dataCreated(dataUtils.formataData(new Date()))
					.build();

			LOG.info("Identificação Positiva - Desafio Validar - Resposta View "
					+ metodosUtils.writeValueAsString(validaDesafioOutptDTO));

		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}

			return validaDesafioOutptDTO;
		}

	}

	public CriaDesafioOutputDTO desafioCriar(@Valid String token, HashMap<String, String> criaDesafioMap, Long cpfSocio,
			Long protocolo) {

		CriaDesafioOutputDTO criaDesafioOutputDTO = new CriaDesafioOutputDTO();
		ResponseEntity<String> response = null;
		JsonNode jsonNode;
		String codigo422 = null;

		if (cpfSocio != null) {
			AtendimentoCliente atendimentoCliente = atendimentoClienteRepository.getReferenceById(protocolo);
			atendimentoCliente.setCpfCliente(cpfSocio);
			atendimentoClienteRepository.save(atendimentoCliente);
		}

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			response = restTemplateDto.getRestTemplate().postForEntity(
					URL_BASE + SiipcUrlEnum.DESAFIO_CRIAR_URL_BASE_1.getUrl(),
					newRequestEntityDesafioCriar(token, criaDesafioMap), String.class);

			LOG.info("Identificação Positiva - Desafio Criar - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(response));

			String statusMessage = validateGatewayStatusDesafioCriar(
					Objects.requireNonNull(response.getStatusCodeValue()), StringUtils.EMPTY);

			criaDesafioOutputDTO = CriaDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).statusMessage(statusMessage)
					.statusCreated(true).dataCreated(dataUtils.formataData(new Date())).build();

			LOG.info("Identificação Positiva - Desafio Criar - Resposta View "
					+ metodosUtils.writeValueAsString(criaDesafioOutputDTO));

		} catch (RestClientResponseException e) {

			String statusMessage = null;

			try {

				jsonNode = metodosUtils.readTree(e.getResponseBodyAsString());

				LOG.info("Identificação Positiva - Desafio Criar - Resposta SIIPC "
						+ metodosUtils.writeValueAsString(jsonNode));

				codigo422 = Objects.requireNonNull(jsonNode.path("codigo").asText());

				statusMessage = validateGatewayStatusDesafioCriar(Objects.requireNonNull(e.getRawStatusCode()),
						codigo422);

			} catch (Exception e1) {

				criaDesafioOutputDTO = CriaDesafioOutputDTO.builder()
						.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
						.statusMessage(statusMessage).statusCreated(false)
						.dataCreated(dataUtils.formataData(new Date())).build();

				LOG.info("Identificação Positiva - Desafio Criar - Erro " + statusMessage);

			}

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
		AtendimentoCliente atendimentoCliente = atendimentoClienteRepository
				.getReferenceById(Long.parseLong(respondeDesafioInputDTO.getProtocolo()));

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			response = restTemplateDto.getRestTemplate()
					.postForEntity(URL_BASE + SiipcUrlEnum.DESAFIO_RESPONDER_URL_BASE_1.getUrl()
							+ Integer.parseInt(idDesafio.trim()) + SiipcUrlEnum.DESAFIO_RESPONDER_URL_BASE_2.getUrl(),
							newRequestEntityDesafioResponder(token, respondeDesafioInputDTO), String.class);

			LOG.info("Identificação Positiva - Desafio Responder - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(response));

			String statusMessage = validateGatewayStatusDesafioResponder(
					Objects.requireNonNull(response.getStatusCodeValue()), StringUtils.EMPTY);

			respondeDesafioOutputDTO = RespondeDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).statusMessage(statusMessage)
					.statusCreated(true).dataCreated(dataUtils.formataData(new Date())).build();

			atendimentoCliente.setSituacaoIdPositiva(1L);
			atendimentoCliente.setDescricaoIdentificacaoPositiva("SUCESSO");
			atendimentoCliente.setDataIdentificacaoPositiva(dataUtils.formataDataBanco());
			atendimentoCliente.setDataValidacaoPositiva(dataUtils.formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);

			LOG.info("Identificação Positiva - Desafio Responder - Resposta View "
					+ metodosUtils.writeValueAsString(respondeDesafioOutputDTO));

		} catch (RestClientResponseException e) {

			jsonNode = metodosUtils.readTree(e.getResponseBodyAsString());

			LOG.info("Identificação Positiva - Desafio Responder - Resposta SIIPC "
					+ metodosUtils.writeValueAsString(jsonNode));

			codigo422 = Objects.requireNonNull(jsonNode.path("codigo").asText());
			String statusMessage = validateGatewayStatusDesafioResponder(Objects.requireNonNull(e.getRawStatusCode()),
					codigo422);

			respondeDesafioOutputDTO = RespondeDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.response(Objects.requireNonNull(e.getResponseBodyAsString())).statusMessage(statusMessage)
					.statusCreated(false).dataCreated(dataUtils.formataData(new Date())).build();

			atendimentoCliente.setSituacaoIdPositiva(2L);
			atendimentoCliente.setDescricaoIdentificacaoPositiva("BLOQUEADO");
			atendimentoCliente.setDataIdentificacaoPositiva(dataUtils.formataDataBanco());
			atendimentoCliente.setDataValidacaoPositiva(dataUtils.formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);

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

	public String validateGatewayStatusDesafioCriar(int statusCode, String codigo422) {

		String statusMessage = null;

		if (HttpStatus.CREATED.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_201;
		} else if (HttpStatus.BAD_REQUEST.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_400;
		} else if (HttpStatus.UNAUTHORIZED.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_401;
		} else if (HttpStatus.NOT_FOUND.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_404;
		} else if (HttpStatus.UNPROCESSABLE_ENTITY.value() == statusCode) {
			statusMessage = validateGatewayStatusDesafioCriarCodigo422(codigo422);
		} else if (HttpStatus.INTERNAL_SERVER_ERROR.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_500;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_DESCONHECIDO;
		}

		return statusMessage;
	}

	public String validateGatewayStatusDesafioCriarCodigo422(String codigo422) {

		String statusMessage = null;

		if (CODIGO_422_1.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_422_1;
		} else if (CODIGO_422_6.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_422_6;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_422_DESCONHECIDO;
		}

		return statusMessage;

	}

	public String validateGatewayStatusDesafioResponder(int statusCode, String codigo422) {

		String statusMessage = null;

		if (HttpStatus.OK.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_200;
		} else if (HttpStatus.BAD_REQUEST.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_400;
		} else if (HttpStatus.UNAUTHORIZED.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_401;
		} else if (HttpStatus.NOT_FOUND.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_404;
		} else if (HttpStatus.UNPROCESSABLE_ENTITY.value() == statusCode) {
			statusMessage = validateGatewayStatusDesafioResponderCodigo422(codigo422);
		} else if (HttpStatus.INTERNAL_SERVER_ERROR.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_500;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_DESCONHECIDO;
		}

		return statusMessage;
	}

	public String validateGatewayStatusDesafioResponderCodigo422(String codigo422) {

		String statusMessage = null;

		if (CODIGO_422_0.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_0;
		} else if (CODIGO_422_1.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_1;
		} else if (CODIGO_422_2.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_2;
		} else if (CODIGO_422_3.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_3;
		} else if (CODIGO_422_4.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_4
					+ IdentificacaoPositivaGatewayMessages.CONSULTA_REALIZADA + dataUtils.formataData(new Date())
					+ IdentificacaoPositivaGatewayMessages.PONTO;
		} else if (CODIGO_422_5.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_5
					+ IdentificacaoPositivaGatewayMessages.CONSULTA_REALIZADA + dataUtils.formataData(new Date())
					+ IdentificacaoPositivaGatewayMessages.PONTO;
		} else if (CODIGO_422_6.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_6;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_DESCONHECIDO;
		}

		return statusMessage;
	}
}
