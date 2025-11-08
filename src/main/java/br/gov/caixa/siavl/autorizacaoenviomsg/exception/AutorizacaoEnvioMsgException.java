package br.gov.caixa.siavl.autorizacaoenviomsg.exception;

import static br.gov.caixa.siavl.autorizacaoenviomsg.constants.MessageConstants.EXCEPTION_ERRO_DADOS_REQUISICAO;
import static br.gov.caixa.siavl.autorizacaoenviomsg.constants.MessageConstants.EXCEPTION_ERRO_INTERNO;
import static br.gov.caixa.siavl.autorizacaoenviomsg.constants.MessageConstants.EXCEPTION_ERRO_TOKEN;
import static br.gov.caixa.siavl.autorizacaoenviomsg.util.ConstantsUtils.AUTHORIZATION;
import static br.gov.caixa.siavl.autorizacaoenviomsg.util.MessageUtils.getMessage;

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

import br.gov.caixa.siavl.autorizacaoenviomsg.controller.AutorizacaoEnvioMsgController;
import br.gov.caixa.siavl.autorizacaoenviomsg.dto.ExceptionOutputDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@SuppressWarnings("all")
@ControllerAdvice(assignableTypes = AutorizacaoEnvioMsgController.class)
public class AutorizacaoEnvioMsgException {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handlerConstraintViolation(ConstraintViolationException e,
			HttpServletRequest httpServletRequest) {
		List<String> errorsServiceUnavailableFields = new LinkedList<>(Arrays.asList(e.getLocalizedMessage()));
		ResponseEntity<Object> response = null;
		if (!errorsServiceUnavailableFields.isEmpty()) {
			ExceptionOutputDto exceptionOutputDto = new ExceptionOutputDto();
			exceptionOutputDto.setMensagem(getMessage(EXCEPTION_ERRO_TOKEN));
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
			exceptionOutputDto.setMensagem(getMessage(EXCEPTION_ERRO_TOKEN));
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
			exceptionOutputDto.setMensagem(getMessage(EXCEPTION_ERRO_DADOS_REQUISICAO));
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
			exceptionOutputDto.setMensagem(getMessage(EXCEPTION_ERRO_INTERNO));
			exceptionOutputDto.setErros(Arrays.asList(errorsForbidden));
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(exceptionOutputDto);
		}
		return response;
	}
}