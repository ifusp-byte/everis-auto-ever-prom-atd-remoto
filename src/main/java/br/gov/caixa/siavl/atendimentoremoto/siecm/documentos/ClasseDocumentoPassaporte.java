package br.gov.caixa.siavl.atendimentoremoto.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoPassaporte implements Serializable {
	NUMERO_REGISTRO("NUMERO_REGISTRO", "Número de Registro", "STRING", true, 10, "", true, TipoMascara.NUMERICO, null),
	PAIS_EMISSOR("PAIS_EMISSOR", "País Emissor", "STRING", true, 30, "", true, TipoMascara.TEXTO, null),
	DATA_EMISSAO("DATA_EMISSAO", "Data Emissão", "DATE", true, 12, "", true, TipoMascara.DATE, null),
	DATA_VALIDADE("DATA_VALIDADE", "Data de Validade", "DATE", true, 12, "", true, TipoMascara.DATE, null);

	private String classeDocumento;
	private String nomeCampo;
	private String tipoCampo;
	private Boolean campoObrigatorio;
	private Integer tamanhoMaximo;
	private String valor;
	private Boolean habilitado;
	private TipoMascara tipoMascara;
	private String[] selectValues;

	ClasseDocumentoPassaporte(String classeDocumento, String nomeCampo, String tipoCampo, Boolean campoObrigatorio,
			Integer tamanhoMaximo, String valor, Boolean habilitado, TipoMascara tipoMascara, String[] selectValues) {
		this.classeDocumento = classeDocumento;
		this.nomeCampo = nomeCampo;
		this.tipoCampo = tipoCampo;
		this.campoObrigatorio = campoObrigatorio;
		this.tamanhoMaximo = tamanhoMaximo;
		this.valor = valor;
		this.habilitado = habilitado;
		this.tipoMascara = tipoMascara;
		this.selectValues = selectValues;
	}

	public String getClasseDocumento() {
		return classeDocumento;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public String getTipoCampo() {
		return tipoCampo;
	}

	public Boolean getCampoObrigatorio() {
		return campoObrigatorio;
	}

	public Integer getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	public String getValor() {
		return valor;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public TipoMascara getTipoMascara() {
		return tipoMascara;
	}

	public String[] getSelectValues() {
		return selectValues;
	}
}
