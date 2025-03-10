package br.gov.caixa.siavl.atendimentoremoto.service;

@SuppressWarnings("all")
public interface AnexoDocumentoSimtrService {

	Object documentos(String token, String cpfCnpj) throws Exception;

}
