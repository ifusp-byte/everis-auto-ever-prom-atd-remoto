package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.Optional;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings({ WARNINGS })
public interface CriaNotaObrigatorioValidator {

	Optional<ViolacaoDto> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto);

}
