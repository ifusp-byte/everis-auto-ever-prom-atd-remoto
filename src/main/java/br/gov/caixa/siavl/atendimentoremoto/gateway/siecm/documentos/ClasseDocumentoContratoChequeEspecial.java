package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoContratoChequeEspecial implements Serializable {
	NUMERO_CONTRATO(new ClasseDocumentoAtributos("NUMERO_CONTRATO", "Número da Conta", SiecmConstants.STRING, true, 24,
			"", true, TipoMascara.NUMERO_CONTA, null)),
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CPF cliente", SiecmConstants.STRING,
			true, 14, "", true, TipoMascara.CPF, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoContratoChequeEspecial(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}