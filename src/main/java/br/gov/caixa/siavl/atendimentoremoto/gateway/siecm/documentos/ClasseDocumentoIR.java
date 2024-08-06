package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoIR implements Serializable {

	ANO_CALENDARIO("ANO_CALENDARIO", "Ano-calendário", SiecmConstants.STRING, true, 4, "", true, TipoMascara.NUMERICO, null),
	ANO_EXERCICIO("ANO_EXERCICIO", "Ano de Exercício", SiecmConstants.STRING, true, 4, "", true, TipoMascara.NUMERICO, null),
	EMISSOR("EMISSOR", "Emissor", SiecmConstants.STRING, true, 100, "", true, null, null),
	IDENTIFICADOR_CLIENTE("IDENTIFICADOR_CLIENTE", "CPF/CNPJ", SiecmConstants.STRING, true, 59, "", true, TipoMascara.CPFCNPJ, null),
	NUMERO_RECIBO("NUMERO_RECIBO", "Número do Recibo", SiecmConstants.STRING, true, 12, "", true, null, null),
	TIPO("TIPO", "Tipo", SiecmConstants.STRING, true, 1, "", true, null, SiecmConstants.IMPOSTO_RENDA);

	private String classeDocumento;
	private String nomeCampo;
	private String tipoCampo;
	private Boolean campoObrigatorio;
	private Integer tamanhoMaximo;
	private String valor;
	private Boolean habilitado;
	private TipoMascara tipoMascara;
	private String[] selectValues;

	ClasseDocumentoIR(String classeDocumento, String nomeCampo, String tipoCampo, Boolean campoObrigatorio,
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
