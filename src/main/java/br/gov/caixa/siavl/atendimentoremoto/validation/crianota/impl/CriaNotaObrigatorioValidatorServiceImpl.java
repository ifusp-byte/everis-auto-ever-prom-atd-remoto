package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaObrigatorioValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaObrigatorioValidator;

@Service
@SuppressWarnings({ WARNINGS })
public class CriaNotaObrigatorioValidatorServiceImpl implements CriaNotaObrigatorioValidatorService {

	private final List<CriaNotaObrigatorioValidator> criaNotaObrigatorioValidators;

	public CriaNotaObrigatorioValidatorServiceImpl(List<CriaNotaObrigatorioValidator> criaNotaObrigatorioValidators) {
		this.criaNotaObrigatorioValidators = criaNotaObrigatorioValidators;
	}

	@Override
	public List<ViolacaoDto> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto) {
		return criaNotaObrigatorioValidators.stream().map(validator -> validator.validateObrigatorio(criaNotaInputDto))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
	}

}
