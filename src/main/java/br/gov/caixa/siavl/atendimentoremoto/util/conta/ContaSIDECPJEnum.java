package br.gov.caixa.siavl.atendimentoremoto.util.conta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.mccue.guava.primitives.Ints;

@SuppressWarnings("all")
public enum ContaSIDECPJEnum {

	CONTA_CORRENTE_PESSOA_JURIDICA(3, "Conta Corrente Pessoa Jurídica"), DEPOSITO_POUPANCA(22, "Depósito Poupança"),
	PRESTACAO_CONTA_CORRESPONDENTE_CAIXA_AQUI(43, "Prestação de Conta Correspondente Caixa Aqui"),
	CHEQUE_ADMINISTRATIVO(93, "Cheque Administrativo");

	private final int codigo;
	private final String descricao;

	ContaSIDECPJEnum(int codigo, String descricao) {
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
		Arrays.stream(ContaSIDECPJEnum.values())
				.forEach(c -> codigos.add(Integer.parseInt(String.valueOf(c.getCodigo()))));
		return Ints.toArray(codigos);
	}

}
