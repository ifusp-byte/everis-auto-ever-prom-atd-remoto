package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoDemonstrativoPagamento implements Serializable {
	DATA_REFERENCIA(new ClasseDocumentoAtributos("DATA_REFERENCIA", "Data de Referência", "DATE", true, 12, "", true,
			TipoMascara.DATE, null)),
	VALOR_RENDA_BRUTA(new ClasseDocumentoAtributos("VALOR_RENDA_BRUTA", "Valor Renda Bruta", "STRING", true, 17, "",
			true, TipoMascara.MONETARIO, null)),
	VALOR_RENDA_LIQUIDA(new ClasseDocumentoAtributos("VALOR_RENDA_LIQUIDA", "Valor Renda Líquida", "STRING", true, 17,
			"", true, TipoMascara.MONETARIO, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoDemonstrativoPagamento(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
