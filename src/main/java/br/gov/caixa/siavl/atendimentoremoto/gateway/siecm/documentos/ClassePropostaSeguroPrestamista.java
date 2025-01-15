package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClassePropostaSeguroPrestamista implements Serializable {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CPF", "STRING", true, 14, "", true,
			TipoMascara.CPF, null)),
	NUMERO_PROPOSTA(new ClasseDocumentoAtributos("NUMERO_PROPOSTA", "Número Proposta", "STRING", true, 22, "", true,
			TipoMascara.NUMERO_CONTRATO, null));

	private ClasseDocumentoAtributos atributos;

	ClassePropostaSeguroPrestamista(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
