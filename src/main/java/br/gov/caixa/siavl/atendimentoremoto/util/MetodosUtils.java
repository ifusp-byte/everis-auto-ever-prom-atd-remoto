package br.gov.caixa.siavl.atendimentoremoto.util;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto.SimtrOutputDto;

@Component
@SuppressWarnings("all")
public class MetodosUtils {

	private static ObjectMapper mapper = new ObjectMapper();

	public String writeValueAsString(Object object) {

		String value = null;

		try {
			value = mapper.writeValueAsString(object).replaceAll("\\u005C", "").replaceAll("\\n", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public String writeValueAsStringPnc(Object object) {

		String value = null;

		try {
			value = mapper.writeValueAsString(object).replaceAll("\\u005C", "").replaceAll("\\n", "")
					.replaceAll("\\u0022", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public JsonNode readTree(Object object) {

		JsonNode value = null;

		try {
			value = mapper.readTree(String.valueOf(object));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public Clob newSerialClob(String object) {

		Clob clob = null;

		try {
			clob = new SerialClob(object.toCharArray());
		} catch (SerialException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clob;

	}

	public ResponseEntity response(HttpStatus status, Object body) {

		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(body);
	}

	public String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

	public static JsonNode StringToJson(Object object) {

		JsonNode value = null;

		try {
			value = mapper.readTree(String.valueOf(object));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public static ResponseEntity<?> downloadDocumento(SimtrOutputDto simtrOutputDto) {

		byte[] resourceContent = simtrOutputDto.getBinario().getBytes();
		byte[] resourceContentDecoded = Base64.getDecoder().decode(simtrOutputDto.getBinario());
		String fileName = simtrOutputDto.getTipologia() + "." + simtrOutputDto.getExtensao();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(simtrOutputDto.getMimeType()));
		headers.set(HttpHeaders.TRANSFER_ENCODING, "identity");
		headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
		headers.setContentLength(resourceContentDecoded.length);

		return ResponseEntity.ok().headers(headers).contentLength(resourceContentDecoded.length)
				.body(resourceContentDecoded);

	}

	public static ResponseEntity<?> responseSucesso(Object body) {

		return ResponseEntity.status(HttpStatus.OK).body(body);

	}

}
