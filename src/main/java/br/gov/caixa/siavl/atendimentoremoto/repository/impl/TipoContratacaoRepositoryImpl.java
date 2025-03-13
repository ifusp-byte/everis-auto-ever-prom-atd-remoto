package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.gov.caixa.siavl.atendimentoremoto.dto.TipoContratacaoDTO;

@SuppressWarnings("all")
public class TipoContratacaoRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public List<TipoContratacaoDTO> tiposContratacaoByModeloNota(Long numeroModeloNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(
				" SELECT B.NU_FORMALIZACAO_CONTRATACAO, B.DE_FORMALIZACAO_CONTRATACAO FROM AVL.AVLTB085_FRMLO_CNTCO_MODELO A, AVL.AVLTB084_TIPO_FRMLO_CNTCO B  WHERE A.NU_FORMALIZACAO_CONTRATACAO = B.NU_FORMALIZACAO_CONTRATACAO AND B.IC_FORMALIZACAO_CNTCO_ATIVO = 'S' AND A.NU_MODELO_NOTA_NEGOCIO = :numeroModeloNota ORDER BY B.NU_FORMALIZACAO_CONTRATACAO ASC ");

		List<Object[]> tiposContratacaoLista = em.createNativeQuery(sb.toString())
				.setParameter("numeroModeloNota", numeroModeloNota).getResultList();

		List<TipoContratacaoDTO> tiposContratacao = new ArrayList<>();

		if (!tiposContratacaoLista.isEmpty()) {
			tiposContratacaoLista.stream().forEach(contratacao -> {
				TipoContratacaoDTO tipoContratacao = new TipoContratacaoDTO();
				tipoContratacao.setId(Long.valueOf(String.valueOf(contratacao[0])));
				tipoContratacao.setDescricao(String.valueOf(contratacao[1]));
				tiposContratacao.add(tipoContratacao);
			});
		}

		return tiposContratacao;
	}

}
