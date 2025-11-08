package br.gov.caixa.siavl.autorizacaoenviomsg.constants;

import static br.gov.caixa.siavl.autorizacaoenviomsg.util.ConstantsUtils.UTILITY_CLASS;

@SuppressWarnings("all")
public class MessageConstants {

	private MessageConstants() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

	// classe
	// AutorizacaoEnvioMsgException
	public static final String EXCEPTION_ERRO_TOKEN = "exception.erro.token";
	public static final String EXCEPTION_ERRO_DADOS_REQUISICAO = "exception.erro.dados.requisicao";
	public static final String EXCEPTION_ERRO_INTERNO = "exception.erro.interno";

}
