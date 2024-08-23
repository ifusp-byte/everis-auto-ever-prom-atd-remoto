package br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.gateway;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SID01;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SIDEC;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REGEX_APENAS_NUMEROS;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.S;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaSicliService;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.constants.SicliGatewayMessages;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.dto.ContaAtendimentoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.dto.ContasOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.dto.SociosOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.util.ContaUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@Validated
@SuppressWarnings("all")
public class SicliGateway {

	@Autowired
	AuditoriaRegistraNotaSicliService auditoriaRegistraNotaSicliService;

	private static final Logger LOG = Logger.getLogger(SicliGateway.class.getName());
	private static String API_KEY = "apikey";

	@Value("${env.apimanager.key}")
	private String API_KEY_VALUE;

	@Value("${env.apimanager.url}")
	private String URL_BASE;

	@Value("${env.url.sicli}")
	private String URL_SICLI;

	@Autowired
	RestTemplateUtils restTemplateUtils;

	@Autowired
	ContaUtils contaUtils;

	@Autowired
	DocumentoUtils documentoUtils;

	@Autowired
	DataUtils dataUtils;

	private static ObjectMapper mapper = new ObjectMapper();

	public HttpEntity<?> newRequestEntityContaAtendimento(String token) {

		return new HttpEntity<String>(newHttpHeaders(token));
	}

	public HttpHeaders newHttpHeaders(String token) {

		String sanitizedToken = StringUtils.normalizeSpace(token);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		headers.set(API_KEY, API_KEY_VALUE);

		return headers;
	}

	public ContaAtendimentoOutputDTO contaAtendimento(@Valid String token, @Valid String cpfCnpj,
			@Valid boolean auditar) throws Exception {

		ContaAtendimentoOutputDTO contaAtendimentoOutputDTO = new ContaAtendimentoOutputDTO();
		ResponseEntity<String> response = null;
		JsonNode body;
		ArrayNode contratos;
		ArrayNode composicaoSocietaria;
		boolean statusCreated = false;
		List<ContasOutputDTO> contasAtendimento = new ArrayList<>();
		List<SociosOutputDTO> sociosLista = new ArrayList<>();

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			String uri = URL_BASE + "/" + URL_SICLI + cpfCnpj.replace(".", "").replace("-", "").trim();
			String finalUri = UriComponentsBuilder.fromHttpUrl(uri).toUriString();

			response = restTemplateDto.getRestTemplate().exchange(finalUri, HttpMethod.GET, newRequestEntityContaAtendimento(token), String.class);

			String statusMessage = validateGatewayStatusAtendimentoConta(Objects.requireNonNull(response.getStatusCodeValue()));

			body = mapper.readTree(String.valueOf(response.getBody()));
			contratos = (ArrayNode) body.get("contratos");
			composicaoSocietaria = (ArrayNode) body.get("composicaoSocietaria");

			String nomeCliente = Objects.requireNonNull(body.path("dadosBasicos").path("nome")).asText();
			String cpfCliente = Objects.requireNonNull(body.path("documentos").path("CPF").path("codigoDocumento")).asText();
			String razaoSocial = Objects.requireNonNull(body.path("dadosBasicos").path("razaoSocial")).asText();
			String cnpj = null;

			if (cpfCnpj.trim().length() == 14) {
				cnpj = cpfCnpj;
			}

			for (JsonNode nodeComposicaoSocietaria : composicaoSocietaria) {
				ArrayNode socios = (ArrayNode) nodeComposicaoSocietaria.path("socios");
				for (JsonNode nodeocios : socios) {
					SociosOutputDTO socio = new SociosOutputDTO();
					String nome = nodeocios.path("noPessoa").asText().trim();
					String cpf = nodeocios.path("coDocumento").asText().trim();
					socio.setNome(nome);
					socio.setCpf(cpf);
					sociosLista.add(socio);
				}
			}

			for (JsonNode node : contratos) {
				ContasOutputDTO conta = new ContasOutputDTO();
				String dtInicio = node.path("dtInicio").asText().trim(); 
				String sgSistema = node.path("sgSistema").asText().trim();
				String nuUnidade = node.path("nuUnidade").asText().trim().replaceAll(REGEX_APENAS_NUMEROS, StringUtils.EMPTY);
				String nuProduto = node.path("nuProduto").asText().trim().replaceAll(REGEX_APENAS_NUMEROS, StringUtils.EMPTY);
				String coIdentificacao = node.path("coIdentificacao").asText().trim().replaceAll(REGEX_APENAS_NUMEROS, StringUtils.EMPTY);

				if (CONTA_SIDEC.equalsIgnoreCase(sgSistema) || CONTA_SID01.equalsIgnoreCase(sgSistema)) {
					contasAtendimento = contaUtils.formataContaTotalLista(dtInicio, sgSistema, nuUnidade, nuProduto,
							coIdentificacao, contasAtendimento);
				}
			}

			if (!contasAtendimento.isEmpty()) {
				statusCreated = true;
				statusMessage = SicliGatewayMessages.SICLI_CONTA_ATENDIMENTO_RETORNO_200;
			} else {
				statusCreated = false;
				statusMessage = SicliGatewayMessages.SICLI_CONTA_ATENDIMENTO_RETORNO_NAO_200;
			}

			contaAtendimentoOutputDTO = ContaAtendimentoOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.statusCreated(statusCreated).dataCreated(dataUtils.formataData(new Date()))
					.nomeCliente(nomeCliente)
					.cpfCliente(cpfCliente.equals(StringUtils.EMPTY) ? StringUtils.EMPTY : documentoUtils.formataCpf(cpfCliente))
					.contas(contasAtendimento).socios(sociosLista).mensagemSicli(statusMessage).razaoSocial(razaoSocial)
					.cnpj(cnpj == null ? StringUtils.EMPTY : documentoUtils.formataCnpj(cnpj))
					.build();

			LOG.info("Conta Atendimento - Consultar - Resposta SICLI " + mapper.writeValueAsString(response));
			LOG.info("Conta Atendimento - Consultar - Resposta View "
					+ mapper.writeValueAsString(contaAtendimentoOutputDTO));

		} catch (RestClientResponseException e) {

			body = mapper.readTree(e.getResponseBodyAsString());
			JsonNode retornoSicli = Objects.requireNonNull(body.path("retorno"));
			String mensagemSicli = Objects.requireNonNull(body.path("mensagem").asText());
			String statusMessage = validateGatewayStatusAtendimentoConta(Objects.requireNonNull(e.getRawStatusCode()));

			contaAtendimentoOutputDTO = ContaAtendimentoOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.statusMessage(statusMessage)
					.statusCreated(false)
					.contas(new ArrayList<>())
					.socios(new ArrayList<>())
					.cpfCliente(StringUtils.EMPTY).nomeCliente(StringUtils.EMPTY)
					.razaoSocial(StringUtils.EMPTY).cnpj(StringUtils.EMPTY).mensagemSicli(mensagemSicli)
					.dataCreated(dataUtils.formataData(new Date())).build();

			LOG.info("Conta Atendimento - Consultar - Resposta SICLI " + mapper.writeValueAsString(body));
			LOG.info("Conta Atendimento - Consultar - Resposta View "
					+ mapper.writeValueAsString(contaAtendimentoOutputDTO));

		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}

			if (!String.valueOf(HttpStatus.CREATED.value()).equals(contaAtendimentoOutputDTO.getStatusCode())) {
				if (Boolean.TRUE.equals(auditar) && !statusCreated) {
					auditoriaRegistraNotaSicliService.auditar(contaAtendimentoOutputDTO, token, cpfCnpj);
				}
			}
			return contaAtendimentoOutputDTO;
		}
	}

	public Boolean verificaMarcaDoi(@Valid String token, @Valid String cpfCnpj) throws Exception {
		ResponseEntity<String> response = null;
		JsonNode body;
		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();
		Boolean verificado = false;

		try {
			String uri = URL_BASE + "/" + URL_SICLI + cpfCnpj.replace(".", "").replace("-", "").trim();
			String finalUri = UriComponentsBuilder.fromHttpUrl(uri).toUriString();

			response = restTemplateDto.getRestTemplate().exchange(finalUri, HttpMethod.GET,
					newRequestEntityContaAtendimento(token), String.class);

			LOG.info("Conta Atendimento - Consultar - Resposta SICLI " + mapper.writeValueAsString(response));

			String statusMessage = validateGatewayStatusAtendimentoConta(
					Objects.requireNonNull(response.getStatusCodeValue()));

			body = mapper.readTree(String.valueOf(response.getBody()));
			String marcaDOI = Objects.requireNonNull(body.path("dadosBasicos").path("marcaDOI")).asText();

			if (marcaDOI.equalsIgnoreCase(S)) {
				verificado = true;
			}

		} catch (RestClientResponseException e) {
			body = mapper.readTree(e.getResponseBodyAsString());
			JsonNode retornoSicli = Objects.requireNonNull(body.path("retorno"));
			LOG.info("Marca Doi - Consultar - Resposta SICLI " + mapper.writeValueAsString(body));

		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}

			return verificado;
		}

	}

	public String validateGatewayStatusAtendimentoConta(int statusCode) {

		String statusMessage = null;

		if (HttpStatus.OK.value() == statusCode) {
			statusMessage = SicliGatewayMessages.SICLI_CONTA_ATENDIMENTO_RETORNO_200;
		} else {
			statusMessage = SicliGatewayMessages.SICLI_CONTA_ATENDIMENTO_RETORNO_NAO_200;
		}
		return statusMessage;
	}

}
