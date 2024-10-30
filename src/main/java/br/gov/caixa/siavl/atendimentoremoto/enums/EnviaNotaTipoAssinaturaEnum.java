package br.gov.caixa.siavl.atendimentoremoto.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wiremock.com.google.common.primitives.Ints;

public enum EnviaNotaTipoAssinaturaEnum {

	TOKEN_SMS("TOKEN_SMS"), TOKEN_PRODUTO("TOKEN_PRODUTO"), APP("APP");

	private final String descricao;

	EnviaNotaTipoAssinaturaEnum(String descricao) {

		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static int[] codigos() {
		List<Integer> codigos = new ArrayList<>();
		Arrays.stream(GeraProtocoloTipoAtendimentoEnum.values())
				.forEach(c -> codigos.add(Integer.parseInt(String.valueOf(c.getCodigo()))));
		return Ints.toArray(codigos);
	}

}
