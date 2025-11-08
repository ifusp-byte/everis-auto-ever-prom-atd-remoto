package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validators;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FORMATO_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FORMATO_CPF;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FORMATO_NUMERO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FORMATO_VALOR;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_OBRIGATORIO_CPF_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_OBRIGATORIO_NUMERO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_OBRIGATORIO_VALOR;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_TIPO_DOCUMENTO_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_TIPO_DOCUMENTO_CPF;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_TIPO_DOCUMENTO_FORMATO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_TIPO_DOCUMENTO_OBRIGATORIO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.VALIDATOR_FORMATO_EQUIPE;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PONTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TIPO_DOCUMENTO_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TIPO_DOCUMENTO_CPF;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.VIRGULA;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.isCnpj;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.isCpf;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.sanitizaDocumento;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.MoedaUtils.sanitizaValor;
import static br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils.matriculaFromToken;
import static br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils.unidadeFromToken;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoNovas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.enums.TipoDocumentoEnum;
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.ContrataNotaObrigatorioFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validator.ContrataNotaObrigatorioFormatoValidator;

@Component
@SuppressWarnings("all")
public class ContrataNotaObrigatorioFormato implements ContrataNotaObrigatorioFormatoValidator {

	@Autowired
	EquipeAtendimentoRepository equipeAtendimentoRepository;

	@Lazy
	@Autowired
	ContrataNotaObrigatorioFormatoValidatorService contrataNotaObrigatorioValidatorService;

	List<ViolacaoDto> violacoes;

	public Optional<List<ViolacaoDto>> validateObrigatorioFormato(ContrataNotaInputDTO contrataNotaInputDTO) {

		violacoes = new ArrayList<>();

		String numeroNota = String
				.valueOf(Optional.ofNullable(contrataNotaInputDTO.getNumeroNota()).orElse(StringUtils.EMPTY));
		String valor = String.valueOf(Optional.ofNullable(contrataNotaInputDTO.getValor()).orElse(StringUtils.EMPTY));
		String cpf = String.valueOf(Optional.ofNullable(contrataNotaInputDTO.getCpf()).orElse(StringUtils.EMPTY));
		String cnpj = String.valueOf(Optional.ofNullable(contrataNotaInputDTO.getCnpj()).orElse(StringUtils.EMPTY));
		String tipoDocumento = String
				.valueOf(Optional.ofNullable(contrataNotaInputDTO.getTipoDocumento()).orElse(StringUtils.EMPTY));

		String matricula = Optional.ofNullable(contrataNotaInputDTO.getToken()).orElse(StringUtils.EMPTY);
		String matriculaValida = matriculaFromToken(matricula);

		String unidade = Optional.ofNullable(contrataNotaInputDTO.getToken()).orElse(StringUtils.EMPTY);
		String unidadeValida = unidadeFromToken(unidade);

		if (StringUtils.isBlank(matriculaValida)) {
			violacoes = violacaoNovas("Token preferred_username inválida", String.valueOf(matriculaValida), violacoes);
		}

		if (StringUtils.isBlank(unidadeValida)) {
			violacoes = violacaoNovas("Token co-unidade inválida", String.valueOf(unidadeValida), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		Long numeroEquipe = equipeAtendimentoRepository
				.findEquipeByUnidadeSR(Long.parseLong(unidadeFromToken(contrataNotaInputDTO.getToken())));

		if (numeroEquipe == null) {
			violacoes = violacaoNovas(getMessage(VALIDATOR_FORMATO_EQUIPE), String.valueOf(numeroEquipe), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (StringUtils.isBlank(numeroNota)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_OBRIGATORIO_NUMERO_NOTA), String.valueOf(numeroNota),
					violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (!NumberUtils.isParsable(numeroNota)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_FORMATO_NUMERO_NOTA), String.valueOf(numeroNota),
					violacoes);
		}

		if (StringUtils.isBlank(valor)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_OBRIGATORIO_VALOR), String.valueOf(valor), violacoes);
		} else {
			valor = sanitizaValor(valor);
			valor = valor.replace(VIRGULA, PONTO);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (!NumberUtils.isParsable(valor)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_FORMATO_VALOR), String.valueOf(valor), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (StringUtils.isBlank(cpf) && StringUtils.isBlank(cnpj)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_OBRIGATORIO_CPF_CNPJ), String.valueOf(StringUtils.EMPTY),
					violacoes);
		} else {
			cpf = sanitizaDocumento(cpf);
			cnpj = sanitizaDocumento(cnpj);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (StringUtils.isNotBlank(cpf) && !isCpf(cpf) && !NumberUtils.isParsable(cpf)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_FORMATO_CPF), String.valueOf(cpf), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (StringUtils.isNotBlank(cnpj) && !isCnpj(cnpj) && !NumberUtils.isParsable(cnpj)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_FORMATO_CNPJ), String.valueOf(cnpj), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (StringUtils.isBlank(tipoDocumento)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_TIPO_DOCUMENTO_OBRIGATORIO),
					String.valueOf(tipoDocumento), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (!ArrayUtils.contains(TipoDocumentoEnum.siglas(), tipoDocumento)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_TIPO_DOCUMENTO_FORMATO), String.valueOf(tipoDocumento),
					violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (TIPO_DOCUMENTO_CNPJ.equalsIgnoreCase(tipoDocumento) && StringUtils.isBlank(cnpj)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_TIPO_DOCUMENTO_CNPJ), String.valueOf(cnpj), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		if (TIPO_DOCUMENTO_CPF.equalsIgnoreCase(tipoDocumento) && StringUtils.isBlank(cpf)) {
			violacoes = violacaoNovas(getMessage(CONTRATA_NOTA_TIPO_DOCUMENTO_CPF), String.valueOf(cnpj), violacoes);
		}

		if (!violacoes.isEmpty()) {
			return Optional.of(violacoes);
		}

		return Optional.empty();
	}

}
