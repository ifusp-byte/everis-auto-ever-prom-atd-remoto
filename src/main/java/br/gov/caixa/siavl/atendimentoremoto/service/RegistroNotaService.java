package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;

public interface RegistroNotaService {
	
	Object registraNota (String token, Long numeroNota, RegistraNotaInputDto registraNotaInputDto) throws Exception;
	
	Boolean enviaCliente(Long numeroNota);

}
