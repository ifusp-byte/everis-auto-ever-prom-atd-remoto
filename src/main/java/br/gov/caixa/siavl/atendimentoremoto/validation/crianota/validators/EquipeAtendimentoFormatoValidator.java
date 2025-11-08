package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_FORMATO_EQUIPE;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNova;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaFormatoValidator;

@Component
@SuppressWarnings({ WARNINGS })
public class EquipeAtendimentoFormatoValidator implements CriaNotaFormatoValidator {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	EquipeAtendimentoRepository equipeAtendimentoRepository;

	@Lazy
	@Autowired
	CriaNotaFormatoValidatorService criaNotaFormatoValidatorService;

	@Override
	public Optional<ViolacaoDto> validateFormato(CriaNotaInputDTO criaNotaInputDto) {

		Long numeroEquipe = equipeAtendimentoRepository
				.findEquipeByUnidadeSR(Long.parseLong(tokenUtils.getUnidadeFromToken(criaNotaInputDto.getToken())));

		if (numeroEquipe == null) {
			return Optional.of(violacaoNova(getMessage(VALIDATOR_FORMATO_EQUIPE),
					String.valueOf(numeroEquipe)));
		}
		return Optional.empty();
	}

}
