package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaDocumentoInputDto;

@SuppressWarnings("all")
public interface AnexoDocumentoSiecmService {

	Object enviaDocumento(String token, String cpfCnpj, EnviaDocumentoInputDto enviaDocumentoInputDto) throws Exception;

	Object consultaDocumento(String token, String codGedAnexo) throws Exception;

	Object excluiDocumento(String token, Long numeroNota, String codGedAnexo) throws Exception;

	Object tipoDocumento(String cpfCnpj) throws Exception;

	Object tipoDocumentoCampos(String codGED) throws Exception;

	Object documentoNota(Long numeroNota) throws Exception;

}
