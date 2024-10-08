package br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.enums;

@SuppressWarnings("all")
public enum SiipcUrlEnum {

	DESAFIO_VALIDAR_URL_BASE_1("/id-positiva/v1/historico-desafio"),
	DESAFIO_CRIAR_URL_BASE_1("/id-positiva/v1/desafios"),
	DESAFIO_RESPONDER_URL_BASE_1("/id-positiva/v1/desafios/"),
	DESAFIO_RESPONDER_URL_BASE_2("/enviar-respostas");

	private final String url;

	SiipcUrlEnum(String url) {

		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
