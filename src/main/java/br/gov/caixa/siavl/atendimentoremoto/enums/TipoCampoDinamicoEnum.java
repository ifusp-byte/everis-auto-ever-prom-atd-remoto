package br.gov.caixa.siavl.atendimentoremoto.enums;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

@SuppressWarnings("all")
public enum TipoCampoDinamicoEnum {

	DATA("D", "Data"), TEXT("T", "Texto"), MOEDA("M", "Moeda"), NUMERICO("N", "Numerico"), LINK("L", "Link"),
	SELECAO("S", "Seleção");

	private final String sigla;
	private final String descricao;

	TipoCampoDinamicoEnum(String sigla, String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}

}
