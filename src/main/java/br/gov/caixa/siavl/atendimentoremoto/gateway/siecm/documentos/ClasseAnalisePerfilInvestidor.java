package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseAnalisePerfilInvestidor implements Serializable {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", SiecmConstants.CPF,
			SiecmConstants.STRING, true, 14, "", true, TipoMascara.CPF, null)),
	NUMERO_CONTA(new ClasseDocumentoAtributos("NUMERO_CONTA", "Número Conta", SiecmConstants.STRING, true, 24, "", true,
			TipoMascara.NUMERO_CONTA, null));

	private ClasseDocumentoAtributos atributos;

	ClasseAnalisePerfilInvestidor(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
