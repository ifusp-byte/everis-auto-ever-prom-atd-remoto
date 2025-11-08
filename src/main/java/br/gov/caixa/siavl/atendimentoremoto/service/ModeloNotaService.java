package br.gov.caixa.siavl.atendimentoremoto.service;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.CampoOutputDto;

@SuppressWarnings("all")
public interface ModeloNotaService {

	Object modelos(Long idAcaoProduto) throws Exception;

	Object detalhesModelo(Long idModelo) throws Exception;

	List<CampoOutputDto> camposDinamicosModeloNota(Long idModeloNota);
}
