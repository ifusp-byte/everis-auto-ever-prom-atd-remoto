package br.gov.caixa.siavl.atendimentoremoto.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoDocumentoEnum {

	CPF("CPF"), CNPJ("CNPJ");

	private final String sigla;

	TipoDocumentoEnum(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}

	public static String[] siglas() {
		List<String> siglas = new ArrayList<>();
		Arrays.stream(TipoDocumentoEnum.values()).forEach(c -> siglas.add(String.valueOf(c.getSigla())));
		return siglas.toArray(String[]::new);
	}

}
