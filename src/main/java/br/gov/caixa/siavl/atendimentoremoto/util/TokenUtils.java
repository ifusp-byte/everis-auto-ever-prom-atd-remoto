package br.gov.caixa.siavl.atendimentoremoto.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import com.auth0.jwt.impl.JWTParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ApplicationScope
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
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return matricula;
	}
}
