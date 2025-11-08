package br.gov.caixa.siavl.atendimentoremoto.constants;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.UTILITY_CLASS;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

@SuppressWarnings("all")
public class ModelosNotaConstants {

	private ModelosNotaConstants() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

	public static final String CONTRATACAO_AUTOMATICA_S = "S";
	public static final String CONTRATACAO_AUTOMATICA_N = "N";
	public static final String NO_CONTENT = "Não foram encontrados Modelos de Nota de Negociação relacionados ao Produto informado.";

}
