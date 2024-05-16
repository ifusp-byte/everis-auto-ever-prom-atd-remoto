package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;

public interface RegistroNotaService {
	
	Object registraNota (String token, RegistraNotaInputDto registraNotaInputDto, Long numeroModeloNota) throws Exception;
	
	Boolean enviaCliente(String token, Long numeroNota, EnviaClienteInputDto enviaClienteInputDto);

}
