package br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.constants;

public final class IdentificacaoPositivaGatewayMessages2 {

	private IdentificacaoPositivaGatewayMessages2() {
		throw new IllegalStateException("Utility class");
	}

	public static final String DESAFIO_CRIAR_RETORNO_201 = "Desafio criado com sucesso. ";
	public static final String DESAFIO_CRIAR_RETORNO_400 = "O JSON fornecido no body da requisição possui formato inválido. ";
	public static final String DESAFIO_CRIAR_RETORNO_401 = "Não permitido. Token inválida ou expirada (não autenticado).";
	public static final String DESAFIO_CRIAR_RETORNO_404 = "Identificação Positiva não existente para o serviço informado.";
	public static final String DESAFIO_CRIAR_RETORNO_422_1 = "Erro negocial. Pessoa bloqueada.";
	public static final String DESAFIO_CRIAR_RETORNO_422_6 = "Erro negocial. Desafio desativado pelo gestor. ";
	public static final String DESAFIO_CRIAR_RETORNO_422_DESCONHECIDO = "Erro negocial. Desconhecido. ";	
	public static final String DESAFIO_CRIAR_RETORNO_500 = "Erro interno do servidor. ";
	public static final String DESAFIO_CRIAR_RETORNO_DESCONHECIDO = "Erro desconhecido.";
	public static final String DESAFIO_RESPONDER_RETORNO_200 = "A pessoa foi identificada através do desafio.";
	public static final String DESAFIO_RESPONDER_RETORNO_400 = "O JSON fornecido no body da requisição possui formato inválido.";
	public static final String DESAFIO_RESPONDER_RETORNO_401 = "Não permitido. Token inválida ou expirada (não autenticado).";
	public static final String DESAFIO_RESPONDER_RETORNO_404 = "Desafio não encontrado.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_0 = "Erro negocial. Pessoa identificada com sucesso.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_1 = "Erro negocial. Usuário bloqueado por excesso de erros.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_2 = "Erro negocial. Desafio expirado (deve ser gerado um novo).";
	public static final String DESAFIO_RESPONDER_RETORNO_422_3 = "Erro negocial. Desafio finalizado (deve ser gerado um novo).";
	public static final String DESAFIO_RESPONDER_RETORNO_422_4 = "Erro negocial. Quantidade de respostas insuficientes.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_5 = "Erro negocial. Quantidade de respostas corretas insuficientes.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_6 = "Erro negocial. Serviço inativo.";
	public static final String DESAFIO_RESPONDER_RETORNO_422_DESCONHECIDO = "Erro negocial. Desconhecido.";
	public static final String DESAFIO_RESPONDER_RETORNO_500 = "Erro interno do servidor.";
	public static final String DESAFIO_RESPONDER_RETORNO_DESCONHECIDO = "Erro desconhecido.";

}
