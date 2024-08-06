package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaDocumentoInputDto;

@SuppressWarnings("all")
public interface AnexoDocumentoService {

	Object enviaDocumento(String token, String cpfCnpj, EnviaDocumentoInputDto enviaDocumentoInputDto) throws Exception;
	
	Object tipoDocumento(String cpfCnpj) throws Exception;
	
	Object tipoDocumentoCampos(String codGED) throws Exception;

}
