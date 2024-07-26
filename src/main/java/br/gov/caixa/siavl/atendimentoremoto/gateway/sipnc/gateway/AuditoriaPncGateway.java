package br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.gateway;

import java.io.IOException;
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

import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateDto;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@SuppressWarnings({"squid:S116", "squid:S1153", "squid:S6418", "squid:S3008", "squid:S1319", "squid:S2293", "squid:S6813", "squid:S3457", "squid:S2629" })
public class AuditoriaPncGateway {

	private static final Logger LOG = Logger.getLogger(AuditoriaPncGateway.class.getName());
	private static String API_KEY = "apikey";

	@Value("${env.apimanager.key}")
	private String API_KEY_VALUE;

	@Value("${env.apimanager.url}")
	private String URL_BASE;

	@Value("${env.url.sipnc.log}")
	private String URL_SIPNC_LOG;

	@Autowired
	RestTemplateUtils restTemplateUtils;

	public HttpEntity<AuditoriaPncInputDTO> newRequestEntityAuditoriaPncSalvar(String token,
			AuditoriaPncInputDTO auditoriaPncInputDTO) {

		return new HttpEntity<AuditoriaPncInputDTO>(auditoriaPncInputDTO, newHttpHeaders(token));
	}

	public HttpHeaders newHttpHeaders(String token) {

		String sanitizedToken = StringUtils.normalizeSpace(token);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(sanitizedToken);
		headers.set(API_KEY, API_KEY_VALUE);

		return headers;
	}

	public void auditoriaPncSalvar(@Valid String token, @Valid AuditoriaPncInputDTO auditoriaPncInputDTO) {

		ResponseEntity<String> response = null;
		RestTemplateDto restTemplateDto = restTemplateUtils.newRestTemplate();

		try {

			response = restTemplateDto.getRestTemplate().postForEntity(URL_BASE + "/" + URL_SIPNC_LOG,
					newRequestEntityAuditoriaPncSalvar(token, auditoriaPncInputDTO), String.class);

			LOG.log(Level.INFO, "Sucesso Auditoria PNC " + String.valueOf(response));

		} catch (RestClientResponseException e) {
			LOG.log(Level.INFO, "Erro Auditoria PNC " + String.valueOf(e.getResponseBodyAsString()));

		} finally {

			try {
				restTemplateDto.getHttpClient().close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erro. Não foi possível fechar a conexão com o socket.");
			}
		}

	}

}
