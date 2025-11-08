package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_FORMATO_TIPO_DOCUMENTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TIPO_DOCUMENTO_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TIPO_DOCUMENTO_CPF;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNova;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaFormatoValidator;

@Component
@SuppressWarnings({ WARNINGS })
public class TipoDocumentoFormatoValidator implements CriaNotaFormatoValidator {

	@Lazy
	@Autowired
	CriaNotaFormatoValidatorService criaNotaFormatoValidatorService;

	@Override
	public Optional<ViolacaoDto> validateFormato(CriaNotaInputDTO criaNotaInputDto) {

		String tipoDocumento = String.valueOf(criaNotaInputDto.getTipoDocumento());

		if (!(TIPO_DOCUMENTO_CNPJ.equalsIgnoreCase(tipoDocumento)
				|| TIPO_DOCUMENTO_CPF.equalsIgnoreCase(tipoDocumento))) {
			return Optional.of(violacaoNova(getMessage(VALIDATOR_FORMATO_TIPO_DOCUMENTO), tipoDocumento));
		}
		return Optional.empty();
	}

}
