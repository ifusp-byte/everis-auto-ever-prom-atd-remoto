package br.gov.caixa.siavl.atendimentoremoto.util.conta;

public enum ContaSID01PFEnum {
	
	CONTA_CORRENTE_PESSOA_FISICA(3701, "Conta Corrente Pessoa Física"),
	CONTA_CORRENTE_PF_ENCERRADA_SALDO(1369, "Conta Corrente PF Encerrada com Saldo"),
	POUPANCA_PESSOA_FISICA_CAIXA(1288, "Poupança Pessoa Física Caixa"),
	POUPANCA_PF_ENCERRADA_SALDO(1367, "Poupança PF Encerrada com Saldo"),
	CONTA_SALARIO_CAIXA(3700, "Conta Salário Caixa");

	private final int codigo;
	private final String descricao;

	ContaSID01PFEnum(int codigo, String descricao) {
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
