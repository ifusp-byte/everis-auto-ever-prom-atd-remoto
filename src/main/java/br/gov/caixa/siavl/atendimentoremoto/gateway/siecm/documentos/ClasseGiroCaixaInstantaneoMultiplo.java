package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseGiroCaixaInstantaneoMultiplo implements Serializable {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CPF/CNPJ", "STRING", true, 14, "",
			true, TipoMascara.CNPJ, null)),
	NUMERO_CONTA(new ClasseDocumentoAtributos("NUMERO_CONTA", "Número Conta", "STRING", true, 24, "", true,
			TipoMascara.NUMERO_CONTA, null));

	private ClasseDocumentoAtributos atributos;

	ClasseGiroCaixaInstantaneoMultiplo(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
