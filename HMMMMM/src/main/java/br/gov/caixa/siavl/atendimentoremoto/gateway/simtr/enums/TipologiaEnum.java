package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public enum TipologiaEnum {

	T0001000100020010("0001000100020010", "Identidade"), T0001000100020013("0001000100020013", "Identidade"),
	T0001000100020007("0001000100020007", "Identidade"), T0001000100020004("0001000100020004", "Identidade"),
	T0001000100030009("0001000100030009", "Renda"), T0001000100030048("0001000100030048", "Renda"),
	T0001000100030029("0001000100030029", "Renda"), T0001000100030024("0001000100030024", "Renda e Endereço"),
	T0001000100010006("0001000100010006", "Endereco"), T0001000100010001("0001000100010001", "Endereco"),
	T0001000100020005("0001000100020005", "Identidade"), T0001000100020011("0001000100020011", "Identidade"),
	T0001000100020018("0001000100020018", "Identidade"), T0001000300010007("0001000300010007", "Renda"),
	T0008000200010001("0008000200010001", "Renda"), T0001000100030049("0001000100030049", "Renda"),
	T0003000200010144("0003000200010144", "Renda"), T0008000300010034("0008000300010034", "Renda"),
	T0003000100080001("0003000100080001", "Renda");

	private final String codigo;
	private final String descricao;

	TipologiaEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String[] codigos() {
		List<String> codigos = new ArrayList<>();
		Arrays.stream(TipologiaEnum.values()).forEach(c -> codigos.add(String.valueOf(c.getCodigo())));
		return codigos.toArray(String[]::new);
	}

}
