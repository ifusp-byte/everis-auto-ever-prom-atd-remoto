package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FILTRO_CPF_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FILTRO_CPF_CNPJ_VALORES;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FILTRO_NUMERO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FILTRO_ORIGEM_SITUACAO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FILTRO_ORIGEM_SITUACAO_NOTA_VALORES;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FILTRO_VALOR;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FILTRO_VALOR_VALORES;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PONTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.VIRGULA;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.sanitizaDocumento;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.MoedaUtils.sanitizaValor;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNovas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.enums.OrigemCadastroNotaEnum;
import br.gov.caixa.siavl.atendimentoremoto.enums.SituacaoNotaEnum;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.ContrataNotaFiltroValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validator.ContrataNotaFiltroValidator;

@Component
@SuppressWarnings("all")
public class ContrataNotaFiltro implements ContrataNotaFiltroValidator {

	@Lazy
	@Autowired
	ContrataNotaFiltroValidatorService contrataNotaFiltroValidatorService;

	List<ViolacaoDto> violacoes;

	public Optional<List<ViolacaoDto>> validateFiltro(ContrataNotaInputDTO contrataNotaInputDTO) {

		String numeroNota = String
				.valueOf(Optional.ofNullable(contrataNotaInputDTO.getNumeroNota()).orElse(StringUtils.EMPTY));

		String valor = sanitizaValor(
				String.valueOf(Optional.ofNullable(contrataNotaInputDTO.getValor()).orElse(StringUtils.EMPTY)))
				.replace(VIRGULA, PONTO);
		String cpf = sanitizaDocumento(
				String.valueOf(Optional.ofNullable(contrataNotaInputDTO.getCpf()).orElse(StringUtils.EMPTY)));
		String cnpj = sanitizaDocumento(
				String.valueOf(Optional.ofNullable(contrataNotaInputDTO.getCnpj()).orElse(StringUtils.EMPTY)));

		Long numeroNotaN = StringUtils.isNotEmpty(numeroNota) ? Long.parseLong(numeroNota) : null;
		Double valorN = StringUtils.isNotEmpty(valor) ? Double.parseDouble(valor) : null;
		Long cpfN = StringUtils.isNotEmpty(cpf) ? Long.parseLong(cpf) : null;
		Long cnpjN = StringUtils.isNotEmpty(cnpj) ? Long.parseLong(cnpj) : null;

		violacoes = new ArrayList<>();

		List<ContrataNotaInputDTO> dadosNotaBuscar = contrataNotaFiltroValidatorService
				.buscaDadosNota(contrataNotaInputDTO);

		if (dadosNotaBuscar.isEmpty()) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_FILTRO_NUMERO_NOTA),
					String.valueOf(contrataNotaInputDTO.getNumeroNota()), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		ContrataNotaInputDTO dadosNota = dadosNotaBuscar.get(0);

		if (!OrigemCadastroNotaEnum.AUTOMATIZADA.getDescricao()
				.equalsIgnoreCase(String.valueOf(dadosNota.getOrigemNota()))
				|| !SituacaoNotaEnum.PENDENTE_CONTRATACAO.getCodigo()
						.equalsIgnoreCase(String.valueOf(dadosNota.getNumeroSituacaoNota()))) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_FILTRO_ORIGEM_SITUACAO_NOTA),
					getMessage(CONTRATA_NOTA_FILTRO_ORIGEM_SITUACAO_NOTA_VALORES,
							String.valueOf(dadosNota.getOrigemNota()),
							String.valueOf(dadosNota.getDescricaoSituacaoNota())),
					violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if ((StringUtils.isNotEmpty(cpf)
				&& cpfN != Long.parseLong(sanitizaDocumento(String.valueOf(dadosNota.getCpf()))))
				|| (StringUtils.isNotEmpty(cnpj)
						&& cnpjN != Long.parseLong(sanitizaDocumento(String.valueOf(dadosNota.getCnpj()))))) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_FILTRO_CPF_CNPJ),
					getMessage(CONTRATA_NOTA_FILTRO_CPF_CNPJ_VALORES, String.valueOf(dadosNota.getCpf()),
							String.valueOf(dadosNota.getCnpj())),
					violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (valorN != Double.parseDouble(String.valueOf(dadosNota.getValor()))) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_FILTRO_VALOR),
					getMessage(CONTRATA_NOTA_FILTRO_VALOR_VALORES, String.valueOf(dadosNota.getValor())), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		return Optional.empty();
	}

}
