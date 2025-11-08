package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.NotaNegociacaoRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.ContrataNotaFiltroValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validator.ContrataNotaFiltroValidator;

@Service
@SuppressWarnings("all")
public class ContrataNotaFiltroValidatorServiceImpl implements ContrataNotaFiltroValidatorService {

	@Autowired
	NotaNegociacaoRepositoryImpl notaNegociacaoRepositoryImpl;

	private final List<ContrataNotaFiltroValidator> contrataNotaFiltroValidators;

	public ContrataNotaFiltroValidatorServiceImpl(List<ContrataNotaFiltroValidator> contrataNotaFiltroValidators) {
		this.contrataNotaFiltroValidators = contrataNotaFiltroValidators;
	}

	@Override
	public List<ViolacaoDto> validateFiltro(ContrataNotaInputDTO contrataNotaInputDTO) {

		List<List<ViolacaoDto>> lista = contrataNotaFiltroValidators.stream()
				.map(validator -> validator.validateFiltro(contrataNotaInputDTO)).filter(Optional::isPresent)
				.map(Optional::get).toList();

		return !lista.isEmpty() ? lista.get(0) : new ArrayList<>();
	}

	@Override
	public List<ContrataNotaInputDTO> buscaDadosNota(ContrataNotaInputDTO contrataNotaInputDTO) {
		Long numeroNota = Long.valueOf(String.valueOf(contrataNotaInputDTO.getNumeroNota()));
		return notaNegociacaoRepositoryImpl.dadosNota(numeroNota);

	}

}
