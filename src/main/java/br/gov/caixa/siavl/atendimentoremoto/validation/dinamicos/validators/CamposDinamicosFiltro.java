package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CAMPOS_DINAMICOS;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_DINAMICOS_FILTRO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_DINAMICOS_FILTRO_NUMERO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_DINAMICOS_FILTRO_QUANTIDADE_DINAMICOS;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNovas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosFiltroValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator.CamposDinamicosFiltroValidator;

@Component
@SuppressWarnings("all")
public class CamposDinamicosFiltro implements CamposDinamicosFiltroValidator {

	@Lazy
	@Autowired
	CamposDinamicosFiltroValidatorService camposDinamicosFiltroValidatorService;

	List<ViolacaoDto> violacoes;

	public Optional<List<ViolacaoDto>> validateFiltro(CriaNotaInputDTO criaNotaInputDto) {

		violacoes = new ArrayList<>();
		List<String> idsDinamicosInput = camposDinamicosFiltroValidatorService
				.obterIdsCamposDinamicosInput(criaNotaInputDto);
		Long[] idsDinamicosModelo = camposDinamicosFiltroValidatorService.camposDinamicosIds(criaNotaInputDto);

		if (idsDinamicosInput.size() != idsDinamicosModelo.length) {
			violacoes = violacaoNovas(
					getMessage(VALIDATOR_DINAMICOS_FILTRO_QUANTIDADE_DINAMICOS, idsDinamicosModelo.length),
					String.valueOf(idsDinamicosInput.size()) + " " + getMessage(CAMPOS_DINAMICOS), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (!idsDinamicosInput.isEmpty()) {
			idsDinamicosInput.stream().forEach(idDinamico -> {
				if (!NumberUtils.isParsable(idDinamico)) {
					violacoes = violacaoNovas(getMessage(VALIDATOR_DINAMICOS_FILTRO_NUMERO, idDinamico), idDinamico,
							violacoes);
				}
			});
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (!idsDinamicosInput.isEmpty()) {
			idsDinamicosInput.stream().forEach(idDinamico -> {
				if (!ArrayUtils.contains(idsDinamicosModelo, Long.valueOf(idDinamico))) {
					violacoes = violacaoNovas(getMessage(VALIDATOR_DINAMICOS_FILTRO, idDinamico), idDinamico,
							violacoes);
				}
			});
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		return Optional.empty();
	}

}
