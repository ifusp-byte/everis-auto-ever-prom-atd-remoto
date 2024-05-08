package br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.gateway;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.util.RestTemplateUtils;

@Service
@SuppressWarnings({ "squid:S6418", "squid:S3008", "squid:S1319", "squid:S2293", "squid:S6813", "squid:S3457" })
public class AuditoriaPncGateway {

	private final static Logger LOG = Logger.getLogger(AuditoriaPncGateway.class.getName());

	private static String AUTHORIZATION = "Authorization";

	private static String BEARER = "Bearer ";

	private static String API_KEY = "apikey";

	private static String API_KEY_VALUE = "l7xx2b6f4c64f3774870b0b9b399a77586f5";

	private static String URL_BASE = "https://api.des.caixa:8443/plataforma-unificada/trilha/v1/registros";

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
		//headers.set(AUTHORIZATION, BEARER + token);
		headers.setBearerAuth(sanitizedToken);
		headers.set(API_KEY, API_KEY_VALUE);

		return headers;
	}

	public void auditoriaPncSalvar(@Valid String token, @Valid AuditoriaPncInputDTO auditoriaPncInputDTO) {

		ResponseEntity<String> response = null;

		try {

			response = restTemplateUtils.newRestTemplate().postForEntity(URL_BASE,
					newRequestEntityAuditoriaPncSalvar(token, auditoriaPncInputDTO), String.class);

			LOG.log(Level.INFO, "Sucesso Auditoria PNC " + String.valueOf(response));

		} catch (RestClientResponseException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			LOG.log(Level.INFO, "Erro Auditoria PNC " + String.valueOf(e.getResponseBodyAsString()));

		}

	}

}
