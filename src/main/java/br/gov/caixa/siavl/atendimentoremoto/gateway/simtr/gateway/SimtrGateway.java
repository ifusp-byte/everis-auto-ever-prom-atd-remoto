package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.gateway;

import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.formataCpfCnpj;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.isCpf;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.StringToJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

	private static String SIMTR_URL_BASE_DOCUMENTOS_CPF = "/negocio/v1/dossie-cliente/cpf/";
	private static String SIMTR_URL_BASE_DOCUMENTOS_CNPJ = "/negocio/v1/dossie-cliente/cnpj/";

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
					newRequestEntityDocumentosConsultar(token), String.class);

			body = StringToJson(String.valueOf(response.getBody()));
			documentos = (ArrayNode) body.get("documentos");

			String tipoPessoa = Objects.requireNonNull(body.path("tipo_pessoa")).asText();
			String idDossie = Objects.requireNonNull(body.path("id")).asText();
			String statusMessage = documentos.isEmpty() ? "Não foram localizados documentos."
					: documentos.size() + " documentos localizados.";

			List<SimtrDocumentoDto> identidadeLista = new ArrayList<>();
			List<SimtrDocumentoDto> enderecoLista = new ArrayList<>();
			List<SimtrDocumentoDto> rendaLista = new ArrayList<>();
			List<SimtrDocumentoDto> desconhecidoLista = new ArrayList<>();

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
							.equalsIgnoreCase(simtrDocumento.getTipologia())) {
						identidadeLista.add(simtrDocumento);
					}

					if (TipologiaDocumentoEnum.RENDA.getDescricao().equalsIgnoreCase(simtrDocumento.getTipologia())) {
						rendaLista.add(simtrDocumento);
					}

					if (TipologiaDocumentoEnum.ENDERECO.getDescricao()
							.equalsIgnoreCase(simtrDocumento.getTipologia())) {
						enderecoLista.add(simtrDocumento);
					}

					if (TipologiaDocumentoEnum.DESCONHECIDO.getDescricao()
							.equalsIgnoreCase(simtrDocumento.getTipologia())) {
						desconhecidoLista.add(simtrDocumento);
					}

				}

				simtrDocumentoTipologia.setIdentidade(identidadeLista);
				simtrDocumentoTipologia.setEndereco(enderecoLista);
				simtrDocumentoTipologia.setRenda(rendaLista);
				simtrDocumentoTipologia.setDesconhecido(desconhecidoLista);
			}

			simtrOutputDto = SimtrOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(response.getStatusCodeValue())))
					.statusCreated(true).statusMessage("Consulta realizada com sucesso." + statusMessage)
					.documentos(simtrDocumentoTipologia).tipoPessoa(tipoPessoa).idDossie(idDossie).build();

		} catch (RestClientResponseException e) {

			simtrOutputDto = SimtrOutputDto.builder()
					.statusCode(String.valueOf(Objects.requireNonNull(e.getRawStatusCode())))
					.statusMessage("Erro na consulta. Tente novamente mais tarde." + e.getMessage())
					.statusCreated(false).documentos(simtrDocumentoTipologia).tipoPessoa(StringUtils.EMPTY)
					.idDossie(StringUtils.EMPTY).build();

		} finally {

			restTemplateDto.getHttpClient().close();
		}

		return simtrOutputDto;
	}

	public String tipologiaDocumento(String tipologiaDocumento) {

		String tipologia = "Desconhecido";

		if (ArrayUtils.contains(TipologiaEnum.codigos(), tipologiaDocumento)) {
			tipologia = TipologiaEnum.valueOf("T" + tipologiaDocumento).getDescricao();
		}

		return tipologia;
	}

}
