package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings({ WARNINGS })
public interface ContrataNotaObrigatorioFormatoValidatorService {

	List<ViolacaoDto> validateObrigatorioFormato(ContrataNotaInputDTO contrataNotaInputDTO);

}
