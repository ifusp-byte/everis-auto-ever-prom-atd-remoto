package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseContratacaoCartaoVaVr implements Serializable {
	NOME_TITULAR(new ClasseDocumentoAtributos("NOME_TITULAR", "Nome do Titular", SiecmConstants.STRING, true, 256, "",
			true, TipoMascara.TEXTO, null)),
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", SiecmConstants.CPF_CNPJ,
			SiecmConstants.STRING, true, 14, "", true, TipoMascara.CNPJ, null));

	private ClasseDocumentoAtributos atributos;

	ClasseContratacaoCartaoVaVr(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
