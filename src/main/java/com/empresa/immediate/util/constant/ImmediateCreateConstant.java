package com.empresa.immediate.util.constant;

public class ImmediateCreateConstant {

	private ImmediateCreateConstant() {
		throw new IllegalStateException("Utility class");
	}

	public static final String HEADER_APIM_GUID = "apim_guid";
	public static final String STATUS_COBRANCA = "ATIVA";

	public static final String START_SCHEMA_VALIDATION = "Starting Physical Schema Validation... ";
	public static final String END_SCHEMA_VALIDATION = "Ending Physical Schema Validation... ";

	public static final int MSG_STATUS_400 = 400;
	public static final int MSG_STATUS_401 = 401;
	public static final int MSG_STATUS_503 = 503;

	public static final String MSG_TYPE_400_1 = "internal/CobOperacaoInvalida";
	public static final String MSG_TYPE_400_2 = "https://pix.bcb.gov.br/api/v2/error/CobOperacaoInvalida";
	public static final String MSG_TYPE_401 = "https://pix.bcb.gov.br/api/v2/error/AcessoNegado";
	public static final String MSG_TYPE_503 = "https://pix.bcb.gov.br/api/v2/error/ServicoIndisponivel";

	public static final String MSG_TITLE_400 = "Cobrança Inválida";
	public static final String MSG_TITLE_401 = "Acesso Negado";
	public static final String MSG_TITLE_503 = "Serviço Indisponível";

	public static final String MSG_DETAIL_400_1 = "A requisição que busca criar uma cobrança para pagamento imediato dinâmico não respeita o schema ou está semanticamente errada.";
	public static final String MSG_DETAIL_400_2 = "O campo chave não foi encontrado.";
	public static final String MSG_DETAIL_400_3 = "O campo chave não se encontra ativo para pagamentos.";
	public static final String MSG_DETAIL_400_4 = "O campo chave corresponde a uma conta que não pertence a este usuário recebedor.";
	public static final String MSG_DETAIL_401 = "Requisição de participante autenticado que viola alguma regra de autorização.";
	public static final String MSG_DETAIL_503 = "Serviço não está disponível no momento. Serviço solicitado pode estar em manutenção ou fora da janela de funcionamento.";
	public static final String MSG_DETAIL_SAQUE_TROCO = "Serviço Saque ou Troco não está disponível no momento.";

	public static final String MSG_RAZAO_START = "O campo Cob.";
	public static final String MSG_RAZAO_END = " não respeita o _schema_.";
	public static final String MSG_PROPRIEDADE = "Cob.";

	public static final String BODY_TXID = "txid";
	public static final String BODY_CALENDARIO_EXPIRACAO = "calendario.expiracao";
	public static final String BODY_DEVEDOR_CPF = "devedor.cpf";
	public static final String BODY_DEVEDOR_CNPJ = "devedor.cnpj";
	public static final String BODY_DEVEDOR_NOME = "devedor.nome";
	public static final String BODY_LOC_ID = "loc.id";
	public static final String BODY_VALOR_ORIGINAL = "valor.original";
	public static final String BODY_VALOR_MODALIDADEALTERACAO = "valor.modalidadeAlteracao";
	public static final String BODY_CHAVE = "chave";
	public static final String BODY_SOLICITACAOPAGADOR = "solicitacaoPagador";
	public static final String BODY_INFOADICIONAIS = "infoAdicionais";
	public static final String BODY_VALOR_RETIRADA_SAQUE_VALOR = "valor.retirada.saque.valor";
	public static final String BODY_VALOR_RETIRADA_SAQUE_MODALIDADE_ALTERACAO = "valor.retirada.saque.modalidadeAlteracao";
	public static final String BODY_VALOR_RETIRADA_SAQUE_MODALIDADE_AGENTE = "valor.retirada.saque.modalidadeAgente";
	public static final String BODY_VALOR_RETIRADA_SAQUE_PRESTADOR_SERVICO_SAQUE = "valor.retirada.saque.prestadorDoServicoDeSaque";
	public static final String BODY_VALOR_RETIRADA_TROCO_VALOR = "valor.retirada.troco.valor";
	public static final String BODY_VALOR_RETIRADA_TROCO_MODALIDADE_ALTERACAO = "valor.retirada.troco.modalidadeAlteracao";
	public static final String BODY_VALOR_RETIRADA_TROCO_MODALIDADE_AGENTE = "valor.retirada.troco.modalidadeAgente";
	public static final String BODY_VALOR_RETIRADA_TROCO_PRESTADOR_SERVICO_SAQUE = "valor.retirada.troco.prestadorDoServicoDeSaque";

	public static final String HEADER_APIM_CLIENT_ID = "apim_client_id";
	public static final String HEADER_LOCAL_BRANCH = "localBranch";
	public static final String HEADER_ACCOUNT_NUMBER = "accountNumber";
	public static final String HEADER_TAXID = "taxid";
	public static final String HEADER_ALIAS_STATUS = "aliasStatus";
	
	public static final String MSG_RAZAO_TXID = "O TXid não respeita o schema.";
	public static final String MSG_RAZAO_CALENDARIO_EXPIRACAO = "O campo Cob.Expiracao é igual ou menor que zero.";	
	public static final String MSG_RAZAO_DEVEDOR = "O objeto Cob.Devedor não respeita o schema.";
	public static final String MSG_RAZAO_LOC_ID = "O objeto Cob.LOC não respeita o schema.";
	public static final String MSG_RAZAO_VALOR_ORIGINAL = "O campo cob.valor.original não respeita o _schema_.";
	public static final String MSG_RAZAO_VALOR_MODALIDADEALTERACAO = "O campo Cob.modalidade.alteração não respeita o _schema_.";
	public static final String MSG_RAZAO_CHAVE1 = "A chave Pix não respeita o schema.";
	public static final String MSG_RAZAO_CHAVE2 = "O campo chave não foi encontrado.";
	public static final String MSG_RAZAO_CHAVE3 = "O campo chave não está ativo para pagamentos.";
	public static final String MSG_RAZAO_CHAVE4 = "O campo chave não está relacionado ao cliente cadastrado.";
	public static final String MSG_RAZAO_CADASTRO = "Cliente não autorizado.";
	public static final String MSG_RAZAO_SOLICITACAOPAGADOR = "Solicitação Pagador não respeita o schema.";	
	public static final String MSG_RAZAO_INFOADICIONAIS = "Informações Adicionais não respeita  o schema.";
	
	public static final String MSG_PROPRIEDADE_TXID = "Cob.txid";
	public static final String MSG_PROPRIEDADE_CALENDARIO_EXPIRACAO = "Cob.Expiracao";
	public static final String MSG_PROPRIEDADE_DEVEDOR = "Cob.Devedor";
	public static final String MSG_PROPRIEDADE_LOC_ID = "Cob.LOC";
	public static final String MSG_PROPRIEDADE_VALOR_ORIGINAL_1 = "cob.valor.original";
	public static final String MSG_PROPRIEDADE_VALOR_ORIGINAL_2 = "Cob.valor";
	public static final String MSG_PROPRIEDADE_VALOR_MODALIDADEALTERACAO = "Cob.modalidade.alteração";
	public static final String MSG_PROPRIEDADE_CHAVE = "Cob.chave";
	public static final String MSG_PROPRIEDADE_SOLICITACAOPAGADOR = "Cob.solicitação pagador";
	public static final String MSG_PROPRIEDADE_INFOADICIONAIS = "Cob.informações.adicionais";
	public static final String MSG_PROPRIEDADE_CADASTRO = "Cob.cadastro";

	public static final String MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_VALOR = "Cob.saque.valor";
	public static final String MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_MODALIDADE_ALTERACAO = "Cob.saque.modalater";
	public static final String MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_MODALIDADE_AGENTE = "Cob.saque.modalagent";
	public static final String MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_PRESTADOR_SERVICO_SAQUE = "Cob.saque.pss";
	public static final String MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_VALOR = "Cob.troco.valor";
	public static final String MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_MODALIDADE_ALTERACAO = "Cob.troco.modalater";
	public static final String MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_MODALIDADE_AGENTE = "Cob.troco.modalagent";
	public static final String MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_PRESTADOR_SERVICO_SAQUE = "Cob.troco.pss";

	public static final String MSG_VALOR_RETIRADA_SAQUE = "Serviço Saque não disponível";
	public static final String MSG_VALOR_RETIRADA_TROCO = "Serviço Troco não disponível";

}
