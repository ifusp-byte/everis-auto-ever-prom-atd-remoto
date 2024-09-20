package br.gov.caixa.siavl.atendimentoremoto.util;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;

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

}
