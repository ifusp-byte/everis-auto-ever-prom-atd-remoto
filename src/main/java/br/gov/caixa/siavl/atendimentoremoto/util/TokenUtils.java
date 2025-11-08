package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ApplicationScope
@SuppressWarnings("all")
public class TokenUtils {

	private static String DEFAULT_IP_CAIXA = "127.0.0.1";
	private static final ObjectMapper mapper = new ObjectMapper();

	public String getMatriculaFromToken(String jwtToken) {

		String matricula = StringUtils.EMPTY;

		if (!StringUtils.isBlank(jwtToken) && jwtToken != null) {

			String[] split_string = null;
			String base64EncodedBody = null;
			Base64 base64Url = null;
			String body = null;
			JsonNode jsonToken;

			try {
				split_string = jwtToken.split("\\.");
				base64EncodedBody = split_string[1];
				base64Url = new Base64(true);
				body = new String(base64Url.decode(base64EncodedBody));
				jsonToken = mapper.readTree(body);
				if (jsonToken.has("preferred_username")) {
					matricula = jsonToken.get("preferred_username").asText();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return matricula;
	}

	public String getIpFromToken(String jwtToken) {

		String ipUsuario = StringUtils.EMPTY;

		if (!StringUtils.isBlank(jwtToken) && jwtToken != null) {

			String ipRetorno = null;
			String[] split_string = null;
			String base64EncodedBody = null;
			Base64 base64Url = null;
			String body = null;
			JsonNode jsonToken;

			try {
				split_string = jwtToken.split("\\.");
				base64EncodedBody = split_string[1];
				base64Url = new Base64(true);
				body = new String(base64Url.decode(base64EncodedBody));
				jsonToken = mapper.readTree(body);
				if (jsonToken.has("clientAddress")) {
					ipUsuario = jsonToken.get("clientAddress").asText();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			if (ipUsuario == null || StringUtils.isBlank(ipUsuario)) {
				ipUsuario = DEFAULT_IP_CAIXA;
			}
		}
		return ipUsuario;
	}

	public String getUnidadeFromToken(String jwtToken) {

		String unidade = StringUtils.EMPTY;

		if (!StringUtils.isBlank(jwtToken) && jwtToken != null) {

			String[] split_string = null;
			String base64EncodedBody = null;
			Base64 base64Url = null;
			String body = null;
			JsonNode jsonToken;

			try {
				split_string = jwtToken.split("\\.");
				base64EncodedBody = split_string[1];
				base64Url = new Base64(true);
				body = new String(base64Url.decode(base64EncodedBody));
				jsonToken = mapper.readTree(body);
				if (jsonToken.has("co-unidade")) {
					unidade = jsonToken.get("co-unidade").asText();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return unidade;
	}
	
	
	
	
	public static String unidadeFromToken(String jwtToken) {

		String unidade = StringUtils.EMPTY;

		if (!StringUtils.isBlank(jwtToken) && jwtToken != null) {

			String[] split_string = null;
			String base64EncodedBody = null;
			Base64 base64Url = null;
			String body = null;
			JsonNode jsonToken;

			try {
				split_string = jwtToken.split("\\.");
				base64EncodedBody = split_string[1];
				base64Url = new Base64(true);
				body = new String(base64Url.decode(base64EncodedBody));
				jsonToken = mapper.readTree(body);
				if (jsonToken.has("co-unidade")) {
					unidade = jsonToken.get("co-unidade").asText();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return unidade;
	}
	
	
	
	public static String matriculaFromToken(String jwtToken) {

		String matricula = StringUtils.EMPTY;

		if (!StringUtils.isBlank(jwtToken) && jwtToken != null) {

			String[] split_string = null;
			String base64EncodedBody = null;
			Base64 base64Url = null;
			String body = null;
			JsonNode jsonToken;

			try {
				split_string = jwtToken.split("\\.");
				base64EncodedBody = split_string[1];
				base64Url = new Base64(true);
				body = new String(base64Url.decode(base64EncodedBody));
				jsonToken = mapper.readTree(body);
				if (jsonToken.has("preferred_username")) {
					matricula = jsonToken.get("preferred_username").asText();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return matricula;
	}
}
