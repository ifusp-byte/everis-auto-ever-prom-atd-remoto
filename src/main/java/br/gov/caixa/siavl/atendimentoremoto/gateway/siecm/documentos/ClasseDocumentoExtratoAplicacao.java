package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoExtratoAplicacao implements Serializable {
	AGENCIA(new ClasseDocumentoAtributos("AGENCIA", "Agência", SiecmConstants.STRING, true, 4, "", true, null, null)),
	CONTA(new ClasseDocumentoAtributos("CONTA", "Conta", SiecmConstants.STRING, true, 9, "", true, null, null)),
	NOME_APLICACAO(new ClasseDocumentoAtributos("NOME_APLICACAO", "Nome da Aplicação", SiecmConstants.STRING, true, 60,
			"", true, null, null)),
	PERIODO(new ClasseDocumentoAtributos("PERIODO", "Período", SiecmConstants.STRING, true, 20, "", true, null, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoExtratoAplicacao(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
