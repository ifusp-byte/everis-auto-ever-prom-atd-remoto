package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseCancelamentoSeguroPrestamista implements Serializable {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", SiecmConstants.CPF,
			SiecmConstants.STRING, true, 14, "", true, TipoMascara.CPF, null)),
	NUMERO_PROPOSTA(new ClasseDocumentoAtributos("NUMERO_PROPOSTA", "Número Proposta", SiecmConstants.STRING, true, 22,
			"", true, TipoMascara.NUMERO_CONTRATO, null));

	private ClasseDocumentoAtributos atributos;

	ClasseCancelamentoSeguroPrestamista(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
