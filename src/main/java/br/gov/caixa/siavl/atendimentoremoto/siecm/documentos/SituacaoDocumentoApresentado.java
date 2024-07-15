package br.gov.caixa.siavl.atendimentoremoto.siecm.documentos;

import java.io.Serializable;

public enum SituacaoDocumentoApresentado implements Serializable {

	SEM_PENDENCIA_ENVIO(0), COM_PENDENCIA_ENVIO(1), APROVADO_RETAGUARDA(2), REJEITADO_RETAGUARDA(3);

	private int situacao;

	SituacaoDocumentoApresentado(int situacao) {
		this.situacao = situacao;
	}

	public int getSituacao() {
		return situacao;
	}

	@Override
	public String toString() {
		return String.valueOf(this.situacao);
	}

}
