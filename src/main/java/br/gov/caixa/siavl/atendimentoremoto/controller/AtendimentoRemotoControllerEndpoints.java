package br.gov.caixa.siavl.atendimentoremoto.controller;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.UTILITY_CLASS;

public class AtendimentoRemotoControllerEndpoints {

	private AtendimentoRemotoControllerEndpoints() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

	public static final String BASE_URL = "/v1/atendimento-remoto";
	public static final String PROTOCOLO = "/protocolo";
	public static final String DESAFIO_VALIDAR = "/desafio-validar/{cpf}";
	public static final String DESAFIO_CRIAR = "/desafio-criar/{cpf}";
	public static final String DESAFIO_RESPONDER = "/desafio-responder/{idDesafio}";
	public static final String TOKEN_SMS = "/token-sms";
	public static final String AUDITORIA_IDENTIFICACAO_POSITIVA = "/auditoria-identificacao-positiva";
	public static final String MODELO_NOTA = "/modelo-nota/{cpfCnpj}";
	public static final String MODELO_NOTA_FAVORITA = "/modelo-nota-favorita/{cpfCnpj}";
	public static final String MODELO_NOTA_FAVORITA_BY_MODELO_NOTA = "/modelo-nota-favorita/{numeroModeloNota}";
	public static final String MODELO_NOTA_MAIS_UTILIZADA = "/modelo-nota-mais-utilizada/{cpfCnpj}";
	public static final String MODELO_NOTA_DINAMICO = "/modelo-nota-dinamico/{numeroModeloNota}";
	public static final String CONTA_ATENDIMENTO = "/conta-atendimento/{cpfCnpj}";
	public static final String NOTA_SALVAR_NOTA = "/nota/{numeroModeloNota}";
	public static final String NOTA_ENVIAR_CLIENTE = "/nota/{numeroNota}";
	public static final String DOCUMENTO = "/documento/{codGedAnexo}";
	public static final String DOCUMENTO_TIPO = "/documento/tipo/{cpfCnpj}";
	public static final String DOCUMENTO_TIPO_CAMPOS = "/documento/tipo/campos/{codGED}";
	public static final String MARCA_DOI = "/marca-doi/{cpfCnpj}";
	public static final String RELATORIOS = "/relatorios";
	public static final String TIPO_NOTA = "/tipo-nota";

}
