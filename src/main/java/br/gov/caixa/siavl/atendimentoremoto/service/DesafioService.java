package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioOutputDTO;
@SuppressWarnings({"squid:S112"})
public interface DesafioService {

	CriaDesafioOutputDTO desafioCriar(String token, String cpf) throws Exception;

	RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio, String respostaDesafio) throws Exception;

}
