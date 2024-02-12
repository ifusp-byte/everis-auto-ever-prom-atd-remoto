package br.gov.caixa.siavl.atendimentoremoto.service;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaOutputDto;

public interface ModeloNotaService {

	List<ModeloNotaOutputDto> consultaModeloNota();

	List<ModeloNotaOutputDto> consultaModeloNotaFavorita(String token);

	Boolean adicionaModeloNotaFavorita(String token, Long numeroModeloNota);
	
	List<ModeloNotaOutputDto> consultaModeloNotaMaisUtilizada();

}
