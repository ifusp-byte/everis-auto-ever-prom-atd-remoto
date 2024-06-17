package br.gov.caixa.siavl.atendimentoremoto.service;

import org.springframework.web.multipart.MultipartFile;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaDocumentoInputDto;

public interface AnexoDocumentoService {

	Object enviaDocumento(String token, String cpfCnpj, MultipartFile arquivoContrato, EnviaDocumentoInputDto enviaDocumentoInputDto) throws Exception;
	
	Object tipoDocumento(String cpfCnpj) throws Exception;
	
	Object tipoDocumentoCampos(String codGED) throws Exception;

}
