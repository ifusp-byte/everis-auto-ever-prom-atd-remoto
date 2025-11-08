package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_FORMATO_CPF_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.sanitizaDocumento;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNova;

import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaFormatoValidator;

@Component
@SuppressWarnings({ WARNINGS })
public class CpfCnpjFormatoValidator implements CriaNotaFormatoValidator {

	@Lazy
	@Autowired
	CriaNotaFormatoValidatorService criaNotaFormatoValidatorService;

	@Override
	public Optional<ViolacaoDto> validateFormato(CriaNotaInputDTO criaNotaInputDto) {

		String cpfCnpj = sanitizaDocumento(String.valueOf(criaNotaInputDto.getCpfCnpj()));

		if (!NumberUtils.isParsable(cpfCnpj)) {
			return Optional
					.of(violacaoNova(getMessage(VALIDATOR_FORMATO_CPF_CNPJ), cpfCnpj));
		}
		return Optional.empty();
	}

}
