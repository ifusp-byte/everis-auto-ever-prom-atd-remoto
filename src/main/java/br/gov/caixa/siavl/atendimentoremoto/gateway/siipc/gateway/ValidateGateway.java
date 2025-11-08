package br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.gateway;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.constants.IdentificacaoPositivaGatewayMessages;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;

@Component
@SuppressWarnings("all")
public class ValidateGateway {

	@Autowired
	DataUtils dataUtils;

	private static String CODIGO_422_0 = "0";
	private static String CODIGO_422_1 = "1";
	private static String CODIGO_422_2 = "2";
	private static String CODIGO_422_3 = "3";
	private static String CODIGO_422_4 = "4";
	private static String CODIGO_422_5 = "5";
	private static String CODIGO_422_6 = "6";

	public String validateGatewayStatusDesafioCriar(int statusCode, String codigo422) {

		String statusMessage = null;

		if (HttpStatus.CREATED.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_201;
		} else if (HttpStatus.BAD_REQUEST.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_400;
		} else if (HttpStatus.UNAUTHORIZED.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_401;
		} else if (HttpStatus.NOT_FOUND.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_404;
		} else if (HttpStatus.UNPROCESSABLE_ENTITY.value() == statusCode) {
			statusMessage = validateGatewayStatusDesafioCriarCodigo422(codigo422);
		} else if (HttpStatus.INTERNAL_SERVER_ERROR.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_500;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_DESCONHECIDO;
		}

		return statusMessage;
	}

	public String validateGatewayStatusDesafioCriarCodigo422(String codigo422) {

		String statusMessage = null;

		if (CODIGO_422_1.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_422_1;
		} else if (CODIGO_422_6.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_422_6;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_CRIAR_RETORNO_422_DESCONHECIDO;
		}

		return statusMessage;

	}

	public String validateGatewayStatusDesafioResponder(int statusCode, String codigo422) {

		String statusMessage = null;

		if (HttpStatus.OK.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_200;
		} else if (HttpStatus.BAD_REQUEST.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_400;
		} else if (HttpStatus.UNAUTHORIZED.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_401;
		} else if (HttpStatus.NOT_FOUND.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_404;
		} else if (HttpStatus.UNPROCESSABLE_ENTITY.value() == statusCode) {
			statusMessage = validateGatewayStatusDesafioResponderCodigo422(codigo422);
		} else if (HttpStatus.INTERNAL_SERVER_ERROR.value() == statusCode) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_500;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_DESCONHECIDO;
		}

		return statusMessage;
	}

	public String validateGatewayStatusDesafioResponderCodigo422(String codigo422) {

		String statusMessage = null;

		if (CODIGO_422_0.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_0;
		} else if (CODIGO_422_1.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_1;
		} else if (CODIGO_422_2.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_2;
		} else if (CODIGO_422_3.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_3;
		} else if (CODIGO_422_4.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_4
					+ IdentificacaoPositivaGatewayMessages.CONSULTA_REALIZADA + dataUtils.formataData(new Date())
					+ IdentificacaoPositivaGatewayMessages.PONTO;
		} else if (CODIGO_422_5.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_5
					+ IdentificacaoPositivaGatewayMessages.CONSULTA_REALIZADA + dataUtils.formataData(new Date())
					+ IdentificacaoPositivaGatewayMessages.PONTO;
		} else if (CODIGO_422_6.equalsIgnoreCase(codigo422)) {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_6;
		} else {
			statusMessage = IdentificacaoPositivaGatewayMessages.DESAFIO_RESPONDER_RETORNO_422_DESCONHECIDO;
		}

		return statusMessage;
	}

}
