package br.gov.caixa.siavl.atendimentoremoto.validation.crianota;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@SuppressWarnings({ WARNINGS })
public interface CriaNotaObrigatorioValidatorService {

	List<ViolacaoDto> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto);

}
