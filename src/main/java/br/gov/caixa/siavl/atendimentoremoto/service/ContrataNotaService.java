package br.gov.caixa.siavl.atendimentoremoto.service;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;

@SuppressWarnings("all")
public interface ContrataNotaService {

	Object contrataNota(ContrataNotaInputDTO contrataNotaInputDto, String token) throws Exception;

}
