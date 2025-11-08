package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings("all")
public interface CamposDinamicosTamanhoValidatorService {

	List<ViolacaoDto> validateTamanho(CriaNotaInputDTO criaNotaInputDto);

	List<CamposDinamicosModeloNotaValidacaoDTO> obterIdsValoresDinamicosInput(CriaNotaInputDTO criaNotaInputDto);

	List<CamposDinamicosModeloNotaValidacaoDTO> obterIdsTamanhosDinamicos(CriaNotaInputDTO criaNotaInputDto);

}
