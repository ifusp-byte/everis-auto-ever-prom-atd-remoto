package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoFGTS implements Serializable {
	DATA_REFERENCIA(new ClasseDocumentoAtributos("DATA_REFERENCIA", "Data de Referência", "DATE", true, 12, "", true,
			TipoMascara.DATE, null)),
	NUMERO_PIS_PASEP(new ClasseDocumentoAtributos("NUMERO_PIS_PASEP", "Número PIS/PASEP", "STRING", true, 60, "", true,
			null, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoFGTS(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
