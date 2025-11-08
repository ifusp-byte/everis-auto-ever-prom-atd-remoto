package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_FORMATO_QUANTIDADE_META;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNova;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaFormatoValidator;

@Component
@SuppressWarnings({ WARNINGS })
public class QuantidadeMetaFormatoValidator implements CriaNotaFormatoValidator {

	@Lazy
	@Autowired
	CriaNotaFormatoValidatorService criaNotaFormatoValidatorService;

	@Override
	public Optional<ViolacaoDto> validateFormato(CriaNotaInputDTO criaNotaInputDto) {

		String quantidadeMeta = String.valueOf(criaNotaInputDto.getQuantidadeMeta());

		if (criaNotaInputDto.getQuantidadeMeta() != null && StringUtils.isNotBlank(quantidadeMeta)) {
			if (NumberUtils.isParsable(quantidadeMeta)) {
				if (quantidadeMeta.length() > 2) {
					return Optional.of(violacaoNova(getMessage(VALIDATOR_FORMATO_QUANTIDADE_META), quantidadeMeta));
				}
			} else {
				return Optional.of(violacaoNova(getMessage(VALIDATOR_FORMATO_QUANTIDADE_META), quantidadeMeta));
			}
		}
		return Optional.empty();
	}

}
