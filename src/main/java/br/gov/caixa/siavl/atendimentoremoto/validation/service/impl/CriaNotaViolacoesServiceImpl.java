package br.gov.caixa.siavl.atendimentoremoto.validation.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.NOTA_ERRO_CAMPOS_DINAMICOS;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.NOTA_ERRO_CAMPOS_FORMATO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.NOTA_ERRO_CAMPOS_OBRIGATORIO;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.notaErroOutputBuild;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.crianota.CriaNotaObrigatorioValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosFiltroValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosFormatoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosMascaraValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosObrigatorioValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosTamanhoValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.service.CriaNotaViolacoesService;

@Service
@SuppressWarnings("all")
public class CriaNotaViolacoesServiceImpl implements CriaNotaViolacoesService {

	@Autowired
	CamposDinamicosObrigatorioValidatorService camposDinamicosObrigatorioValidatorService;

	@Autowired
	CamposDinamicosTamanhoValidatorService camposDinamicosTamanhoValidatorService;

	@Autowired
	CamposDinamicosFormatoValidatorService camposDinamicosFormatoValidatorService;

	@Autowired
	CamposDinamicosMascaraValidatorService camposDinamicosMascaraValidatorService;

	@Autowired
	CamposDinamicosFiltroValidatorService camposDinamicosFiltroValidatorService;

	@Autowired
	CriaNotaObrigatorioValidatorService criaNotaObrigatorioValidatorService;

	@Autowired
	CriaNotaFormatoValidatorService criaNotaFormatoValidatorService;

	@Override
	public List<ViolacaoDto> violacoesTodas(CriaNotaInputDTO criaNotaInputDto) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesObrigatorio = criaNotaObrigatorioValidatorService
				.validateObrigatorio(criaNotaInputDto);
		todasViolacoes.addAll(violacoesObrigatorio);

		List<ViolacaoDto> violacoesFormato = criaNotaFormatoValidatorService.validateFormato(criaNotaInputDto);
		todasViolacoes.addAll(violacoesFormato);

		List<ViolacaoDto> violacoesDinamicosFiltro = (List<ViolacaoDto>) camposDinamicosFiltroValidatorService
				.validateFiltro(criaNotaInputDto);
		todasViolacoes.addAll(violacoesDinamicosFiltro);

		return todasViolacoes;

	}

	@Override
	public List<ViolacaoDto> violacoesObrigatorio(CriaNotaInputDTO criaNotaInputDto) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesObrigatorio = criaNotaObrigatorioValidatorService
				.validateObrigatorio(criaNotaInputDto);
		todasViolacoes.addAll(violacoesObrigatorio);

		return todasViolacoes;

	}

	@Override
	public List<ViolacaoDto> violacoesFormato(CriaNotaInputDTO criaNotaInputDto) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesFormato = criaNotaFormatoValidatorService.validateFormato(criaNotaInputDto);
		todasViolacoes.addAll(violacoesFormato);

		return todasViolacoes;

	}

	@Override
	public List<ViolacaoDto> violacoesDinamicosFiltro(CriaNotaInputDTO criaNotaInputDto) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesDinamicosFiltro = (List<ViolacaoDto>) camposDinamicosFiltroValidatorService
				.validateFiltro(criaNotaInputDto);
		todasViolacoes.addAll(violacoesDinamicosFiltro);

		return todasViolacoes;

	}

	@Override
	public List<ViolacaoDto> violacoesDinamicosObrigatorio(CriaNotaInputDTO criaNotaInputDto) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesDinamicosObrigatorio = (List<ViolacaoDto>) camposDinamicosObrigatorioValidatorService
				.validateObrigatorio(criaNotaInputDto);
		todasViolacoes.addAll(violacoesDinamicosObrigatorio);

		return todasViolacoes;

	}

	@Override
	public List<ViolacaoDto> violacoesDinamicosTamanho(CriaNotaInputDTO criaNotaInputDto) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesDinamicosTamanho = (List<ViolacaoDto>) camposDinamicosTamanhoValidatorService
				.validateTamanho(criaNotaInputDto);
		todasViolacoes.addAll(violacoesDinamicosTamanho);

		return todasViolacoes;

	}

	@Override
	public List<ViolacaoDto> violacoesDinamicosFormato(CriaNotaInputDTO criaNotaInputDto) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesDinamicosFormato = (List<ViolacaoDto>) camposDinamicosFormatoValidatorService
				.validateFormato(criaNotaInputDto);
		todasViolacoes.addAll(violacoesDinamicosFormato);

		return todasViolacoes;

	}

	@Override
	public List<ViolacaoDto> violacoesDinamicosMascara(CriaNotaInputDTO criaNotaInputDto) {

		List<ViolacaoDto> todasViolacoes = new ArrayList<>();

		List<ViolacaoDto> violacoesDinamicosMascara = (List<ViolacaoDto>) camposDinamicosMascaraValidatorService
				.validateMascara(criaNotaInputDto);
		todasViolacoes.addAll(violacoesDinamicosMascara);

		return todasViolacoes;

	}

	@Override
	public Object violacoesValidar(CriaNotaInputDTO criaNotaInputDto) {

		Object response = null;

		List<ViolacaoDto> violacoesObrigatorio = violacoesObrigatorio(criaNotaInputDto);
		if (!violacoesObrigatorio.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(NOTA_ERRO_CAMPOS_OBRIGATORIO),
					violacoesObrigatorio);
		}

		List<ViolacaoDto> violacoesFormato = violacoesFormato(criaNotaInputDto);
		if (!violacoesFormato.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(NOTA_ERRO_CAMPOS_FORMATO),
					violacoesFormato);
		}

		List<ViolacaoDto> violacoesDinamicosFiltro = violacoesDinamicosFiltro(criaNotaInputDto);
		if (!violacoesDinamicosFiltro.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(NOTA_ERRO_CAMPOS_DINAMICOS),
					violacoesDinamicosFiltro);
		}

		List<ViolacaoDto> violacoesDinamicosObrigatorio = violacoesDinamicosObrigatorio(criaNotaInputDto);
		if (!violacoesDinamicosObrigatorio.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(NOTA_ERRO_CAMPOS_DINAMICOS),
					violacoesDinamicosObrigatorio);
		}

		List<ViolacaoDto> violacoesDinamicosTamanho = violacoesDinamicosTamanho(criaNotaInputDto);
		if (!violacoesDinamicosTamanho.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(NOTA_ERRO_CAMPOS_DINAMICOS),
					violacoesDinamicosTamanho);
		}

		List<ViolacaoDto> violacoesDinamicosFormato = violacoesDinamicosFormato(criaNotaInputDto);
		if (!violacoesDinamicosFormato.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(NOTA_ERRO_CAMPOS_DINAMICOS),
					violacoesDinamicosFormato);
		}

		List<ViolacaoDto> violacoesDinamicosMascara = violacoesDinamicosMascara(criaNotaInputDto);
		if (!violacoesDinamicosMascara.isEmpty()) {
			return notaErroOutputBuild(HttpStatus.BAD_REQUEST.value(), getMessage(NOTA_ERRO_CAMPOS_DINAMICOS),
					violacoesDinamicosMascara);
		}

		return response;
	}

}
