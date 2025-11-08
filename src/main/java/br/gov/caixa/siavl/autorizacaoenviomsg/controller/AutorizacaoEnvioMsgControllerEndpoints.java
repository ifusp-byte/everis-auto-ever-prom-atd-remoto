package br.gov.caixa.siavl.autorizacaoenviomsg.controller;

import static br.gov.caixa.siavl.autorizacaoenviomsg.util.ConstantsUtils.UTILITY_CLASS;

@SuppressWarnings("all")
public class AutorizacaoEnvioMsgControllerEndpoints {

	private AutorizacaoEnvioMsgControllerEndpoints() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

	public static final String BASE_URL = "/v1";
	public static final String AUTORIZACAO = "/autorizacao/{cpfCnpj}";
	public static final String CREDITOS = "/creditos";
	public static final String SALDO = "/saldo";

}
