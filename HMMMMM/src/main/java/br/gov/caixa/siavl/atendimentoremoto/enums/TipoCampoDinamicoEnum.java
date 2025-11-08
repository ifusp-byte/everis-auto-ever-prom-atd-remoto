package br.gov.caixa.siavl.atendimentoremoto.enums;

@SuppressWarnings("all")
public enum TipoCampoDinamicoEnum {

	DATA("D");

	private final String descricao;

	TipoCampoDinamicoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
