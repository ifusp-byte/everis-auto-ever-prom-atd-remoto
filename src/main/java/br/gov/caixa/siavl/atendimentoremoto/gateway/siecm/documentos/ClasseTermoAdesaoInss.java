package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseTermoAdesaoInss implements Serializable {
	IDENTIFICADOR_CLIENTE(new ClasseDocumentoAtributos("IDENTIFICADOR_CLIENTE", "CPF", "STRING", true, 14, "", true,
			TipoMascara.CPF, null)),
	NUMERO_BENEFICIO(new ClasseDocumentoAtributos("NUMERO_BENEFICIO", "Número Beneficio", "STRING", true, 10, "", true,
			TipoMascara.N, null));

	private ClasseDocumentoAtributos atributos;

	ClasseTermoAdesaoInss(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}
