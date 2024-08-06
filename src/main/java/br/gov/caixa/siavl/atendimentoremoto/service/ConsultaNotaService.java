package br.gov.caixa.siavl.atendimentoremoto.service;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.AtendimentoClienteOutputDto;

@SuppressWarnings("all")
public interface ConsultaNotaService {

	List<AtendimentoClienteOutputDto> consultaNota(String token);

}
