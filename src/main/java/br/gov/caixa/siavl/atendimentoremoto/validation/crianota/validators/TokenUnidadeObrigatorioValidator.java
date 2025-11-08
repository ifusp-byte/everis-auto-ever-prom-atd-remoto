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
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaObrigatorioValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaObrigatorioValidator;

@Component
@SuppressWarnings({ WARNINGS })
public class TokenUnidadeObrigatorioValidator implements CriaNotaObrigatorioValidator {

	@Autowired
	TokenUtils tokenUtils;

	@Lazy
	@Autowired
	CriaNotaObrigatorioValidatorService criaNotaValidatorService;

	@Override
	public Optional<ViolacaoDto> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto) {

		String unidade = Optional.ofNullable(criaNotaInputDto.getToken()).orElse(StringUtils.EMPTY);
		String unidadeValida = tokenUtils.getUnidadeFromToken(unidade);

		if (StringUtils.isBlank(unidadeValida)) {
			return Optional.of(violacaoNova("Token co-unidade inválida", unidadeValida));
		}
		return Optional.empty();
	}

}
