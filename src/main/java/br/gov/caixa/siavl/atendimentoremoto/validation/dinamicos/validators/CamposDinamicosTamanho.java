package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_DINAMICOS_TAMANHO;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNovas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosTamanhoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator.CamposDinamicosTamanhoValidator;

@Component
@SuppressWarnings("all")
public class CamposDinamicosTamanho implements CamposDinamicosTamanhoValidator {

	@Lazy
	@Autowired
	CamposDinamicosTamanhoValidatorService camposDinamicosTamanhoValidatorService;

	List<ViolacaoDto> violacoes;

	public Optional<List<ViolacaoDto>> validateTamanho(CriaNotaInputDTO criaNotaInputDto) {

		violacoes = new ArrayList<>();
		List<CamposDinamicosModeloNotaValidacaoDTO> idsTamanhosDinamicos = camposDinamicosTamanhoValidatorService
				.obterIdsTamanhosDinamicos(criaNotaInputDto);
		List<CamposDinamicosModeloNotaValidacaoDTO> idsValoresDinamicosInput = camposDinamicosTamanhoValidatorService
				.obterIdsValoresDinamicosInput(criaNotaInputDto);

		if (!idsValoresDinamicosInput.isEmpty()) {
			idsValoresDinamicosInput.stream().forEach(idValorInput -> {
				Optional<CamposDinamicosModeloNotaValidacaoDTO> campoDinamicoOpt = idsTamanhosDinamicos.stream().filter(
						dinamico -> dinamico.getIdCampoDinamico().equalsIgnoreCase(idValorInput.getIdCampoDinamico()))
						.findFirst();
				if (Long.valueOf(idValorInput.getValorInput().length()) == 0L
						|| Long.valueOf(idValorInput.getValorInput().length()) > Long
								.valueOf(campoDinamicoOpt.get().getQuantidadeCaracteres())) {
					violacoes = violacaoNovas(
							getMessage(VALIDATOR_DINAMICOS_TAMANHO, String.valueOf(idValorInput.getIdCampoDinamico()),
									campoDinamicoOpt.get().getQuantidadeCaracteres()),
							String.valueOf(idValorInput.getValorInput()), violacoes);
				}
			});
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		return Optional.empty();
	}

}
