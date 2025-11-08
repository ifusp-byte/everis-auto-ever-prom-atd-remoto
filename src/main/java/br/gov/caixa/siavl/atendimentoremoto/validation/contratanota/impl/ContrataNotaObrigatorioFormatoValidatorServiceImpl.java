package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.ContrataNotaObrigatorioFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validator.ContrataNotaObrigatorioFormatoValidator;

@Service
@SuppressWarnings({ WARNINGS })
public class ContrataNotaObrigatorioFormatoValidatorServiceImpl
		implements ContrataNotaObrigatorioFormatoValidatorService {

	private final List<ContrataNotaObrigatorioFormatoValidator> contrataNotaObrigatorioFormatoValidators;

	public ContrataNotaObrigatorioFormatoValidatorServiceImpl(
			List<ContrataNotaObrigatorioFormatoValidator> contrataNotaObrigatorioFormatoValidators) {
		this.contrataNotaObrigatorioFormatoValidators = contrataNotaObrigatorioFormatoValidators;
	}

	@Override
	public List<ViolacaoDto> validateObrigatorioFormato(ContrataNotaInputDTO contrataNotaInputDTO) {

		List<List<ViolacaoDto>> lista = contrataNotaObrigatorioFormatoValidators.stream()
				.map(validator -> validator.validateObrigatorioFormato(contrataNotaInputDTO))
				.filter(Optional::isPresent).map(Optional::get).toList();

		return !lista.isEmpty() ? lista.get(0) : new ArrayList<>();
	}

}
