package br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.gateway;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.constants.IdentificacaoPositivaGatewayMessages;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@SuppressWarnings({ "squid:S6418", "squid:S3008", "squid:S1319", "squid:S2293", "squid:S6813" })
public class IdentificacaoPositivaGateway {
	
	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository; 

	private final static Logger LOG = Logger.getLogger(IdentificacaoPositivaGateway.class.getName());
	private static String API_KEY = "APIKey";
	private static String API_KEY_VALUE = "l7xx2b6f4c64f3774870b0b9b399a77586f5";
	private static String URL_BASE = "https://api.des.caixa:8443/id-positiva/v1/desafios";
	private static ObjectMapper mapper = new ObjectMapper();
	private static String CODIGO_422_0 = "0";
	private static String CODIGO_422_1 = "1";
	private static String CODIGO_422_2 = "2";
	private static String CODIGO_422_3 = "3";
	private static String CODIGO_422_4 = "4";
	private static String CODIGO_422_5 = "5";
	private static String CODIGO_422_6 = "6";

	@Autowired
	RestTemplateUtils restTemplateUtils;

	public HttpEntity<HashMap<String, String>> newRequestEntityDesafioCriar(String token,
			HashMap<String, String> criaDesafioMap) {

		return new HttpEntity<HashMap<String, String>>(criaDesafioMap, newHttpHeaders(token));
	}

	public HttpEntity<String> newRequestEntityDesafioResponder(String token,
			RespondeDesafioInputDTO respondeDesafioInputDTO) {
		
		HashMap<String, Object> respondeDesafioMap = new HashMap<String, Object>();
		String request = null;
		try {	
			respondeDesafioMap.put("listaResposta", respondeDesafioInputDTO.getListaResposta());
			request = mapper.writeValueAsString(respondeDesafioMap).replaceAll("\\u005C", "").replaceAll("\\n", "");
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

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

	public CriaDesafioOutputDTO desafioCriar(@Valid  String token, HashMap<String, String> criaDesafioMap, Long cpfSocio, Long protocolo) throws Exception {
        
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

			response = restTemplateDto.getRestTemplate().postForEntity(URL_BASE,
					newRequestEntityDesafioCriar(token, criaDesafioMap), String.class);

			LOG.info("Identificação Positiva - Desafio Criar - Resposta SIIPC " + mapper.writeValueAsString(response));

			String statusMessage = validateGatewayStatusDesafioCriar(
					Objects.requireNonNull(response.getStatusCodeValue()), StringUtils.EMPTY);

			criaDesafioOutputDTO = CriaDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).statusMessage(statusMessage)
					.statusCreated(true).dataCreated(formataData(new Date())).build();

			LOG.info("Identificação Positiva - Desafio Criar - Resposta View " + mapper.writeValueAsString(criaDesafioOutputDTO));

		} catch (RestClientResponseException e) {
			
			jsonNode = mapper.readTree(e.getResponseBodyAsString());

			LOG.info("Identificação Positiva - Desafio Criar - Resposta SIIPC " + mapper.writeValueAsString(jsonNode));

			codigo422 = Objects.requireNonNull(jsonNode.path("codigo").asText());
			String statusMessage = validateGatewayStatusDesafioCriar(Objects.requireNonNull(e.getRawStatusCode()),
					codigo422);

			criaDesafioOutputDTO = CriaDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.response(Objects.requireNonNull(e.getResponseBodyAsString())).statusMessage(statusMessage)
					.statusCreated(false).dataCreated(formataData(new Date())).build();

			LOG.info("Identificação Positiva - Desafio Criar - Resposta View " + mapper.writeValueAsString(criaDesafioOutputDTO));

		} finally {
			
			restTemplateDto.getHttpClient().close();
		}

		return criaDesafioOutputDTO;

	}

	public RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio,
			RespondeDesafioInputDTO respondeDesafioInputDTO) throws Exception {

		RespondeDesafioOutputDTO respondeDesafioOutputDTO = new RespondeDesafioOutputDTO();
		ResponseEntity<String> response = null;
		JsonNode jsonNode;
		String codigo422 = null;
		AtendimentoCliente atendimentoCliente = atendimentoClienteRepository.getReferenceById(Long.parseLong(respondeDesafioInputDTO.getProtocolo()));

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();
		
		try {

			response = restTemplateDto.getRestTemplate().postForEntity(
					URL_BASE + "/" + Integer.parseInt(idDesafio.trim()) + "/enviar-respostas",
					newRequestEntityDesafioResponder(token, respondeDesafioInputDTO), String.class);

			LOG.info("Identificação Positiva - Desafio Responder - Resposta SIIPC "
					+ mapper.writeValueAsString(response));

			String statusMessage = validateGatewayStatusDesafioResponder(
					Objects.requireNonNull(response.getStatusCodeValue()), StringUtils.EMPTY);

			respondeDesafioOutputDTO = RespondeDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).statusMessage(statusMessage)
					.statusCreated(true).dataCreated(formataData(new Date())).build();
			
			atendimentoCliente.setSituacaoIdPositiva(1L);
			atendimentoCliente.setDescricaoIdentificacaoPositiva("SUCESSO");
			atendimentoCliente.setDataIdentificacaoPositiva(formataDataBanco());
			atendimentoCliente.setDataValidacaoPositiva(formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);
				
			LOG.info("Identificação Positiva - Desafio Responder - Resposta View "
					+ mapper.writeValueAsString(respondeDesafioOutputDTO));

		} catch (RestClientResponseException e) {

			jsonNode = mapper.readTree(e.getResponseBodyAsString());

			LOG.info("Identificação Positiva - Desafio Responder - Resposta SIIPC "
					+ mapper.writeValueAsString(jsonNode));

			codigo422 = Objects.requireNonNull(jsonNode.path("codigo").asText());
			String statusMessage = validateGatewayStatusDesafioResponder(Objects.requireNonNull(e.getRawStatusCode()),
					codigo422);

			respondeDesafioOutputDTO = RespondeDesafioOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.response(Objects.requireNonNull(e.getResponseBodyAsString())).statusMessage(statusMessage)
					.statusCreated(false).dataCreated(formataData(new Date())).build();
			
			atendimentoCliente.setSituacaoIdPositiva(2L);
			atendimentoCliente.setDescricaoIdentificacaoPositiva("BLOQUEADO");
			atendimentoCliente.setDataIdentificacaoPositiva(formataDataBanco());
			atendimentoCliente.setDataValidacaoPositiva(formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);

			LOG.info("Identificação Positiva - Desafio Responder - Resposta View "
					+ mapper.writeValueAsString(respondeDesafioOutputDTO));

		} finally {
			
			restTemplateDto.getHttpClient().close();
		}

		return respondeDesafioOutputDTO;

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
					+ IdentificacaoPositivaGatewayMessages.CONSULTA_REALIZADA + formataData(new Date())
					+ IdentificacaoPositivaGatewayMessages.PONTO;
		} else if (CODIGO_422_5.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_5
					+ IdentificacaoPositivaGatewayMessages.CONSULTA_REALIZADA + formataData(new Date())
					+ IdentificacaoPositivaGatewayMessages.PONTO;
		} else if (CODIGO_422_6.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_6;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_DESCONHECIDO;
		}

		return statusMessage;

	}

	private String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}
	
	private Date formataDataBanco() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		return time.getTime();
	}

}
