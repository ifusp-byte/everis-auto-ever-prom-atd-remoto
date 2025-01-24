package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoNegociacaoCDB implements Serializable {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CPF/CNPJ", "STRING", true, 59, "",
			true, TipoMascara.CPFCNPJ, null)),
	NUMERO_NOTA(new ClasseDocumentoAtributos("NUMERO_NOTA", "Número da Nota", "STRING", true, 14, "", true,
			TipoMascara.NUMERICO, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoNegociacaoCDB(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
