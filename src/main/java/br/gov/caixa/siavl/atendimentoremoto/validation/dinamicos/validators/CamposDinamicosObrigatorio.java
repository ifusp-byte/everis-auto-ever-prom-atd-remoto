package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_DINAMICOS_OBRIGATORIO;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNovas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosObrigatorioValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator.CamposDinamicosObrigatorioValidator;

@Component
@SuppressWarnings("all")
public class CamposDinamicosObrigatorio implements CamposDinamicosObrigatorioValidator {

	@Lazy
	@Autowired
	CamposDinamicosObrigatorioValidatorService camposDinamicosObrigatorioValidatorService;

	List<ViolacaoDto> violacoes;

	public Optional<List<ViolacaoDto>> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto) {

		violacoes = new ArrayList<>();
		List<Long> idsDinamicosObrigatorios = camposDinamicosObrigatorioValidatorService
				.camposDinamicosIdsObrigatorios(criaNotaInputDto);
		String[] idsDinamicosInput = camposDinamicosObrigatorioValidatorService
				.obterIdsCamposDinamicosInput(criaNotaInputDto);
		List<CamposDinamicosModeloNotaValidacaoDTO> dinamicosInput = camposDinamicosObrigatorioValidatorService
				.valoresDinamicosInput(criaNotaInputDto);

		if (!idsDinamicosObrigatorios.isEmpty()) {
			idsDinamicosObrigatorios.stream().forEach(idObrigatorio -> {
				if (!ArrayUtils.contains(idsDinamicosInput, String.valueOf(idObrigatorio))) {
					violacoes = violacaoNovas(
							getMessage(VALIDATOR_DINAMICOS_OBRIGATORIO, String.valueOf(idObrigatorio)),
							String.valueOf(idObrigatorio), violacoes);
				}

				Optional<CamposDinamicosModeloNotaValidacaoDTO> campoDinamicoInputOpt = dinamicosInput.stream().filter(
						dinamico -> dinamico.getIdCampoDinamico().equalsIgnoreCase(String.valueOf(idObrigatorio)))
						.findFirst();

				if (campoDinamicoInputOpt.isPresent()) {
					if (StringUtils.isEmpty(campoDinamicoInputOpt.get().getValorInput())) {
						violacoes = violacaoNovas(
								getMessage(VALIDATOR_DINAMICOS_OBRIGATORIO, String.valueOf(idObrigatorio)),
								String.valueOf(campoDinamicoInputOpt.get().getValorInput()), violacoes);
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
