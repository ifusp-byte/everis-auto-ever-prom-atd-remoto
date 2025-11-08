package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoFaturaCartaoCredito implements Serializable {
	NOME_TITULAR(new ClasseDocumentoAtributos("NOME_TITULAR", "Nome do Titular", SiecmConstants.STRING, true, 60, "",
			true, TipoMascara.TEXTO, null)),
	ENDERECO_TITULAR(new ClasseDocumentoAtributos("ENDERECO_TITULAR", "Endereço", SiecmConstants.STRING, true, 60, "",
			true, null, null)),
	CEP(new ClasseDocumentoAtributos("CEP", "CEP", SiecmConstants.STRING, true, 10, "", true, TipoMascara.CEP, null)),
	BAIRRO(new ClasseDocumentoAtributos("BAIRRO", "Bairro", SiecmConstants.STRING, true, 64, "", true, null, null)),
	CIDADE(new ClasseDocumentoAtributos("CIDADE", "Cidade", SiecmConstants.STRING, true, 40, "", true, null, null)),
	UF(new ClasseDocumentoAtributos("UF", "UF", SiecmConstants.STRING, true, 20, "", true, TipoMascara.UF,
			SiecmConstants.ESTADOS)),
	DATA_FATURA(new ClasseDocumentoAtributos("DATA_FATURA", "Data da Fatura", "DATE", true, 12, "", true,
			TipoMascara.DATE, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoFaturaCartaoCredito(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
