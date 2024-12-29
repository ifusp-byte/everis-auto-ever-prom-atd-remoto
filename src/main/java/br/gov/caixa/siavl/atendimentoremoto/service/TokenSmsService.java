package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.dto.TokenSmsInputDto;

@SuppressWarnings("all")
public interface TokenSmsService {
	
	Object identificacaoTokenSms(String token, TokenSmsInputDto tokenSmsInputDto);

}
