package br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_FORMATO_PRODUTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNova;

import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validator.CriaNotaFormatoValidator;

@Component
@SuppressWarnings({ WARNINGS })
public class IdProdutoFormatoValidator implements CriaNotaFormatoValidator {

	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	@Lazy
	@Autowired
	CriaNotaFormatoValidatorService criaNotaFormatoValidatorService;

	@Override
	public Optional<ViolacaoDto> validateFormato(CriaNotaInputDTO criaNotaInputDto) {

		String idProduto = String.valueOf(criaNotaInputDto.getIdProduto());
		String idModeloNota = String.valueOf(criaNotaInputDto.getIdModeloNota());

		if (NumberUtils.isParsable(idProduto) && NumberUtils.isParsable(idModeloNota)) {

			if (!modeloNotaRepository.modeloProdutoValido(Long.parseLong(idProduto), Long.parseLong(idModeloNota))
					.isPresent()) {
				return Optional.of(
						violacaoNova(getMessage(VALIDATOR_FORMATO_PRODUTO), idProduto));
			}

		} else {
			return Optional
					.of(violacaoNova(getMessage(VALIDATOR_FORMATO_PRODUTO), idProduto));
		}
		return Optional.empty();
	}
}
