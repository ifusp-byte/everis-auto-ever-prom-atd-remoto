package br.gov.caixa.siavl.atendimentoremoto.sicli.constants;

public class SicliGatewayMessages {

	private SicliGatewayMessages() {
		throw new IllegalStateException("Utility class");
	}

	public static final String SICLI_CONTA_ATENDIMENTO_RETORNO_200 = "Apresentar menu seleção de contas.";
	public static final String SICLI_CONTA_ATENDIMENTO_RETORNO_NAO_200 = "Falha de comunicação com o serviço";

}
