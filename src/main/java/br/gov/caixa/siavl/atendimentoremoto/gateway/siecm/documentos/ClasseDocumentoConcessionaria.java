package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;

@SuppressWarnings("all")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumentoConcessionaria implements Serializable {

	DATA_REFERENCIA(new ClasseDocumentoAtributos("DATA_REFERENCIA", "Data de Referência", "DATE", true, 12, "", true, TipoMascara.DATE, null)),
	ENDERECO(new ClasseDocumentoAtributos("ENDERECO", "Endereço", SiecmConstants.STRING, true, 200, "", true, null, null)),
	CEP(new ClasseDocumentoAtributos("CEP", "CEP", SiecmConstants.STRING, true, 10, "", true, TipoMascara.CEP, null));

	ClasseDocumentoConcessionaria(ClasseDocumentoAtributos classeDocumentoAtributos) {
		// TODO Auto-generated constructor stub
	}



	/*
	private String classeDocumento;
	private String nomeCampo;
	private String tipoCampo;
	private Boolean campoObrigatorio;
	private Integer tamanhoMaximo;
	private String valor;
	private Boolean habilitado;
	private TipoMascara tipoMascara;
	private String[] selectValues;

	private ClasseDocumentoConcessionaria(String classeDocumento, String nomeCampo, String tipoCampo,
			boolean campoObrigatorio, Integer tamanhoMaximo, String valor, TipoMascara tipoMascara, Boolean habilitado,
			String[] selectValues) {
		this.classeDocumento = classeDocumento;
		this.nomeCampo = nomeCampo;
		this.tipoCampo = tipoCampo;
		this.campoObrigatorio = campoObrigatorio;
		this.tamanhoMaximo = tamanhoMaximo;
		this.valor = valor;
		this.tipoMascara = tipoMascara;
		this.habilitado = habilitado;
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
	*/
}
