package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validator;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.List;
import java.util.Optional;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings({ WARNINGS })
public interface ContrataNotaObrigatorioFormatoValidator {

	Optional<List<ViolacaoDto>> validateObrigatorioFormato(ContrataNotaInputDTO contrataNotaInputDTO);

}
