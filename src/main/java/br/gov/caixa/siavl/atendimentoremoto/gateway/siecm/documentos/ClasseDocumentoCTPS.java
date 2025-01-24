package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoCTPS implements Serializable {
	EMISSOR(new ClasseDocumentoAtributos("EMISSOR", "Emissor", "STRING", true, 100, "Ministério do Trabalho", true,
			null, null)),
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CPF/CNPJ", "STRING", true, 59, "",
			true, TipoMascara.CPFCNPJ, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoCTPS(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
