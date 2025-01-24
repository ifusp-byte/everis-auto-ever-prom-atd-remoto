package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoContituicaoPJ {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CNPJ", SiecmConstants.STRING, true, 59,
			"", true, TipoMascara.CNPJ, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoContituicaoPJ(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}