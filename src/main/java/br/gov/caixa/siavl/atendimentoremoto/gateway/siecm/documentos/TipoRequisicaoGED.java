package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

@SuppressWarnings("all")
public enum TipoRequisicaoGED implements Serializable {

	GRAVA_DOCUMENTO("GRAVA_DOCUMENTO"), PESQUISA_DOSSIE("PESQUISA_DOSSIE"),
	GRAVA_DOCUMENTO_DOSSIE("GRAVA_DOCUMENTO_DOSSIE"), CRIA_DOSSIE("CRIA_DOSSIE");

	private String descricao;

	private TipoRequisicaoGED(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
