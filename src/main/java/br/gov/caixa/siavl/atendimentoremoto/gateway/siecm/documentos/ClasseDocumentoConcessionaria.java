package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoConcessionaria implements Serializable {
	DATA_REFERENCIA(new ClasseDocumentoAtributos("DATA_REFERENCIA", "Data de Referência", "DATE", true, 12, "", true,
			TipoMascara.DATE, null)),
	ENDERECO(new ClasseDocumentoAtributos("ENDERECO", "Endereço", SiecmConstants.STRING, true, 200, "", true, null,
			null)),
	CEP(new ClasseDocumentoAtributos("CEP", "CEP", SiecmConstants.STRING, true, 10, "", true, TipoMascara.CEP, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoConcessionaria(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}