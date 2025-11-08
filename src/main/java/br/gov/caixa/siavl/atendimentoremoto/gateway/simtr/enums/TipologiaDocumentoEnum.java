package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.enums;

@SuppressWarnings("all")
public enum TipologiaDocumentoEnum {

	IDENTIDADE(1, "Identidade"), RENDA(2, "Renda"), ENDERECO(3, "Endereco"), DESCONHECIDO(3, "Desconhecido");

	private final int codigo;
	private final String descricao;

	TipologiaDocumentoEnum(int codigo, String descricao) {
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
