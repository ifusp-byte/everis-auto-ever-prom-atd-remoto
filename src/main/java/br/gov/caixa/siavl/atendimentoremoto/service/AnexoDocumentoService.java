package br.gov.caixa.siavl.atendimentoremoto.service;

public interface AnexoDocumentoService {

	Object enviaDocumento(String token, String cpfCnpj) throws Exception;
	
	Object tipoDocumento(String cpfCnpj) throws Exception;
	
	Object tipoDocumentoCampos(String codGED) throws Exception;

}
