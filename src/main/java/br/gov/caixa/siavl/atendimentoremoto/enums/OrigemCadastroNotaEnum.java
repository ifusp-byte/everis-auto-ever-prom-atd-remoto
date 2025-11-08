package br.gov.caixa.siavl.atendimentoremoto.enums;

public enum OrigemCadastroNotaEnum {

	AUTOMATIZADA("AUTOMATIZADA");

	private final String descricao;

	OrigemCadastroNotaEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
