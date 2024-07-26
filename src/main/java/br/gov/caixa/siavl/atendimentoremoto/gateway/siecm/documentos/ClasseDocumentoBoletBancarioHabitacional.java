package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoBoletBancarioHabitacional implements Serializable {
	NOME_SACADO("NOME_SACADO", "Nome do Sacado", SiecmConstants.STRING, true, 60, StringUtils.EMPTY, true, TipoMascara.TEXTO, null),
	ENDERECO_SACADO("ENDERECO_SACADO", "Endereço", SiecmConstants.STRING, true, 60, StringUtils.EMPTY, true, null, null),
	CEP("CEP", "CEP", SiecmConstants.STRING, true, 10, StringUtils.EMPTY, true, TipoMascara.CEP, null),
	BAIRRO("BAIRRO", "Bairro", SiecmConstants.STRING, true, 64, StringUtils.EMPTY, true, null, null),
	CIDADE("CIDADE", "Cidade", SiecmConstants.STRING, true, 40, StringUtils.EMPTY, true, null, null),
	UF("UF", "UF", "STRING", true, 20, StringUtils.EMPTY, true, TipoMascara.UF, SiecmConstants.ESTADOS),
	NOME_CEDENTE("NOME_CEDENTE", "Nome do Cedente", SiecmConstants.STRING, true, 60, StringUtils.EMPTY, true, TipoMascara.TEXTO, null);

	private String classeDocumento;
	private String nomeCampo;
	private String tipoCampo;
	private Boolean campoObrigatorio;
	private Integer tamanhoMaximo;
	private String valor;
	private Boolean habilitado;
	private TipoMascara tipoMascara;
	private String[] selectValues;

	ClasseDocumentoBoletBancarioHabitacional(String classeDocumento, String nomeCampo, String tipoCampo,
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
