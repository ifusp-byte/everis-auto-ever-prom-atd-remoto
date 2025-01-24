package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoCarteiraFuncional implements Serializable {
	ORGAO_EMISSOR(new ClasseDocumentoAtributos("ORGAO_EMISSOR", "Orgão Emissor", SiecmConstants.STRING, true, 100, "",
			true, null, null)),
	NUMERO_REGISTRO(new ClasseDocumentoAtributos("NUMERO_REGISTRO", "Número de Registro", SiecmConstants.STRING, true,
			100, "", true, TipoMascara.NUMERICO, null)),
	UF_EXPEDICAO(new ClasseDocumentoAtributos("UF_EXPEDICAO", "UF de Expedição", SiecmConstants.STRING, true, 2, "",
			true, TipoMascara.UF, SiecmConstants.ESTADOS)),
	DATA_EXPEDICAO(new ClasseDocumentoAtributos("DATA_EXPEDICAO", "Data de Expedição", "DATE", true, 12, "", true,
			TipoMascara.DATE, null)),
	DATA_VALIDADE(new ClasseDocumentoAtributos("DATA_VALIDADE", "Data de Validade", "DATE", true, 12, "", true,
			TipoMascara.DATE, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoCarteiraFuncional(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}