package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoBoletBancarioHabitacional implements Serializable {
	NOME_SACADO(new ClasseDocumentoAtributos("NOME_SACADO", "Nome do Sacado", SiecmConstants.STRING, true, 60,
			StringUtils.EMPTY, true, TipoMascara.TEXTO, null)),
	ENDERECO_SACADO(new ClasseDocumentoAtributos("ENDERECO_SACADO", "Endereço", SiecmConstants.STRING, true, 60,
			StringUtils.EMPTY, true, null, null)),
	CEP(new ClasseDocumentoAtributos("CEP", "CEP", SiecmConstants.STRING, true, 10, StringUtils.EMPTY, true,
			TipoMascara.CEP, null)),
	BAIRRO(new ClasseDocumentoAtributos("BAIRRO", "Bairro", SiecmConstants.STRING, true, 64, StringUtils.EMPTY, true,
			null, null)),
	CIDADE(new ClasseDocumentoAtributos("CIDADE", "Cidade", SiecmConstants.STRING, true, 40, StringUtils.EMPTY, true,
			null, null)),
	UF(new ClasseDocumentoAtributos("UF", "UF", "STRING", true, 20, StringUtils.EMPTY, true, TipoMascara.UF,
			SiecmConstants.ESTADOS)),
	NOME_CEDENTE(new ClasseDocumentoAtributos("NOME_CEDENTE", "Nome do Cedente", SiecmConstants.STRING, true, 60,
			StringUtils.EMPTY, true, TipoMascara.TEXTO, null));

	private ClasseDocumentoAtributos atributos;

	ClasseDocumentoBoletBancarioHabitacional(ClasseDocumentoAtributos atributos) {
		this.atributos = atributos;
	}

	public ClasseDocumentoAtributos getAtributos() {
		return atributos;
	}
}