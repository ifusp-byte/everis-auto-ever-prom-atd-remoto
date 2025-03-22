package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.gateway;

import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.formataCpfCnpj;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.isCpf;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.StringToJson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto.SimtrDocumentoDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto.SimtrDocumentoTipologiaDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto.SimtrOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.enums.TipologiaDocumentoEnum;
import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.enums.TipologiaEnum;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@Validated
@SuppressWarnings("all")
public class SimtrGateway {

	private static final Logger LOG = Logger.getLogger(SimtrGateway.class.getName());
	private static String API_KEY = "apikey";

	@Value("${env.url.simtr}")
	private String URL_SIMTR;

	@Value("${env.simtr.siavl.token.url}")
	private String SIMTR_SIAVL_TOKEN_URL;

	@Value("${env.simtr.siavl.grant.type}")
	private String SIMTR_SIAVL_GRANT_TYPE;

	@Value("${env.simtr.siavl.client.id}")
	private String SIMTR_SIAVL_CLIENT_ID;

	@Value("${env.simtr.siavl.client.secret}")
	private String SIMTR_SIAVL_CLIENT_SECRET;

	private static String SIMTR_URL_BASE_DOCUMENTOS_CPF = "/negocio/v1/dossie-cliente/cpf/";
	private static String SIMTR_URL_BASE_DOCUMENTOS_CNPJ = "/negocio/v1/dossie-cliente/cnpj/";
	private static String SIMTR_URL_BASE_DOCUMENTO_ID = "/negocio/v2/documento/";
	private static String SIMTR_URL_BASE_DOCUMENTO_ID_FLAG_BINARIO = "\u003F" + "binario=true";

	@Autowired
	RestTemplateUtils restTemplateUtils;

	public HttpHeaders newHttpHeaders(String token) {
		String sanitizedToken = StringUtils.normalizeSpace(token);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		return headers;
	}

	public HttpHeaders newHttpHeadersTokenServico() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}

	public HttpEntity<?> newRequestEntityTokenServico() {
		String params = "grant_type=" + SIMTR_SIAVL_GRANT_TYPE + "&client_id=" + SIMTR_SIAVL_CLIENT_ID
				+ "&client_secret=" + SIMTR_SIAVL_CLIENT_SECRET;
		return new HttpEntity<String>(params, newHttpHeadersTokenServico());
	}

	public HttpEntity<?> newRequestEntityDocumentosConsultar(String token) {
		return new HttpEntity<String>(newHttpHeaders(token));
	}

	public SimtrOutputDto documentosCpfCnpjConsultar(@Valid String cpfCnpj, @Valid String tokenServico)
			throws Exception {

		SimtrOutputDto simtrOutputDto = new SimtrOutputDto();
		String statusMessage = StringUtils.EMPTY;
		SimtrDocumentoTipologiaDto simtrDocumentoTipologia = new SimtrDocumentoTipologiaDto();
		ResponseEntity<String> response = null;
		JsonNode body;
		ArrayNode documentos;

		String url = isCpf(cpfCnpj) ? URL_SIMTR + SIMTR_URL_BASE_DOCUMENTOS_CPF
				: URL_SIMTR + SIMTR_URL_BASE_DOCUMENTOS_CNPJ;

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			String uri = url + formataCpfCnpj(cpfCnpj);
			String finalUri = UriComponentsBuilder.fromHttpUrl(uri).toUriString();

			response = restTemplateDto.getRestTemplate().exchange(finalUri, HttpMethod.GET,
					newRequestEntityDocumentosConsultar(tokenServico), String.class);

			body = StringToJson(String.valueOf(response.getBody()));
			documentos = (ArrayNode) body.get("documentos");

			String tipoPessoa = Objects.requireNonNull(body.path("tipo_pessoa")).asText();
			String idDossie = Objects.requireNonNull(body.path("id")).asText();

			List<SimtrDocumentoDto> identidadeLista = new ArrayList<>();
			List<SimtrDocumentoDto> listaIdentidade = new ArrayList<>();

			List<SimtrDocumentoDto> enderecoLista = new ArrayList<>();
			List<SimtrDocumentoDto> listaEndereco = new ArrayList<>();

			List<SimtrDocumentoDto> rendaLista = new ArrayList<>();
			List<SimtrDocumentoDto> listaRenda = new ArrayList<>();

			List<SimtrDocumentoDto> desconhecidoLista = new ArrayList<>();
			List<SimtrDocumentoDto> listaDesconhecido = new ArrayList<>();

			if (!documentos.isEmpty()) {
				for (JsonNode nodeDocumentos : documentos) {
					SimtrDocumentoDto simtrDocumento = new SimtrDocumentoDto();

					simtrDocumento.setId(nodeDocumentos.path("id").asText().trim());
					simtrDocumento.setTipologia(
							nodeDocumentos.path("tipo_documento").path("codigo_tipologia").asText().trim());
					simtrDocumento.setAcordeonMfe(tipologiaDocumento(
							nodeDocumentos.path("tipo_documento").path("codigo_tipologia").asText().trim()));
					simtrDocumento.setNome(nodeDocumentos.path("tipo_documento").path("nome").asText().trim());
					simtrDocumento.setAtivo(nodeDocumentos.path("tipo_documento").path("ativo").asText().trim());
					simtrDocumento.setSituacaoDocumento(nodeDocumentos.path("situacao_documento").asText().trim());
					simtrDocumento.setDataHoraCaptura(nodeDocumentos.path("data_hora_captura").asText().trim());
					simtrDocumento.setMimeType(nodeDocumentos.path("mime_type").asText().trim());

					if (TipologiaDocumentoEnum.IDENTIDADE.getDescricao()
							.equalsIgnoreCase(simtrDocumento.getAcordeonMfe())) {
						identidadeLista.add(simtrDocumento);
					}

					if (TipologiaDocumentoEnum.RENDA.getDescricao().equalsIgnoreCase(simtrDocumento.getAcordeonMfe())) {
						rendaLista.add(simtrDocumento);
					}

					if (TipologiaDocumentoEnum.ENDERECO.getDescricao()
							.equalsIgnoreCase(simtrDocumento.getAcordeonMfe())) {
						enderecoLista.add(simtrDocumento);
					}

					if (TipologiaDocumentoEnum.DESCONHECIDO.getDescricao()
							.equalsIgnoreCase(simtrDocumento.getAcordeonMfe())) {
						desconhecidoLista.add(simtrDocumento);
					}

				}
				
				AtomicInteger contadorLista = new AtomicInteger(0);
				int quantidadeDocumentos = 0;

				if (!identidadeLista.isEmpty()) {
					listaIdentidade.add(identidadeLista.stream()
							.sorted(Comparator.comparing(SimtrDocumentoDto::getDataCaptura).reversed()).findFirst()
							.get());
					quantidadeDocumentos = contadorLista.incrementAndGet();		
							}

				if (!enderecoLista.isEmpty()) {
					listaEndereco.add(enderecoLista.stream()
							.sorted(Comparator.comparing(SimtrDocumentoDto::getDataCaptura).reversed()).findFirst()
							.get());
					quantidadeDocumentos = contadorLista.incrementAndGet();	
				}

				if (!rendaLista.isEmpty()) {
					listaRenda.add(rendaLista.stream()
							.sorted(Comparator.comparing(SimtrDocumentoDto::getDataCaptura).reversed()).findFirst()
							.get());
					quantidadeDocumentos = contadorLista.incrementAndGet();	
				}

				if (!desconhecidoLista.isEmpty()) {
					listaDesconhecido.add(desconhecidoLista.stream()
							.sorted(Comparator.comparing(SimtrDocumentoDto::getDataCaptura).reversed()).findFirst()
							.get());
					quantidadeDocumentos = contadorLista.incrementAndGet();	
				}

				simtrDocumentoTipologia.setIdentidade(listaIdentidade);
				simtrDocumentoTipologia.setEndereco(listaEndereco);
				simtrDocumentoTipologia.setRenda(listaRenda);
				simtrDocumentoTipologia.setDesconhecido(listaDesconhecido);
				
				 statusMessage = quantidadeDocumentos == 0 ? "Não foram localizados documentos."
						: quantidadeDocumentos + " documentos localizados.";
			}

			simtrOutputDto = SimtrOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.statusCreated(true).statusMessage("Consulta realizada com sucesso." + statusMessage)
					.documentos(simtrDocumentoTipologia).tipoPessoa(tipoPessoa).idDossie(idDossie).build();

		} catch (RestClientResponseException e) {

			simtrOutputDto = SimtrOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.statusMessage("Erro na consulta. Tente novamente mais tarde.").statusCreated(false)
					.documentos(simtrDocumentoTipologia).tipoPessoa(StringUtils.EMPTY).idDossie(StringUtils.EMPTY)
					.build();

		} finally {

			restTemplateDto.getHttpClient().close();
		}

		return simtrOutputDto;
	}

	public SimtrOutputDto documentoByIdConsultar(@Valid String idDocumento, @Valid String tokenServico)
			throws Exception {

		SimtrOutputDto simtrOutputDto = new SimtrOutputDto();
		ResponseEntity<String> response = null;
		JsonNode body;
		String binario = StringUtils.EMPTY;
		String extensao = StringUtils.EMPTY;
		String mimeType = StringUtils.EMPTY;
		String tipologia = StringUtils.EMPTY;
		String url = URL_SIMTR + SIMTR_URL_BASE_DOCUMENTO_ID + idDocumento + SIMTR_URL_BASE_DOCUMENTO_ID_FLAG_BINARIO;

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			String uri = url;
			String finalUri = UriComponentsBuilder.fromHttpUrl(uri).toUriString();

			response = restTemplateDto.getRestTemplate().exchange(finalUri, HttpMethod.GET,
					newRequestEntityDocumentosConsultar(tokenServico), String.class);

			body = StringToJson(String.valueOf(response.getBody()));

			binario = Objects.requireNonNull(body.path("binario")).asText().trim();
			extensao = Objects.requireNonNull(body.path("mime_type")).asText().trim();
			mimeType = Objects.requireNonNull(body.path("mimetype")).asText().trim();
			tipologia = Objects.requireNonNull(body.path("tipo_documento").path("codigo_tipologia")).asText().trim();

			String statusMessage = binario == null ? "Documento não localizado." : "Documento localizado.";

			simtrOutputDto = SimtrOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.statusCreated(true).statusMessage("Consulta realizada com sucesso." + statusMessage)
					.binario(binario != null ? binario : StringUtils.EMPTY)
					.mimeType(mimeType != null ? mimeType : StringUtils.EMPTY)
					.extensao(extensao != null ? extensao : StringUtils.EMPTY).tipologia(tipologia).build();

		} catch (RestClientResponseException e) {

			simtrOutputDto = SimtrOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.statusMessage("Erro na consulta. Tente novamente mais tarde.").statusCreated(false)
					.binario(StringUtils.EMPTY).mimeType(StringUtils.EMPTY).extensao(StringUtils.EMPTY)
					.tipologia(StringUtils.EMPTY).build();

		} finally {

			restTemplateDto.getHttpClient().close();
		}

		return simtrOutputDto;
	}

	public String tokenServico() throws Exception {

		ResponseEntity<String> response = null;
		JsonNode body;
		String access_token = StringUtils.EMPTY;

		String url = SIMTR_SIAVL_TOKEN_URL;

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			String uri = url;
			String finalUri = UriComponentsBuilder.fromHttpUrl(uri).toUriString();

			response = restTemplateDto.getRestTemplate().exchange(finalUri, HttpMethod.POST,
					newRequestEntityTokenServico(), String.class);

			body = StringToJson(String.valueOf(response.getBody()));

			access_token = Objects.requireNonNull(body.path("access_token")).asText().trim();

		} catch (RestClientResponseException e) {

			access_token = StringUtils.EMPTY;

		} finally {

			restTemplateDto.getHttpClient().close();
		}

		return access_token;

	}

	public String tipologiaDocumento(String tipologiaDocumento) {

		String tipologia = "Desconhecido";

		if (ArrayUtils.contains(TipologiaEnum.codigos(), tipologiaDocumento)) {
			tipologia = TipologiaEnum.valueOf("T" + tipologiaDocumento).getDescricao();
		}

		return tipologia;
	}

}
