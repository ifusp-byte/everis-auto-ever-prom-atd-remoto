package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoExtratoBancario implements Serializable {
	DATA_REFERENCIA(new ClasseDocumentoAtributos("DATA_REFERENCIA", "Data de Referência", "DATE", true, 12, "", true,
			TipoMascara.DATE, null)),
	EMISSOR(new ClasseDocumentoAtributos("EMISSOR", "Emissor", "STRING", true, 100, "", true, null, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoExtratoBancario(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
