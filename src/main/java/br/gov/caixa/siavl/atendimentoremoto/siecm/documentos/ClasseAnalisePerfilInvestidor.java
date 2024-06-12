package br.gov.caixa.siavl.atendimentoremoto.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseAnalisePerfilInvestidor implements Serializable {
	IDENTIFICADOR_CLIENTE("IDENTIFICADOR_CLIENTE", "CPF", "STRING", true, 14, "", true, TipoMascara.CPF, null),
	NUMERO_CONTA("NUMERO_CONTA", "Número Conta", "STRING", true, 24, "", true, TipoMascara.NUMERO_CONTA, null);

	private String classeDocumento;
	private String nomeCampo;
	private String tipoCampo;
	private Boolean campoObrigatorio;
	private Integer tamanhoMaximo;
	private String valor;
	private Boolean habilitado;
	private TipoMascara tipoMascara;
	private String[] selectValues;

	ClasseAnalisePerfilInvestidor(String classeDocumento, String nomeCampo, String tipoCampo, Boolean campoObrigatorio,
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
