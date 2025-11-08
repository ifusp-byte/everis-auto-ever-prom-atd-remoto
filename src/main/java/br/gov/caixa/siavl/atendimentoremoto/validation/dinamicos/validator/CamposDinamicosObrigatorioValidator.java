package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator;

import java.util.List;
import java.util.Optional;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings("all")
public interface CamposDinamicosObrigatorioValidator {

	Optional<List<ViolacaoDto>> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto);

}
