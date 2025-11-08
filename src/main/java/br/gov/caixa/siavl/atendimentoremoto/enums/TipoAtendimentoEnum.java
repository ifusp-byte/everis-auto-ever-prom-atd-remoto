package br.gov.caixa.siavl.atendimentoremoto.enums;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.mccue.guava.primitives.Ints;


@SuppressWarnings("all")
public enum TipoAtendimentoEnum {

	I(11, "Automatizado");

	private final int codigo;
	private final String descricao;

	TipoAtendimentoEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static int[] codigos() {
		List<Integer> codigos = new ArrayList<>();
		Arrays.stream(TipoAtendimentoEnum.values())
				.forEach(c -> codigos.add(Integer.parseInt(String.valueOf(c.getCodigo()))));
		return Ints.toArray(codigos);
	}

}
