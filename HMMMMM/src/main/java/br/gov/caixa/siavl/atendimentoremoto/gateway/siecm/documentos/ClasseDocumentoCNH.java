package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoCNH implements Serializable {
	DATA_VALIDADE(new ClasseDocumentoAtributos("DATA_VALIDADE", "Data de Validade", "DATE", true, 12, "", true,
			TipoMascara.DATE, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoCNH(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}