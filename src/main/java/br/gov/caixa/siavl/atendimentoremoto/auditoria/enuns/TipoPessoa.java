package br.gov.caixa.siavl.atendimentoremoto.auditoria.enuns;

import java.io.Serializable;

public enum TipoPessoa implements Serializable {

	PF("Pessoa Física"), PJ("Pessoa Jurídica"), NA("Nenhuma");

	private String descricao;

	private TipoPessoa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoPessoa fromValue(String value) {
		for (TipoPessoa tipo : TipoPessoa.values()) {
			if (tipo.name().equals(value)) {
				return tipo;
			}
		}
		throw new IllegalArgumentException("Descrição inválida: " + value);
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}