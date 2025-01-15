package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoPassaporte implements Serializable {
	NUMERO_REGISTRO(new ClasseDocumentoAtributos("NUMERO_REGISTRO", "Número de Registro", "STRING", true, 10, "", true,
			TipoMascara.NUMERICO, null)),
	PAIS_EMISSOR(new ClasseDocumentoAtributos("PAIS_EMISSOR", "País Emissor", "STRING", true, 30, "", true,
			TipoMascara.TEXTO, null)),
	DATA_EMISSAO(new ClasseDocumentoAtributos("DATA_EMISSAO", "Data Emissão", "DATE", true, 12, "", true,
			TipoMascara.DATE, null)),
	DATA_VALIDADE(new ClasseDocumentoAtributos("DATA_VALIDADE", "Data de Validade", "DATE", true, 12, "", true,
			TipoMascara.DATE, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoPassaporte(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
