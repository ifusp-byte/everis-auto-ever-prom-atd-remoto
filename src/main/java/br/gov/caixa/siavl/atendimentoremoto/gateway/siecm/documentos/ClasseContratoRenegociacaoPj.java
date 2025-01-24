package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseContratoRenegociacaoPj implements Serializable {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", SiecmConstants.CPF_CNPJ,
			SiecmConstants.STRING, true, 14, "", true, TipoMascara.CNPJ, null)),
	NUMERO_CONTRATO(new ClasseDocumentoAtributos("NUMERO_CONTRATO", "Número do contrato", SiecmConstants.STRING, true,
			22, "", true, TipoMascara.NUMERO_CONTRATO, null));

	private ClasseDocumentoAtributos atributos;

	ClasseContratoRenegociacaoPj(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}