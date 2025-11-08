package br.gov.caixa.siavl.atendimentoremoto.validation.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_FILTRO_ERRO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_OBRIGATORIO_ERRO;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.notaErroOutputBuild;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.ContrataNotaFiltroValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.ContrataNotaObrigatorioFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.service.ContrataNotaViolacoesService;

@Service
@SuppressWarnings("all")
public class ContrataNotaViolacoesServiceImpl implements ContrataNotaViolacoesService {

	@Autowired
	ContrataNotaObrigatorioFormatoValidatorService contrataNotaObrigatorioFormatoValidatorService;

	@Autowired
	ContrataNotaFiltroValidatorService contrataNotaFiltroValidatorService;

	@Override
	public List<ViolacaoDto> violacoesObrigatorio(ContrataNotaInputDTO contrataNotaInputDTO) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesObrigatorio = contrataNotaObrigatorioFormatoValidatorService
				.validateObrigatorioFormato(contrataNotaInputDTO);
		todasViolacoes.addAll(violacoesObrigatorio);

		return todasViolacoes;

	}

	@Override
	public List<ViolacaoDto> violacoesFiltro(ContrataNotaInputDTO contrataNotaInputDTO) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesFiltro = contrataNotaFiltroValidatorService.validateFiltro(contrataNotaInputDTO);
		todasViolacoes.addAll(violacoesFiltro);

		return todasViolacoes;

	}

	@Override
	public Object violacoesValidar(ContrataNotaInputDTO contrataNotaInputDTO) {

		Object response = null;

		List<ViolacaoDto> violacoesObrigatorio = violacoesObrigatorio(contrataNotaInputDTO);
		if (!violacoesObrigatorio.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(CONTRATA_NOTA_OBRIGATORIO_ERRO),
					violacoesObrigatorio);
		}

		List<ViolacaoDto> violacoesFiltro = violacoesFiltro(contrataNotaInputDTO);
		if (!violacoesFiltro.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(CONTRATA_NOTA_FILTRO_ERRO),
					violacoesFiltro);
		}

		return response;
	}

}
