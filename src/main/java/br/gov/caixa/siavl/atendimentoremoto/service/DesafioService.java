package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.gateway.identificacaopositiva.dto.CriaDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.identificacaopositiva.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.identificacaopositiva.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.identificacaopositiva.dto.RespondeDesafioOutputDTO;
@SuppressWarnings("all")
public interface DesafioService {

	CriaDesafioOutputDTO desafioCriar(String token, String cpf, CriaDesafioInputDTO criaDesafioInputDTO) throws Exception;

	RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio, RespondeDesafioInputDTO respostaDesafio) throws Exception;

}
