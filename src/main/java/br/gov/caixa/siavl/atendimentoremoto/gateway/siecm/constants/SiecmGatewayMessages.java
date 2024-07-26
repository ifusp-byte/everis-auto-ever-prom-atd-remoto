package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants;

public class SiecmGatewayMessages {

	private SiecmGatewayMessages() {
		throw new IllegalStateException("Utility class");
	}

	public static final String SIECM_CRIA_DOSSIE_RETORNO_200 = "OPERAÇÃO REALIZADA COM SUCESSO";
	public static final String SIECM_CRIA_DOSSIE_RETORNO_401 = "ACCESS TOKEN INVÁLIDO, VERIFIQUE O ACCESS TOKEN INFORMADO";
	public static final String SIECM_CRIA_DOSSIE_RETORNO_403 = "ACESSO NAO AUTORIZADO, SISTEMA NÂO CADASTRADO";
	public static final String SIECM_CRIA_DOSSIE_RETORNO_500 = "ERRO INTERNO DO SERVIDOR";

}
