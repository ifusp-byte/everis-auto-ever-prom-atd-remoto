package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoFaturamentoDossiePJ {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CNPJ", "STRING", true, 59, "", true,
			TipoMascara.CNPJ, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoFaturamentoDossiePJ(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
