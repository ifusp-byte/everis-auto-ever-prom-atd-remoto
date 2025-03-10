package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.gateway;

import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.formataCpfCnpj;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.isCpf;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.StringToJson;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto.SimtrDocumentoDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto.SimtrOutputDto;
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

	private static String SIMTR_URL_BASE_DOCUMENTOS_CPF = "negocio/v1/dossie-cliente/cpf/";
	private static String SIMTR_URL_BASE_DOCUMENTOS_CNPJ = "negocio/v1/dossie-cliente/cnpj/";

	@Autowired
	RestTemplateUtils restTemplateUtils;

	public HttpHeaders newHttpHeaders(String token) {
		String sanitizedToken = StringUtils.normalizeSpace(token);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		return headers;
	}

	public HttpEntity<?> newRequestEntityDocumentosConsultar(String token) {
		return new HttpEntity<String>(newHttpHeaders(token));
	}

	public SimtrOutputDto documentosCpfCnpjConsultar(@Valid String token, @Valid String cpfCnpj) throws Exception {

		SimtrOutputDto simtrOutputDto = new SimtrOutputDto();
		ResponseEntity<String> response = null;
		JsonNode body;
		ArrayNode documentos;

		String url = isCpf(cpfCnpj) ? SIMTR_URL_BASE_DOCUMENTOS_CPF : SIMTR_URL_BASE_DOCUMENTOS_CNPJ;

		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			String uri = url + formataCpfCnpj(cpfCnpj);
			String finalUri = UriComponentsBuilder.fromHttpUrl(uri).toUriString();

			response = restTemplateDto.getRestTemplate().exchange(finalUri, HttpMethod.GET,
					newRequestEntityDocumentosConsultar(token), String.class);

			body = StringToJson(String.valueOf(response.getBody()));
			documentos = (ArrayNode) body.get("documentos");

			String tipoPessoa = Objects.requireNonNull(body.path("tipo_pessoa")).asText();
			String idDossie = Objects.requireNonNull(body.path("id")).asText();
			String statusMessage = documentos.isEmpty() ? "Não foram localizados documentos."
					: documentos.size() + " documentos localizados.";

			List<SimtrDocumentoDto> simtrDocumentos = new ArrayList<>();

			if (!documentos.isEmpty()) {
				for (JsonNode nodeDocumentos : documentos) {
					SimtrDocumentoDto simtrDocumento = new SimtrDocumentoDto();

					simtrDocumento.setId(nodeDocumentos.path("id").asText().trim());
					simtrDocumento.setTipologia(
							nodeDocumentos.path("tipo_documento").path("codigo_tipologia").asText().trim());
					simtrDocumento.setAcordeonMfe(TipologiaEnum
							.valueOf("T"+nodeDocumentos.path("tipo_documento").path("codigo_tipologia").asText().trim())
							.getDescricao());
					simtrDocumento.setNome(nodeDocumentos.path("tipo_documento").path("nome").asText().trim());
					simtrDocumento.setAtivo(nodeDocumentos.path("tipo_documento").path("ativo").asText().trim());
					simtrDocumento.setSituacaoDocumento(
							nodeDocumentos.path("tipo_documento").path("situacao_documento").asText().trim());
					simtrDocumento.setDataHoraCaptura(
							nodeDocumentos.path("tipo_documento").path("data_hora_captura").asText().trim());
					simtrDocumento.setMimeType(nodeDocumentos.path("tipo_documento").path("mime_type").asText().trim());

					simtrDocumentos.add(simtrDocumento);

				}
			}

			simtrOutputDto = SimtrOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.statusCreated(true).statusMessage("Consulta realizada com sucesso." + statusMessage)
					.documentos(simtrDocumentos).tipoPessoa(tipoPessoa).idDossie(idDossie).build();

		} catch (RestClientResponseException e) {

			body = StringToJson(e.getResponseBodyAsString());

			simtrOutputDto = SimtrOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.statusMessage("Erro na consulta. Tente novamente mais tarde.").statusCreated(false)
					.documentos(new ArrayList<>()).tipoPessoa(StringUtils.EMPTY).idDossie(StringUtils.EMPTY).build();

		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}

			return simtrOutputDto;
		}
	}

}
