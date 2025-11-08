package br.gov.caixa.siavl.atendimentoremoto.validation.service;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings("all")
public interface CriaNotaViolacoesService {
	Object violacoesValidar(CriaNotaInputDTO criaNotaInputDto);

	List<ViolacaoDto> violacoesTodas(CriaNotaInputDTO criaNotaInputDto);

	List<ViolacaoDto> violacoesObrigatorio(CriaNotaInputDTO criaNotaInputDto);

	List<ViolacaoDto> violacoesFormato(CriaNotaInputDTO criaNotaInputDto);

	List<ViolacaoDto> violacoesDinamicosFiltro(CriaNotaInputDTO criaNotaInputDto);

	List<ViolacaoDto> violacoesDinamicosObrigatorio(CriaNotaInputDTO criaNotaInputDto);

	List<ViolacaoDto> violacoesDinamicosTamanho(CriaNotaInputDTO criaNotaInputDto);

	List<ViolacaoDto> violacoesDinamicosFormato(CriaNotaInputDTO criaNotaInputDto);

	List<ViolacaoDto> violacoesDinamicosMascara(CriaNotaInputDTO criaNotaInputDto);

}
