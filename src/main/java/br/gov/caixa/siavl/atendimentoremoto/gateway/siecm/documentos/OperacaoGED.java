package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

@SuppressWarnings("all")
public enum OperacaoGED implements Serializable {

	SIECM_OPERACAO_GRAVAR_DOCUMENTO("ECM/gravaDocumento"), SIECM_OPERACAO_LOCALIZA_DOCUMENTO("ECM/localizaDocumento"),
	DOSSIE_LISTA_DOCUMENTOS("ECM/reuso/dossie/listaDocumentos"),
	DOSSIE_GRAVA_DOCUMENTO("ECM/reuso/dossie/gravaDocumento"), DOSSIE_CRIAR_PASTA_DOSSIE("ECM/reuso/dossie"),
	INCLUIR_DOCUMENTOS("ECM/v1/documentos/incluir"),;

	private String descricao;

	private OperacaoGED(String descricao) {
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