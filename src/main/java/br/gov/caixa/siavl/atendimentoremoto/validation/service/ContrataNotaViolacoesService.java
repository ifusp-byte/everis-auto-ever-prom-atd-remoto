package br.gov.caixa.siavl.atendimentoremoto.validation.service;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings("all")
public interface ContrataNotaViolacoesService {

	Object violacoesValidar(ContrataNotaInputDTO contrataNotaInputDTO);

	List<ViolacaoDto> violacoesObrigatorio(ContrataNotaInputDTO contrataNotaInputDTO);

	List<ViolacaoDto> violacoesFiltro(ContrataNotaInputDTO contrataNotaInputDTO);

}
