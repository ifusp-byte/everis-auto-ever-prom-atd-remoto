package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@SuppressWarnings({ "squid:S107"})
public enum ClasseDocumentoCarteiraIdentidadeMilitar implements Serializable {
	ORGAO_EMISSOR("ORGAO_EMISSOR", "Orgão Emissor", SiecmConstants.STRING, true, 100, "", true, null, null),
	NUMERO_REGISTRO("NUMERO_REGISTRO", "Número de Registro", SiecmConstants.STRING, true, 100, "", true, null, null),
	UF_EXPEDICAO("UF_EXPEDICAO", "UF de Expedição", SiecmConstants.STRING, true, 2, "", true, TipoMascara.UF, SiecmConstants.ESTADOS),
	DATA_EXPEDICAO("DATA_EXPEDICAO", "Data de Expedição", "DATE", true, 12, "", true, TipoMascara.DATE, null),
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

	ClasseDocumentoCarteiraIdentidadeMilitar(String classeDocumento, String nomeCampo, String tipoCampo,
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
