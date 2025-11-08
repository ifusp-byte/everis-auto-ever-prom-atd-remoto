package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaFormatoValidator;

@Service
@SuppressWarnings({ WARNINGS })
public class CriaNotaFormatoValidatorServiceImpl implements CriaNotaFormatoValidatorService {

	private final List<CriaNotaFormatoValidator> criaNotaFormatoValidators;

	public CriaNotaFormatoValidatorServiceImpl(List<CriaNotaFormatoValidator> criaNotaFormatoValidators) {

		this.criaNotaFormatoValidators = criaNotaFormatoValidators;
	}

	@Override
	public List<ViolacaoDto> validateFormato(CriaNotaInputDTO criaNotaInputDto) {
		return criaNotaFormatoValidators.stream().map(validator -> validator.validateFormato(criaNotaInputDto))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
	}

}
