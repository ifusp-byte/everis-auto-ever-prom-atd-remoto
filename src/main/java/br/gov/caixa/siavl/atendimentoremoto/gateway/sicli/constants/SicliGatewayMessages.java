package br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.constants;

@SuppressWarnings("all")
public class SicliGatewayMessages {

	private SicliGatewayMessages() {
		throw new IllegalStateException("Utility class");
	}

	public static final String SICLI_CONTA_ATENDIMENTO_RETORNO_200 = "Consulta SICLI realizada com sucesso";
	public static final String SICLI_CONTA_ATENDIMENTO_RETORNO_NAO_200 = "Falha de comunicação com o serviço do SICLI";

}
