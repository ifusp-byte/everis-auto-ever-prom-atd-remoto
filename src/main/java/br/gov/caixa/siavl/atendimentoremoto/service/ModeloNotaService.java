package br.gov.caixa.siavl.atendimentoremoto.service;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaOutputDto;

@SuppressWarnings("all")
public interface ModeloNotaService {

	List<ModeloNotaOutputDto> consultaModeloNota(String cpfCnpj);

	List<ModeloNotaOutputDto> consultaModeloNotaFavorita(String token, String cpfCnpj);

	Boolean adicionaModeloNotaFavorita(String token, Long numeroModeloNota);
	
	List<ModeloNotaOutputDto> consultaModeloNotaMaisUtilizada(String cpfCnpj);
	
	Object modeloNotaDinamico (String token, Long numeroModeloNota, ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO) throws Exception;

}
