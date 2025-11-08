package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoPedidoOfertaPublica implements Serializable {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CPF/CNPJ", SiecmConstants.STRING, true,
			59, "", true, TipoMascara.CPFCNPJ, null)),
	CODIGO_ISIN(new ClasseDocumentoAtributos("CODIGO_ISIN", "Código ISIN", SiecmConstants.STRING, true, 14, "", true,
			TipoMascara.NUMERICO, null)),
	CODIGO_NEGOCIACAO_B3(new ClasseDocumentoAtributos("CODIGO_NEGOCIACAO_B3", "Código de Negociação B3",
			SiecmConstants.STRING, false, 14, "", true, TipoMascara.NUMERICO, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoPedidoOfertaPublica(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
