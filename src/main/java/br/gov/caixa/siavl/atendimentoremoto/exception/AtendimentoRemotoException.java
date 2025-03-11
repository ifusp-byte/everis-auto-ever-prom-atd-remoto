package br.gov.caixa.siavl.atendimentoremoto.exception;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.AUTHORIZATION;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoController;
import br.gov.caixa.siavl.atendimentoremoto.dto.ExceptionOutputDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@SuppressWarnings("all")
@ControllerAdvice(assignableTypes = AtendimentoRemotoController.class)
public class AtendimentoRemotoException {
	

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handlerConstraintViolation(ConstraintViolationException e,
			HttpServletRequest httpServletRequest) {

		List<String> errorsServiceUnavailableFields = new LinkedList<>(Arrays.asList(e.getLocalizedMessage()));

		ResponseEntity<Object> response = null;

		if (!errorsServiceUnavailableFields.isEmpty()) {

			ExceptionOutputDto exceptionOutputDto = new ExceptionOutputDto();
			exceptionOutputDto.setMensagem("Token não informado ou inválido");
			exceptionOutputDto.setErros(Arrays.asList(errorsServiceUnavailableFields));

			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON)
					.body(exceptionOutputDto);
		}
		return response;
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<Object> handlerConstraintViolation(MissingRequestHeaderException e,
			HttpServletRequest httpServletRequest) {

		List<String> errorsServiceUnavailableFields = new LinkedList<>(Arrays.asList(AUTHORIZATION));
		List<String> errorsServiceUnavailableHeader = errorsServiceUnavailableFields.stream()
				.filter(e.getLocalizedMessage()::contains).collect(Collectors.toList());

		ResponseEntity<Object> response = null;

		if (!errorsServiceUnavailableHeader.isEmpty()) {

			ExceptionOutputDto exceptionOutputDto = new ExceptionOutputDto();
			exceptionOutputDto.setMensagem("Token não informado ou inválido");
			exceptionOutputDto.setErros(Arrays.asList(errorsServiceUnavailableHeader));

			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON)
					.body(exceptionOutputDto);
		}
		return response;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handlerConstraintViolation(HttpMessageNotReadableException e,
			HttpServletRequest httpServletRequest) {

		List<String> errorsBadRequesteFields = new LinkedList<>(Arrays.asList(e.getLocalizedMessage()));

		ResponseEntity<Object> response = null;

		if (!errorsBadRequesteFields.isEmpty()) {

			ExceptionOutputDto exceptionOutputDto = new ExceptionOutputDto();
			exceptionOutputDto.setMensagem("Dados da requisição não informado ou inválido");
			exceptionOutputDto.setErros(Arrays.asList(errorsBadRequesteFields));

			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(exceptionOutputDto);
		}
		return response;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handlerConstraintViolation(Exception e, HttpServletRequest httpServletRequest) {

		List<String> errorsForbidden = new LinkedList<>(Arrays.asList(e.getLocalizedMessage()));

		ResponseEntity<Object> response = null;

		if (!errorsForbidden.isEmpty()) {

			ExceptionOutputDto exceptionOutputDto = new ExceptionOutputDto();
			exceptionOutputDto.setMensagem("Erro interno da aplicação");
			exceptionOutputDto.setErros(Arrays.asList(errorsForbidden));

			response = ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
					.body(exceptionOutputDto);
		}
		return response;
	}

}
