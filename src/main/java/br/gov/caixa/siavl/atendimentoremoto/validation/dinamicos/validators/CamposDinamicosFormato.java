package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_DINAMICOS_FORMATO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REGEX_APENAS_NUMEROS;
import static br.gov.caixa.siavl.atendimentoremoto.util.DataUtils.isValidDate1;
import static br.gov.caixa.siavl.atendimentoremoto.util.DataUtils.isValidDate2;
import static br.gov.caixa.siavl.atendimentoremoto.util.DataUtils.isValidDate3;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoAgrupada;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNovas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.enums.TipoCampoDinamicoEnum;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator.CamposDinamicosTamanhoValidator;

@Component
@SuppressWarnings("all")
public class CamposDinamicosFormato implements CamposDinamicosTamanhoValidator {

	@Lazy
	@Autowired
	CamposDinamicosFormatoValidatorService camposDinamicosFormatoValidatorService;

	List<ViolacaoDto> violacoes;

	public Optional<List<ViolacaoDto>> validateTamanho(CriaNotaInputDTO criaNotaInputDto) {

		violacoes = new ArrayList<>();
		List<CamposDinamicosModeloNotaValidacaoDTO> idsTamanhosDinamicos = camposDinamicosFormatoValidatorService
				.obterIdsTamanhosDinamicos(criaNotaInputDto);
		List<CamposDinamicosModeloNotaValidacaoDTO> idsValoresDinamicosInput = camposDinamicosFormatoValidatorService
				.obterIdsValoresDinamicosInput(criaNotaInputDto);

		if (!idsValoresDinamicosInput.isEmpty()) {
			idsValoresDinamicosInput.stream().forEach(idValorInput -> {
				Optional<CamposDinamicosModeloNotaValidacaoDTO> campoDinamicoOpt = idsTamanhosDinamicos.stream().filter(
						dinamico -> dinamico.getIdCampoDinamico().equalsIgnoreCase(idValorInput.getIdCampoDinamico()))
						.findFirst();
				if (TipoCampoDinamicoEnum.NUMERICO.getSigla().equals(campoDinamicoOpt.get().getFlagTipoDado())
						&& !NumberUtils.isParsable(String.valueOf(idValorInput.getValorInput()))) {
					violacoes = violacaoNovas(
							getMessage(VALIDATOR_DINAMICOS_FORMATO, String.valueOf(idValorInput.getIdCampoDinamico()),
									TipoCampoDinamicoEnum.NUMERICO.getDescricao()),
							String.valueOf(idValorInput.getValorInput()), violacoes);
				}

				if (TipoCampoDinamicoEnum.MOEDA.getSigla().equals(campoDinamicoOpt.get().getFlagTipoDado())
						&& !NumberUtils.isParsable(String.valueOf(idValorInput.getValorInput()))) {
					violacoes = violacaoNovas(
							getMessage(VALIDATOR_DINAMICOS_FORMATO, String.valueOf(idValorInput.getIdCampoDinamico()),
									TipoCampoDinamicoEnum.MOEDA.getDescricao()),
							String.valueOf(idValorInput.getValorInput()), violacoes);
				}

				Pattern patternData1 = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d", Pattern.CASE_INSENSITIVE);
				Pattern patternData2 = Pattern.compile("\\d\\d\\d\\d/\\d\\d/\\d\\d", Pattern.CASE_INSENSITIVE);

				if (TipoCampoDinamicoEnum.DATA.getSigla().equals(campoDinamicoOpt.get().getFlagTipoDado())) {

					if ((patternData1.matcher(String.valueOf(idValorInput.getValorInput())).matches()
							&& !isValidDate1(String.valueOf(idValorInput.getValorInput())))
							|| (patternData2.matcher(String.valueOf(idValorInput.getValorInput())).matches()
									&& !isValidDate2(String.valueOf(idValorInput.getValorInput())))) {
						violacoes = violacaoNovas(
								getMessage(VALIDATOR_DINAMICOS_FORMATO,
										String.valueOf(idValorInput.getIdCampoDinamico()),
										TipoCampoDinamicoEnum.DATA.getDescricao()),
								String.valueOf(idValorInput.getValorInput()), violacoes);
					}

					String data = String.valueOf(idValorInput.getValorInput()).replaceAll(REGEX_APENAS_NUMEROS,
							StringUtils.EMPTY);

					if (!isValidDate3(data)) {
						violacoes = violacaoNovas(
								getMessage(VALIDATOR_DINAMICOS_FORMATO,
										String.valueOf(idValorInput.getIdCampoDinamico()),
										TipoCampoDinamicoEnum.DATA.getDescricao()),
								String.valueOf(idValorInput.getValorInput()), violacoes);

					}

					violacoes = violacaoAgrupada(violacoes);

				}

			});
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		return Optional.empty();
	}

}
