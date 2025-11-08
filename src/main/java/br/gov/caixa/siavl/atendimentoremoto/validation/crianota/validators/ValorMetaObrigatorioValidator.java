package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNova;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaObrigatorioValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaObrigatorioValidator;

@Component
@SuppressWarnings({ WARNINGS })
public class ValorMetaObrigatorioValidator implements CriaNotaObrigatorioValidator {

	@Lazy
	@Autowired
	CriaNotaObrigatorioValidatorService criaNotaValidatorService;

	@Override
	public Optional<ViolacaoDto> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto) {

		String valorMeta = String
				.valueOf(Optional.ofNullable(criaNotaInputDto.getValorMeta()).orElse(StringUtils.EMPTY));

		if (StringUtils.isBlank(valorMeta)) {
			return Optional.of(violacaoNova("valorMeta não informado", valorMeta));
		}
		return Optional.empty();
	}

}
