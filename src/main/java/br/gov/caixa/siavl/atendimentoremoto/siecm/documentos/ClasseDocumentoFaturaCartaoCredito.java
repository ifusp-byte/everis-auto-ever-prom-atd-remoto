package br.gov.caixa.siavl.atendimentoremoto.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.siecm.constants.SiecmConstants;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoFaturaCartaoCredito implements Serializable {
	NOME_TITULAR("NOME_TITULAR", "Nome do Titular", SiecmConstants.STRING, true, 60, "", true, TipoMascara.TEXTO, null),
	ENDERECO_TITULAR("ENDERECO_TITULAR", "Endereço", SiecmConstants.STRING, true, 60, "", true, null, null),
	CEP("CEP", "CEP", SiecmConstants.STRING, true, 10, "", true, TipoMascara.CEP, null),
	BAIRRO("BAIRRO", "Bairro", SiecmConstants.STRING, true, 64, "", true, null, null),
	CIDADE("CIDADE", "Cidade", SiecmConstants.STRING, true, 40, "", true, null, null),
	UF("UF", "UF", SiecmConstants.STRING, true, 20, "", true, TipoMascara.UF, SelectValues.ESTADOS),
	DATA_FATURA("DATA_FATURA", "Data da Fatura", "DATE", true, 12, "", true, TipoMascara.DATE, null);

	private String classeDocumento;
	private String nomeCampo;
	private String tipoCampo;
	private Boolean campoObrigatorio;
	private Integer tamanhoMaximo;
	private String valor;
	private Boolean habilitado;
	private TipoMascara tipoMascara;
	private String[] selectValues;

	ClasseDocumentoFaturaCartaoCredito(String classeDocumento, String nomeCampo, String tipoCampo,
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
