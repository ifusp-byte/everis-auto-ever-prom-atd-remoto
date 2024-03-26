package br.gov.caixa.siavl.atendimentoremoto.sicli.gateway;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.sicli.constants.SicliGatewayMessages;
import br.gov.caixa.siavl.atendimentoremoto.sicli.dto.ContaAtendimentoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@SuppressWarnings({ "squid:S6418", "squid:S3008", "squid:S1319", "squid:S2293", "squid:S6813" })
public class SicliGateway {

	static Logger LOG = Logger.getLogger(SicliGateway.class.getName());

	private static String AUTHORIZATION = "Authorization";

	private static String BEARER = "Bearer ";

	private static String API_KEY = "apikey";

	private static String API_KEY_VALUE = "l7xx2b6f4c64f3774870b0b9b399a77586f5";

	private static String URL_BASE_1 = "https://api.des.caixa:8443/cadastro/v2/clientes?cpfcnpj=";
	private static String URL_BASE_2 = "&campos=contratos";

	@Autowired
	RestTemplateUtils restTemplateUtils;

	private static ObjectMapper mapper = new ObjectMapper();

	public HttpEntity<?> newRequestEntityContaAtendimento(String token) {

		return new HttpEntity<String>(newHttpHeaders(token));
	}

	public HttpHeaders newHttpHeaders(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(AUTHORIZATION, BEARER + token);
		headers.set(API_KEY, API_KEY_VALUE);

		return headers;
	}

	public ContaAtendimentoOutputDTO contaAtendimento(String token, String cpfCnpj) throws Exception {

		ContaAtendimentoOutputDTO contaAtendimentoOutputDTO = new ContaAtendimentoOutputDTO();
		ResponseEntity<String> response = null;
		JsonNode jsonNode;
		String retorno = null;

		try {

			response = restTemplateUtils.newRestTemplate().exchange(URL_BASE_1 + cpfCnpj + URL_BASE_2, HttpMethod.GET,
					newRequestEntityContaAtendimento(token), String.class);

			LOG.info("Conta Atendimento - Consultar - Resposta SICLI " + mapper.writeValueAsString(response));

			String statusMessage = validateGatewayStatusAtendimentoConta(
					Objects.requireNonNull(response.getStatusCodeValue()));

			jsonNode = mapper.readTree(String.valueOf(response));

			// montaContas(jsonNode);

			contaAtendimentoOutputDTO = ContaAtendimentoOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).statusMessage(statusMessage)
					.statusCreated(true).dataCreated(formataData(new Date())).build();

			LOG.info("Conta Atendimento - Consultar - Resposta View "
					+ mapper.writeValueAsString(contaAtendimentoOutputDTO));

		} catch (RestClientResponseException e) {

			e.printStackTrace();

			jsonNode = mapper.readTree(e.getResponseBodyAsString());
			JsonNode retornoSicli = Objects.requireNonNull(jsonNode.path("retorno"));

			LOG.info("Conta Atendimento - Consultar - Resposta SICLI " + mapper.writeValueAsString(jsonNode));
			String statusMessage = validateGatewayStatusAtendimentoConta(Objects.requireNonNull(e.getRawStatusCode()));

			contaAtendimentoOutputDTO = ContaAtendimentoOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.response(Objects.requireNonNull(mapper.writeValueAsString(retornoSicli))).statusMessage(statusMessage)
					.statusCreated(false).dataCreated(formataData(new Date())).build();

			LOG.info("Conta Atendimento - Consultar - Resposta View "
					+ mapper.writeValueAsString(contaAtendimentoOutputDTO));

		}
		return contaAtendimentoOutputDTO;
	}

	/*
	public void montaContas(JsonNode jsonNode) {

		List<Map<String, Object>> contratos;

		contratos = (List<Map<String, Object>>) Objects.requireNonNull(jsonNode.path("contratos").asText());

	}
	
	*/

	public String validateGatewayStatusAtendimentoConta(int statusCode) {

		String statusMessage = null;

		if (HttpStatus.OK.value() == statusCode) {
			statusMessage = SicliGatewayMessages.SICLI_CONTA_ATENDIMENTO_RETORNO_200;
		} else {
			statusMessage = SicliGatewayMessages.SICLI_CONTA_ATENDIMENTO_RETORNO_NAO_200;
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

}
