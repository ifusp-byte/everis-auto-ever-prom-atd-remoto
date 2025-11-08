package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validator;

import java.util.List;
import java.util.Optional;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings("all")
public interface ContrataNotaFiltroValidator {

	Optional<List<ViolacaoDto>> validateFiltro(ContrataNotaInputDTO contrataNotaInputDTO);

}
