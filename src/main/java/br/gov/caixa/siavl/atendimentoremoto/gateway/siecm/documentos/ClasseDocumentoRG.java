package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoRG implements Serializable {

	NUMERO_REGISTRO(new ClasseDocumentoAtributos("NUMERO_REGISTRO", "Número de Registro", "STRING", true, 20, "", true,
			TipoMascara.NUMERICO, null)),
	NATURALIDADE(new ClasseDocumentoAtributos("NATURALIDADE", "Naturalidade", "STRING", true, 64, "", true,
			TipoMascara.TEXTO, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoRG(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
