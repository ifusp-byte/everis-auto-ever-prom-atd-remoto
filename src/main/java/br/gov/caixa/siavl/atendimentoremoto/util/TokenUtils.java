package br.gov.caixa.siavl.atendimentoremoto.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import com.auth0.jwt.impl.JWTParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ApplicationScope
@SuppressWarnings({ "squid:S4507", "squid:S117", "squid:S2129" })
public class TokenUtils {

	String accessToken;

	JWTParser parser = new JWTParser();

	public String getMatriculaFromToken(String jwtToken) {

		String matricula = null;

		String[] split_string = jwtToken.split("\\.");
		String base64EncodedBody = split_string[1];

		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonToken;
		try {
			jsonToken = mapper.readTree(body);
			if (jsonToken.has("preferred_username")) {
				matricula = jsonToken.get("preferred_username").asText();
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return matricula;
	}

	public String getIpFromToken(String jwtToken) {

		String ipUsuario = null;

		String[] split_string = jwtToken.split("\\.");
		String base64EncodedBody = split_string[1];

		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonToken;
		try {
			jsonToken = mapper.readTree(body);
			if (jsonToken.has("clientAddress")) {
				ipUsuario = jsonToken.get("clientAddress").asText();
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return ipUsuario;
	}

	public String getUnidadeFromToken(String jwtToken) {

		String unidade = null;

		String[] split_string = jwtToken.split("\\.");
		String base64EncodedBody = split_string[1];

		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonToken;
		try {
			jsonToken = mapper.readTree(body);
			if (jsonToken.has("co-unidade")) {
				unidade = jsonToken.get("co-unidade").asText();
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return unidade;
	}

	public boolean isTwoFactorCertified(String jwtToken) {
		String[] split_string = jwtToken.split("\\.");
		String base64EncodedBody = split_string[1];

		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonToken;
		try {
			jsonToken = mapper.readTree(body);
			if (jsonToken.has("2f_cert")) {
				return jsonToken.get("2f_cert").asBoolean();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return false;
	}
}
