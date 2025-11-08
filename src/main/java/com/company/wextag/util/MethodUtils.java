package com.company.wextag.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@SuppressWarnings("all")
public class MethodUtils {

	@Autowired
	MessageSource messageSource;

	private static ObjectMapper mapper = new ObjectMapper();

	public static JsonNode readTree(Object object) {

		JsonNode value = null;

		try {
			value = mapper.readTree(String.valueOf(object));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public static boolean isValidDate(String dateStr) {
		try {
			String pattern = "yyyy-MM-dd";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			LocalDate.parse(dateStr, formatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public static ResponseEntity response(HttpStatus status, Object body) {
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(body);
	}

}
