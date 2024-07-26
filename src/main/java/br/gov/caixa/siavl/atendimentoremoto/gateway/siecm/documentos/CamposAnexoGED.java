package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;

@SuppressWarnings("all")
public enum CamposAnexoGED implements Serializable {

	// Cabeçalho do json para a requisição para gravar o documento
	DADOS_REQUISICAO("dadosRequisicao"), TIPO_REQUISICAO("tipoRequisicao"), LOCAL_ARMAZENAMENTO("localArmazenamento"),
	IP_USUARIO_FINAL("ipUsuarioFinal"),

	// Informaçõe de pasta para armazenamento no siecm
	SIECM_PASTA("TRANSACOES"), SIECM_SUB_PASTA("NOTA_NEGOCIACAO"),
	SIECM_CLASSE_DOCUMENTO_NOTA_NEGOCIACAO("NOTA_NEGOCIACAO"),

	// Status Documento
	DOCUMENTO_VALIDO("0"), DOCUMENTO_VENCIDO("1"), DOCUMENTO_INCONFORME("2"), DOCUMENTO_AGUARDANDO_CONFORMIDADE("3"),
	DOCUMENTO_TEMPORARIO_POS_OCR("4"), DOCUMENTO_TEMPORARIO_ANTI_FRAUDE("5");

	private String descricao;

	private CamposAnexoGED(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
