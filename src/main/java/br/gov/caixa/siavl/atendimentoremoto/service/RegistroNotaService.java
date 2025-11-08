package br.gov.caixa.siavl.atendimentoremoto.service;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;

@SuppressWarnings("all")
public interface RegistroNotaService {

	CriaNotaInputDTO registraNota(CriaNotaInputDTO criaNotaInputDto);

	CriaNotaInputDTO enviaCliente(CriaNotaInputDTO criaNotaInputDto);

}
