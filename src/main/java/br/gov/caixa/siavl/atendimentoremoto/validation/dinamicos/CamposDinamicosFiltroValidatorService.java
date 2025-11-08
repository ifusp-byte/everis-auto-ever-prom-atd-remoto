package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings("all")
public interface CamposDinamicosFiltroValidatorService {

	List<ViolacaoDto> validateFiltro(CriaNotaInputDTO criaNotaInputDto);

	Long[] camposDinamicosIds(CriaNotaInputDTO criaNotaInputDto);

	List<String> obterIdsCamposDinamicosInput(CriaNotaInputDTO criaNotaInputDto);

}
