package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_DINAMICOS_MASCARA;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.validarValorComMascara;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNovas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosMascaraValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator.CamposDinamicosMascaraValidator;

@Component
@SuppressWarnings("all")
public class CamposDinamicosMascara implements CamposDinamicosMascaraValidator {

	@Lazy
	@Autowired
	CamposDinamicosMascaraValidatorService camposDinamicosMascaraValidatorService;

	List<ViolacaoDto> violacoes;

	public Optional<List<ViolacaoDto>> validateMascara(CriaNotaInputDTO criaNotaInputDto) {

		violacoes = new ArrayList<>();
		List<CamposDinamicosModeloNotaValidacaoDTO> dinamicos = camposDinamicosMascaraValidatorService
				.obterIdsTamanhosDinamicos(criaNotaInputDto);
		List<CamposDinamicosModeloNotaValidacaoDTO> dinamicosInput = camposDinamicosMascaraValidatorService
				.obterIdsValoresDinamicosInput(criaNotaInputDto);

		if (!dinamicosInput.isEmpty()) {
			dinamicosInput.stream().forEach(input -> {
				Optional<CamposDinamicosModeloNotaValidacaoDTO> campoDinamicoOpt = dinamicos.stream()
						.filter(dinamico -> dinamico.getIdCampoDinamico().equalsIgnoreCase(input.getIdCampoDinamico()))
						.findFirst();
				if (campoDinamicoOpt.isPresent()) {
					String mascaraCampo = String.valueOf(
							Optional.ofNullable(campoDinamicoOpt.get().getMascaraCampo()).orElse(StringUtils.EMPTY));
					if (StringUtils.isNotEmpty(mascaraCampo)
							&& validarValorComMascara(String.valueOf(input.getValorInput()), mascaraCampo)) {
						violacoes = violacaoNovas(getMessage(VALIDATOR_DINAMICOS_MASCARA,
								String.valueOf(input.getIdCampoDinamico()), mascaraCampo),
								String.valueOf(input.getValorInput()), violacoes);
					}
				}
			});
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		return Optional.empty();
	}

}
