package br.gov.caixa.siavl.atendimentoremoto.util.conta;

public enum ContaSIDECPFEnum {

	CONTA_CORRENTE_PESSOA_FISICA(1, "Conta Corrente Pessoa Física"), 
	POUPANCA_CAIXA(13, "Poupança Caixa"),
	CONTA_CAIXA_FACIL(23, "Conta Caixa Fácil"), 
	CONTA_SALARIO(37, "Conta Salário");

	private final int codigo;
	private final String descricao;

	ContaSIDECPFEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}
