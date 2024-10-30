package br.gov.caixa.siavl.atendimentoremoto.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wiremock.com.google.common.primitives.Ints;

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
