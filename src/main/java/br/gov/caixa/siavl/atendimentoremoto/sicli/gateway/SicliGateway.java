package br.gov.caixa.siavl.atendimentoremoto.sicli.gateway;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaSicliService;
import br.gov.caixa.siavl.atendimentoremoto.sicli.constants.SicliGatewayMessages;
import br.gov.caixa.siavl.atendimentoremoto.sicli.dto.ContaAtendimentoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.sicli.dto.ContasOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.sicli.dto.SociosOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@Validated
@SuppressWarnings({ "squid:S6418", "squid:S3008", "squid:S1319", "squid:S2293", "squid:S6813", "squid:S4507" })
public class SicliGateway {

	@Autowired
	AuditoriaRegistraNotaSicliService auditoriaRegistraNotaSicliService;

	private final static Logger LOG = Logger.getLogger(SicliGateway.class.getName());

	private static String AUTHORIZATION = "Authorization";
	private static String BEARER = "Bearer ";
	private static String API_KEY = "apikey";
	private static String API_KEY_VALUE = "l7xx2b6f4c64f3774870b0b9b399a77586f5";
	private static String URL_BASE_1 = "https://api.des.caixa:8443/cadastro/v2/clientes?cpfcnpj=";
	private static String URL_BASE_2 = "&campos=dadosbasicos,composicaoSocietaria,enderecos,contratos,documentos,nicho,carteiragrc,vinculo,dadosatualizacaocadastral,meiocomunicacao,rendas,profissaosiric&classe=1";
	private static String REPLACE_IDENTIFICACAO = "0000000000000000";
	private static String REPLACE_CONTA_1 = "0000";
	private static String REPLACE_CONTA_2 = "000";
	
	@Autowired
	RestTemplateUtils restTemplateUtils;

	private static ObjectMapper mapper = new ObjectMapper();

	public HttpEntity<?> newRequestEntityContaAtendimento(String token) {

		return new HttpEntity<String>(newHttpHeaders(token));
	}

	public HttpHeaders newHttpHeaders(String token) {

		String sanitizedToken = StringUtils.normalizeSpace(token);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//headers.set(AUTHORIZATION, BEARER + token);
		headers.setBearerAuth(sanitizedToken);
		headers.set(API_KEY, API_KEY_VALUE);

		return headers;
	}

	public ContaAtendimentoOutputDTO contaAtendimento(@Valid String token, @Valid  String cpfCnpj, @Valid boolean auditar) throws Exception {

		ContaAtendimentoOutputDTO contaAtendimentoOutputDTO = new ContaAtendimentoOutputDTO();
		ResponseEntity<String> response = null;
		JsonNode body;
		ArrayNode contratos;
		ArrayNode composicaoSocietaria;
		boolean statusCreated = false;
		List<ContasOutputDTO> contasAtendimento = new ArrayList<>();
		List<SociosOutputDTO> sociosLista = new ArrayList<>();

		try {

			response = restTemplateUtils.newRestTemplate().exchange(
					URL_BASE_1 + cpfCnpj.replace(".", "").replace("-", "").trim() + URL_BASE_2, HttpMethod.GET,
					newRequestEntityContaAtendimento(token), String.class);

			LOG.info("Conta Atendimento - Consultar - Resposta SICLI " + mapper.writeValueAsString(response));

			String statusMessage = validateGatewayStatusAtendimentoConta(
					Objects.requireNonNull(response.getStatusCodeValue()));

			body = mapper.readTree(String.valueOf(response.getBody()));
			contratos = (ArrayNode) body.get("contratos");
			composicaoSocietaria = (ArrayNode) body.get("composicaoSocietaria");

			String nomeCliente = Objects.requireNonNull(body.path("dadosBasicos").path("nome")).asText();
			String cpfCliente = Objects.requireNonNull(body.path("documentos").path("CPF").path("codigoDocumento")).asText();
			
			
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
				String nuUnidade = node.path("nuUnidade").asText().trim();
				String nuProduto = node.path("nuProduto").asText().trim();
				String coIdentificacao = node.path("coIdentificacao").asText().trim();
					
				String contaInput = formataContaTotal(dtInicio, sgSistema, nuUnidade, nuProduto, coIdentificacao); 
				
				conta.setConta(contaInput);
				contasAtendimento.add(conta);
			}

			if (!contasAtendimento.isEmpty() && !nomeCliente.isEmpty() && !cpfCliente.isEmpty()) {
				statusCreated = true;
				statusMessage = SicliGatewayMessages.SICLI_CONTA_ATENDIMENTO_RETORNO_NAO_200;
			}

			contaAtendimentoOutputDTO = ContaAtendimentoOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.response(String.valueOf(Objects.requireNonNull(response.getBody()))).statusMessage(statusMessage)
					.statusCreated(statusCreated).dataCreated(formataData(new Date())).nomeCliente(nomeCliente)
					.cpfCnpjCliente(formataCpf(cpfCliente))
					.contas(contasAtendimento)
					.socios(sociosLista)
					.build();

			LOG.info("Conta Atendimento - Consultar - Resposta View "
					+ mapper.writeValueAsString(contaAtendimentoOutputDTO));

		} catch (RestClientResponseException e) {

			LOG.log(Level.SEVERE, e.getMessage(), e);

			body = mapper.readTree(e.getResponseBodyAsString());
			JsonNode retornoSicli = Objects.requireNonNull(body.path("retorno"));

			LOG.info("Conta Atendimento - Consultar - Resposta SICLI " + mapper.writeValueAsString(body));
			String statusMessage = validateGatewayStatusAtendimentoConta(Objects.requireNonNull(e.getRawStatusCode()));

			contaAtendimentoOutputDTO = ContaAtendimentoOutputDTO.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.response(Objects.requireNonNull(mapper.writeValueAsString(retornoSicli)))
					.statusMessage(statusMessage).statusCreated(false).dataCreated(formataData(new Date())).build();

			LOG.info("Conta Atendimento - Consultar - Resposta View "
					+ mapper.writeValueAsString(contaAtendimentoOutputDTO));

		}

		if (!String.valueOf(HttpStatus.CREATED.value()).equals(contaAtendimentoOutputDTO.getStatusCode())) {
			if (Boolean.TRUE.equals(auditar) && !statusCreated) {
				auditoriaRegistraNotaSicliService.auditar(contaAtendimentoOutputDTO, token, cpfCnpj);
			}
		}

		return contaAtendimentoOutputDTO;
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

	private String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

	private String formataCpf(Object object) {

		String cpfInput = null;
		String formatCpf = null;
		String cpf = null;
		MaskFormatter cpfMask = null;

		if (object != null) {
			cpfInput = String.valueOf(object).replace(".", "").replace("/", "").replace("/", "").replace("-", "");
			formatCpf = "00000000000".substring(cpfInput.length()) + cpfInput;
			try {
				cpfMask = new MaskFormatter("###.###.###-##");
				cpfMask.setValueContainsLiteralCharacters(false);
				cpf = cpfMask.valueToString(formatCpf);
			} catch (ParseException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return cpf;
	}

	private String formataContaTotal (String dtInicio, String sgSistema, Object nuUnidade, Object nuProduto, Object coIdentificacao) {

		String contaFormatada = null; 
		
		if ("SIART".equalsIgnoreCase(sgSistema)) {	
			String identificacao = String.valueOf(coIdentificacao).replace(".", "").replace("-", "");	
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);	
			identificacao = identificacao.replace(unidade , ""); 
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length())+unidade;	
			String formataProduto = REPLACE_CONTA_1.substring(produto.length())+produto;
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length())+identificacao;		
			contaFormatada = formataUnidade+formataProduto+formatIdentificacao; 	
			
		} else if ("SIDEC".equalsIgnoreCase(sgSistema)) {
			String identificacao = String.valueOf(coIdentificacao).replace(".", "").replace("-", "");	
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);			
			String formatProdutoReplace = REPLACE_CONTA_2.substring(produto.length())+produto;
			identificacao = identificacao.replace(unidade+formatProdutoReplace , ""); 
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length())+identificacao;
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length())+unidade;	
			String formataProduto = REPLACE_CONTA_1.substring(produto.length())+produto;
			contaFormatada = formataUnidade+formataProduto+formatIdentificacao;
			
		} else if ("SIIFX".equalsIgnoreCase(sgSistema)) {
			String dataInicio = String.valueOf(dtInicio).replace(".", "").replace("-", "");	
			String identificacao = String.valueOf(coIdentificacao).replace(".", "").replace("-", "");	
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);		
			identificacao = identificacao.replace(dataInicio , ""); 
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length())+identificacao;
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length())+unidade;	
			String formataProduto = REPLACE_CONTA_1.substring(produto.length())+produto;	
			contaFormatada = formataUnidade+formataProduto+formatIdentificacao;
				
		} else {
			String identificacao = String.valueOf(coIdentificacao).replace(".", "").replace("-", "");	
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);	
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length())+unidade;	
			String formataProduto = REPLACE_CONTA_1.substring(produto.length())+produto;
			identificacao = identificacao.replace(formataUnidade+ formataProduto, ""); 
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length())+identificacao;	
			contaFormatada = formataUnidade+formataProduto+formatIdentificacao;
		}
		
		return contaFormatada; 
		
	}
	
}
