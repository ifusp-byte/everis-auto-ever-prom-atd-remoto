package br.gov.caixa.siavl.atendimentoremoto.gateway.identificacaopositiva.constants;

@SuppressWarnings("all")
public final class IdentificacaoPositivaGatewayMessages {

	private IdentificacaoPositivaGatewayMessages() {
		throw new IllegalStateException("Utility class");
	}

	public static final String DESAFIO_CRIAR_RETORNO_201 = "Apresentar as perguntas no front.";
	public static final String DESAFIO_CRIAR_RETORNO_400 = "O JSON fornecido no body da requisição possui formato inválido. ";
	public static final String DESAFIO_CRIAR_RETORNO_401 = "Não permitido. Token inválida ou expirada (não autenticado).";
	public static final String DESAFIO_CRIAR_RETORNO_404 = "Identificação Positiva não existente para o serviço informado.";
	public static final String DESAFIO_CRIAR_RETORNO_422_1 = "Cliente com bloqueio ativo para este canal.";
	public static final String DESAFIO_CRIAR_RETORNO_422_6 = "Erro negocial. Desafio desativado pelo gestor. ";
	public static final String DESAFIO_CRIAR_RETORNO_422_DESCONHECIDO = "Erro negocial. Desconhecido. ";
	public static final String DESAFIO_CRIAR_RETORNO_500 = "O canal está indisponível no momento.";
	public static final String DESAFIO_CRIAR_RETORNO_DESCONHECIDO = "Erro desconhecido.";
	public static final String DESAFIO_RESPONDER_RETORNO_200 = "Identificação positiva realizada com sucesso.";
	public static final String DESAFIO_RESPONDER_RETORNO_400 = "O JSON fornecido no body da requisição possui formato inválido.";
	public static final String DESAFIO_RESPONDER_RETORNO_401 = "Não permitido. Token inválida ou expirada (não autenticado).";
	public static final String DESAFIO_RESPONDER_RETORNO_404 = "Desafio não encontrado.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_0 = "Erro negocial. Pessoa identificada com sucesso.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_1 = "Cliente com bloqueio ativo para este canal.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_2 = "Erro negocial. Desafio expirado (deve ser gerado um novo).";
	public static final String DESAFIO_RESPONDER_RETORNO_422_3 = "Erro negocial. Desafio finalizado (deve ser gerado um novo).";
	public static final String DESAFIO_RESPONDER_RETORNO_422_4 = "Identificação Positiva não validada. Não é possível continuar este atendimento. ";
	public static final String DESAFIO_RESPONDER_RETORNO_422_5 = "Identificação Positiva não validada. Não é possível continuar este atendimento. ";
	public static final String DESAFIO_RESPONDER_RETORNO_422_6 = "Falha na comunicação com o serviço.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_DESCONHECIDO = "Erro negocial. Desconhecido.";
	public static final String DESAFIO_RESPONDER_RETORNO_500 = "Falha na comunicação com o serviço.";
	public static final String DESAFIO_RESPONDER_RETORNO_DESCONHECIDO = "Erro desconhecido.";
	public static final String CONSULTA_REALIZADA = "Consulta realizada em ";
	public static final String PONTO = ".";
	

}
