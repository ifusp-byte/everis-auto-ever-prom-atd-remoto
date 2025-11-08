package com.company.wextag.exception;

import static com.company.wextag.constant.PurchaseTransactionConstants.INTERNAL_APPLICATION_ERROR;
import static com.company.wextag.util.MessageUtils.getMessage;
import static com.company.wextag.util.MethodUtils.response;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.wextag.controller.PurchaseTransactionController;
import com.company.wextag.dto.GenericResponseDTO;

@SuppressWarnings("all")
@ControllerAdvice(assignableTypes = PurchaseTransactionController.class)
public class PurchaseTransactionException {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handlerConstraintViolation(Exception e, HttpServletRequest httpServletRequest) {

		List<String> errorsForbidden = new LinkedList<>(Arrays.asList(e.getLocalizedMessage()));

		ResponseEntity<?> response = null;

		if (!errorsForbidden.isEmpty()) {

			GenericResponseDTO genericResponse = new GenericResponseDTO();
			genericResponse.setStatusCode(HttpStatus.FORBIDDEN.value());
			genericResponse.setMessage(getMessage(INTERNAL_APPLICATION_ERROR));
			genericResponse.setData(Arrays.asList(errorsForbidden));
			
			response = response(HttpStatus.valueOf(genericResponse.getStatusCode()), genericResponse);

		}

		return response;
	}

}
