package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioOutputDTO;

@SuppressWarnings("all")
public interface DesafioService {

	Object desafioValidar(String token, String cpf);

	CriaDesafioOutputDTO desafioCriar(String token, String cpf, CriaDesafioInputDTO criaDesafioInputDTO);

	RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio, RespondeDesafioInputDTO respostaDesafio);

}
