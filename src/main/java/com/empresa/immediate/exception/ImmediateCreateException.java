package com.empresa.immediate.exception;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.empresa.immediate.controller.ImmediateCreateController;
import com.empresa.immediate.dto.ViolacaoDTO;
import com.empresa.immediate.service.ImmediateCreateValidatorService;
import com.empresa.immediate.util.constant.ImmediateCreateConstant;
import com.empresa.immediate.util.method.ImmediateMethod;

@ControllerAdvice(assignableTypes = ImmediateCreateController.class)
public class ImmediateCreateException {

	@Autowired
	ImmediateCreateValidatorService immediateCreateValidatorService;

	@Autowired
	ImmediateMethod util;

	private static HttpHeaders immediateCreateResponseHeaders = new HttpHeaders();

	static Logger logger = Logger.getLogger(ImmediateCreateException.class.getName());

	public void inputDataValidationBuild(List<String> errorsBody, List<ViolacaoDTO> violacoesBody) {

		errorsBody.stream().forEach(error -> {

			try {

				var methodInputDataValidation = ImmediateCreateValidatorService.class.getDeclaredMethod(

						error.replace(".", "").replace("_", "").toLowerCase() + "ArgumentValidation");

				Object violacaoCustomized = methodInputDataValidation.invoke(immediateCreateValidatorService);

				violacoesBody.add((ViolacaoDTO) violacaoCustomized);

			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}

		});

	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handlerConstraintViolation(ConstraintViolationException e,
			HttpServletRequest immediateCreateRequestDTO) {

		List<String> errorsServiceUnavailableFields = new LinkedList<>(
				Arrays.asList(ImmediateCreateConstant.HEADER_APIM_CLIENT_ID));

		List<String> errorsBadRequestFields = new LinkedList<>(Arrays.asList(
				ImmediateCreateConstant.HEADER_LOCAL_BRANCH, ImmediateCreateConstant.HEADER_ACCOUNT_NUMBER,
				ImmediateCreateConstant.HEADER_TAXID, ImmediateCreateConstant.HEADER_ALIAS_STATUS));

		List<String> errorsServiceUnavailableHeader = errorsServiceUnavailableFields.stream()
				.filter(e.getLocalizedMessage()::contains).collect(Collectors.toList());

		List<String> errorsBadRequestHeader = errorsBadRequestFields.stream().filter(e.getLocalizedMessage()::contains)
				.collect(Collectors.toList());

		logger.log(Level.INFO, ImmediateCreateConstant.START_SCHEMA_VALIDATION);

		List<ViolacaoDTO> violacoesBadRequest = new ArrayList<>();
		List<ViolacaoDTO> violacoesErrors = new ArrayList<>();
		List<ViolacaoDTO> violacoesErrorsResult = new ArrayList<>();

		ResponseEntity<Object> immediateCreateResponse = null;

		if (((!errorsServiceUnavailableHeader.isEmpty()) && (!errorsBadRequestHeader.isEmpty()))
				|| (!errorsServiceUnavailableHeader.isEmpty())) {

			immediateCreateResponse = util.responseEntityBuild(HttpStatus.SERVICE_UNAVAILABLE,
					immediateCreateResponseHeaders,

					util.errorResultBuild(immediateCreateRequestDTO.getHeader(ImmediateCreateConstant.HEADER_APIM_GUID),
							ImmediateCreateConstant.MSG_STATUS_503, ImmediateCreateConstant.MSG_TYPE_503,
							ImmediateCreateConstant.MSG_TITLE_503, ImmediateCreateConstant.MSG_DETAIL_503, null));

		} else {

			if (!errorsBadRequestHeader.stream()
					.filter(error -> error.equalsIgnoreCase(ImmediateCreateConstant.HEADER_ALIAS_STATUS))
					.collect(Collectors.toList()).isEmpty()) {

				inputDataValidationBuild(errorsBadRequestHeader.stream()
						.filter(error -> error.equalsIgnoreCase(ImmediateCreateConstant.HEADER_ALIAS_STATUS))
						.collect(Collectors.toList()), violacoesBadRequest);

				util.errorResultBuild(immediateCreateRequestDTO.getHeader(ImmediateCreateConstant.HEADER_APIM_GUID),
						ImmediateCreateConstant.MSG_STATUS_503, ImmediateCreateConstant.MSG_TYPE_503,
						ImmediateCreateConstant.MSG_TITLE_503, ImmediateCreateConstant.MSG_DETAIL_503, null);

			} else {

				if (!errorsBadRequestHeader.stream()
						.filter(error -> error.equalsIgnoreCase(ImmediateCreateConstant.HEADER_ALIAS_STATUS))
						.collect(Collectors.toList()).isEmpty()) {

					inputDataValidationBuild(errorsBadRequestHeader.stream()
							.filter(error -> error.equalsIgnoreCase(ImmediateCreateConstant.HEADER_ALIAS_STATUS))
							.collect(Collectors.toList()), violacoesBadRequest);

					violacoesErrors.addAll(violacoesBadRequest.stream().distinct().collect(Collectors.toList()));
					violacoesErrorsResult.addAll(violacoesErrors.stream().distinct().collect(Collectors.toList()));

					immediateCreateResponse = util.responseEntityBuild(HttpStatus.BAD_REQUEST,
							immediateCreateResponseHeaders,

							util.errorResultBuild(
									immediateCreateRequestDTO.getHeader(ImmediateCreateConstant.HEADER_APIM_GUID),
									ImmediateCreateConstant.MSG_STATUS_400, ImmediateCreateConstant.MSG_TYPE_400_2,
									ImmediateCreateConstant.MSG_TITLE_400, ImmediateCreateConstant.MSG_DETAIL_400_3,
									violacoesErrorsResult));

				} else {

					inputDataValidationBuild(errorsBadRequestHeader, violacoesBadRequest);

					violacoesErrors.addAll(violacoesBadRequest.stream().distinct().collect(Collectors.toList()));
					violacoesErrorsResult.addAll(violacoesErrors.stream().distinct().collect(Collectors.toList()));

					immediateCreateResponse = util.responseEntityBuild(HttpStatus.BAD_REQUEST,
							immediateCreateResponseHeaders,
							util.errorResultBuild(
									immediateCreateRequestDTO.getHeader(ImmediateCreateConstant.HEADER_APIM_GUID),
									ImmediateCreateConstant.MSG_STATUS_400, ImmediateCreateConstant.MSG_TYPE_400_2,
									ImmediateCreateConstant.MSG_TITLE_400, ImmediateCreateConstant.MSG_DETAIL_400_2,
									violacoesErrorsResult));

				}

			}

		}

		logger.log(Level.SEVERE, e.getLocalizedMessage());
		logger.log(Level.INFO, ImmediateCreateConstant.END_SCHEMA_VALIDATION);

		return immediateCreateResponse;

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)

	public ResponseEntity<Object> handlerBodyValidation(HttpServletRequest immediateCreateRequestDTO,
			MethodArgumentNotValidException e) {

		logger.log(Level.INFO, ImmediateCreateConstant.START_SCHEMA_VALIDATION);

		List<FieldError> errorsArgumentNotValid = e.getBindingResult().getFieldErrors();
		List<String> errorsAll = new ArrayList<>();

		errorsArgumentNotValid.stream().map(error -> errorsAll.add(error.getField())).collect(Collectors.toList());

		List<String> errorsServiceUnavailableFields = new LinkedList<>(Arrays.asList(

				ImmediateCreateConstant.BODY_VALOR_RETIRADA_SAQUE_VALOR,
				ImmediateCreateConstant.BODY_VALOR_RETIRADA_SAQUE_MODALIDADE_ALTERACAO,
				ImmediateCreateConstant.BODY_VALOR_RETIRADA_SAQUE_MODALIDADE_AGENTE,
				ImmediateCreateConstant.BODY_VALOR_RETIRADA_SAQUE_PRESTADOR_SERVICO_SAQUE,
				ImmediateCreateConstant.BODY_VALOR_RETIRADA_TROCO_VALOR,
				ImmediateCreateConstant.BODY_VALOR_RETIRADA_TROCO_MODALIDADE_ALTERACAO,
				ImmediateCreateConstant.BODY_VALOR_RETIRADA_TROCO_MODALIDADE_AGENTE,
				ImmediateCreateConstant.BODY_VALOR_RETIRADA_TROCO_PRESTADOR_SERVICO_SAQUE

		));

		List<String> errorsBadRequestFields = new LinkedList<>(Arrays.asList(

				ImmediateCreateConstant.BODY_TXID, ImmediateCreateConstant.BODY_CALENDARIO_EXPIRACAO,
				ImmediateCreateConstant.BODY_DEVEDOR_CPF, ImmediateCreateConstant.BODY_DEVEDOR_CNPJ,
				ImmediateCreateConstant.BODY_DEVEDOR_NOME, ImmediateCreateConstant.BODY_LOC_ID,
				ImmediateCreateConstant.BODY_VALOR_ORIGINAL, ImmediateCreateConstant.BODY_VALOR_MODALIDADEALTERACAO,
				ImmediateCreateConstant.BODY_CHAVE, ImmediateCreateConstant.BODY_SOLICITACAOPAGADOR,
				ImmediateCreateConstant.BODY_INFOADICIONAIS

		));

		List<String> errorsGeneralBody = errorsAll.stream().filter(Predicate.not(errorsBadRequestFields::contains))
				.filter(Predicate.not(errorsServiceUnavailableFields::contains)).collect(Collectors.toList());

		List<String> errorsServiceUnavailableBody = errorsAll.stream().filter(errorsServiceUnavailableFields::contains)
				.collect(Collectors.toList());

		List<String> errorsBadRequestBody = errorsAll.stream().filter(errorsBadRequestFields::contains)
				.collect(Collectors.toList());

		List<ViolacaoDTO> violacoesGeneral = new ArrayList<>();
		List<ViolacaoDTO> violacoesServiceUnavailable = new ArrayList<>();
		List<ViolacaoDTO> violacoesBadRequest = new ArrayList<>();

		errorsGeneralBody.stream().forEach(error -> violacoesGeneral.add(new ViolacaoDTO(

				ImmediateCreateConstant.MSG_RAZAO_START + error + ImmediateCreateConstant.MSG_RAZAO_END,
				ImmediateCreateConstant.MSG_PROPRIEDADE + error)));

		ResponseEntity<Object> immediateCreateResponse = null;
		
		if(((!errorsServiceUnavailableBody.isEmpty()) && (!errorsBadRequestBody.isEmpty()))
				|| (!errorsServiceUnavailableBody.isEmpty())) {
			
			inputDataValidationBuild(errorsServiceUnavailableBody, violacoesServiceUnavailable);
			
			List<ViolacaoDTO> violacoesErrorsResult = new ArrayList<>();
			
			violacoesErrorsResult.addAll(violacoesServiceUnavailable.stream().distinct().collect(Collectors.toList()));
			
			
			
			
			immediateCreateResponse = util.responseEntityBuild(HttpStatus.SERVICE_UNAVAILABLE,
					immediateCreateResponseHeaders,
					util.errorResultBuild(
							immediateCreateRequestDTO.getHeader(ImmediateCreateConstant.HEADER_APIM_GUID),
							ImmediateCreateConstant.MSG_STATUS_503, ImmediateCreateConstant.MSG_TYPE_503,
							ImmediateCreateConstant.MSG_TITLE_503, ImmediateCreateConstant.MSG_DETAIL_SAQUE_TROCO,
							violacoesErrorsResult));
			
			
			
			
		} else {
			
			
			inputDataValidationBuild(errorsBadRequestBody, violacoesBadRequest);
			
			List<ViolacaoDTO> violacoesErrors = new ArrayList<>();
			List<ViolacaoDTO> violacoesErrorsResult = new ArrayList<>();
			
			violacoesErrors.addAll(violacoesGeneral.stream().distinct().collect(Collectors.toList()));
			violacoesErrors.addAll(violacoesBadRequest.stream().distinct().collect(Collectors.toList()));
			violacoesErrorsResult.addAll(violacoesErrors.stream().distinct().collect(Collectors.toList()));
			
			
			
			immediateCreateResponse = util.responseEntityBuild(HttpStatus.BAD_REQUEST,
					immediateCreateResponseHeaders,
					util.errorResultBuild(
							immediateCreateRequestDTO.getHeader(ImmediateCreateConstant.HEADER_APIM_GUID),
							ImmediateCreateConstant.MSG_STATUS_400, ImmediateCreateConstant.MSG_TYPE_400_1,
							ImmediateCreateConstant.MSG_TITLE_400, ImmediateCreateConstant.MSG_DETAIL_400_1,
							violacoesErrorsResult));
			
		}
		
		logger.log(Level.SEVERE, e.getLocalizedMessage());
		logger.log(Level.INFO, ImmediateCreateConstant.END_SCHEMA_VALIDATION);
		
		return immediateCreateResponse;
		
		

	}
}
