package br.gov.caixa.siavl.atendimentoremoto.service;

@SuppressWarnings("all")
public interface AnexoDocumentoSimtrService {

	Object documentos(String cpfCnpj) throws Exception;

	Object documentoConsulta(String idDocumento) throws Exception;

}
