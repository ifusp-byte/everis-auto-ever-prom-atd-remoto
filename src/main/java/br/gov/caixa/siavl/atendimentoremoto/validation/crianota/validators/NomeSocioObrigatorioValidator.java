package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TIPO_DOCUMENTO_CNPJ;
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
public class NomeSocioObrigatorioValidator implements CriaNotaObrigatorioValidator {

	@Lazy
	@Autowired
	CriaNotaObrigatorioValidatorService criaNotaValidatorService;

	@Override
	public Optional<ViolacaoDto> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto) {

		String nomeSocio = String.valueOf(Optional.ofNullable(criaNotaInputDto.getNomeSocio()).orElse(StringUtils.EMPTY));
		String tipoDocumento = String.valueOf(Optional.ofNullable(criaNotaInputDto.getTipoDocumento()).orElse(StringUtils.EMPTY));

		if (TIPO_DOCUMENTO_CNPJ.equalsIgnoreCase(tipoDocumento) && StringUtils.isBlank(nomeSocio)) {
			return Optional.of(violacaoNova("nomeSocio não informado", nomeSocio));
		}
		return Optional.empty();
	}

}
