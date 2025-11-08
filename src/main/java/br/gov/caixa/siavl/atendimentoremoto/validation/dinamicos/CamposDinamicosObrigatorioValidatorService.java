package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings("all")
public interface CamposDinamicosObrigatorioValidatorService {

	List<ViolacaoDto> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto);

	List<Long> camposDinamicosIdsObrigatorios(CriaNotaInputDTO criaNotaInputDto);

	String[] obterIdsCamposDinamicosInput(CriaNotaInputDTO criaNotaInputDto);

	List<CamposDinamicosModeloNotaValidacaoDTO> valoresDinamicosInput(CriaNotaInputDTO criaNotaInputDto);

}
