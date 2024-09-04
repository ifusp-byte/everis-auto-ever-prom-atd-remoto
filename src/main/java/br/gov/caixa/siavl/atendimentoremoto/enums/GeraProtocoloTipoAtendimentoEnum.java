package br.gov.caixa.siavl.atendimentoremoto.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wiremock.com.google.common.primitives.Ints;

@SuppressWarnings("all")
public enum GeraProtocoloTipoAtendimentoEnum {

	W(5, "WhatsApp"), 
	T(6, "Telefone"), 
	E(7, "E-mail"), 
	C(8, "Webchat"),
	F(9, "Videoconferência"), 
	P(9, "Presencial");

	private final int codigo;
	private final String descricao;

	GeraProtocoloTipoAtendimentoEnum(int codigo, String descricao) {
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
		Arrays.stream(GeraProtocoloTipoAtendimentoEnum.values()).forEach(c -> codigos.add(Integer.parseInt(String.valueOf(c.getCodigo()))));
		return Ints.toArray(codigos);
	}

}
