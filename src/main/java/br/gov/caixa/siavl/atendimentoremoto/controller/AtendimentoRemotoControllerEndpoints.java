package br.gov.caixa.siavl.atendimentoremoto.controller;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.UTILITY_CLASS;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

@SuppressWarnings("all")
public class AtendimentoRemotoControllerEndpoints {

	private AtendimentoRemotoControllerEndpoints() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

	public static final String BASE_URL = "/v1";
	public static final String MODELOS = "/modelos";
	public static final String MODELO_DETALHES = "/modelos/{idModelo}";
	public static final String NOTA_CRIAR = "/notas";
	public static final String NOTA_CONTRATAR = "/notas/contratar";

}
