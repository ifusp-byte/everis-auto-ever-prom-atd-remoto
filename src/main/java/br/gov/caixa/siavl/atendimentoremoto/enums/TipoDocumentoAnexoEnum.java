package br.gov.caixa.siavl.atendimentoremoto.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.mccue.guava.primitives.Ints;

public enum TipoDocumentoAnexoEnum {

	ACEITE("ACEITE"), OPCIONAL("OPCIONAL"), OBRIGATORIO("OBRIGATORIO");

	private final String descricao;

	TipoDocumentoAnexoEnum(String descricao) {

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
