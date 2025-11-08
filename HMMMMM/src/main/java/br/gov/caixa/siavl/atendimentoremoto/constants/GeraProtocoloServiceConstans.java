package br.gov.caixa.siavl.atendimentoremoto.constants;

@SuppressWarnings("all")
public class GeraProtocoloServiceConstans {

	private GeraProtocoloServiceConstans() {
		throw new IllegalStateException("Utility class");
	}

	public static final String MENSAGEM_PADRAO_ERRO_PROTOCOLO = "Não foi possível gerar protocolo de atendimento.";
	public static final String NOME_CLIENTE = "Nome do Cliente: ";
	public static final String RAZAO_SOCIAL = "Razão Social: ";
	public static final String CONTAS = "Contas: ";
	public static final String SOCIOS = "Sócios: ";
	public static final String MENSAGEM_SICLI = "Mensagem SICLI: ";
	public static final String DADOS_CPF = "Os dados do CPF ";
	public static final String DADOS_CNPJ = "Os dados do CNPJ ";
	public static final String INVALIDOS = " são inválidos.";
	public static final String INFORMAR_CPF_CNPJ_CLIENTE = "Informe o CPF ou CNPJ do cliente";

}
