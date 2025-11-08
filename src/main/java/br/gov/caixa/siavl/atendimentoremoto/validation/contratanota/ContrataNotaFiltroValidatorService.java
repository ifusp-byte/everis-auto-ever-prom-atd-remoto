package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings("all")
public interface ContrataNotaFiltroValidatorService {

	List<ViolacaoDto> validateFiltro(ContrataNotaInputDTO contrataNotaInputDTO);

	List<ContrataNotaInputDTO> buscaDadosNota(ContrataNotaInputDTO contrataNotaInputDTO);

}
