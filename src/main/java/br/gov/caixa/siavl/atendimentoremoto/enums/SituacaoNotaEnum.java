package br.gov.caixa.siavl.atendimentoremoto.enums;

public enum SituacaoNotaEnum {

	PENDENTE_CONTRATACAO("23", "Pendente de Contrataçao");

	private final String codigo;
	private final String descricao;

	SituacaoNotaEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}
