package br.gov.caixa.siavl.atendimentoremoto.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoExtratoAplicacao implements Serializable {

	AGENCIA("AGENCIA", "Agência", "STRING", true, 4, "", true, null, null),
	CONTA("CONTA", "Conta", "STRING", true, 9, "", true, null, null),
	NOME_APLICACAO("NOME_APLICACAO", "Nome da Aplicação", "STRING", true, 60, "", true, null, null),
	PERIODO("PERIODO", "Período", "STRING", true, 20, "", true, null, null);

	private String classeDocumento;
	private String nomeCampo;
	private String tipoCampo;
	private Boolean campoObrigatorio;
	private Integer tamanhoMaximo;
	private String valor;
	private Boolean habilitado;
	private TipoMascara tipoMascara;
	private String[] selectValues;

	ClasseDocumentoExtratoAplicacao(String classeDocumento, String nomeCampo, String tipoCampo,
			Boolean campoObrigatorio, Integer tamanhoMaximo, String valor, Boolean habilitado, TipoMascara tipoMascara,
			String[] selectValues) {
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
