package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@SuppressWarnings("all")
public class ModeloNotaRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public List<Object[]> modeloNotaMaisUtilizada(Long publicoAlvo) {
		
		LocalDate localDate = LocalDate.now().minusDays(365);
		Date date = java.sql.Date.valueOf(localDate);

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT COUNT(A.numeroModeloNota), A.numeroModeloNota, C.numeroAcao, C.descricao, D.numeroTipoNota, D.nomeTipoNota ");
		sb.append(" FROM  NotaNegociacao A, ModeloNotaNegocio B, AcaoProduto C, TipoNota D ");
		sb.append(" WHERE D.numeroTipoNota = B.numeroTipoNota AND A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroAcao = C.numeroAcao ");
		sb.append(" AND A.dataCriacaoNota > :dataCriacao  ");
		sb.append(" AND B.modeloNotaAtivo = 1 ");
		sb.append(" AND B.contratacaoAutomatica = 'N' ");
		sb.append(" AND B.situacaoModeloNota = 'P' ");
		sb.append(" AND C.acaoProdutoAtivo = 1 ");	
		sb.append(" AND B.numeroModeloNota NOT IN (SELECT C.numeroModeloNota FROM FluxoAtendimento C) ");
		sb.append(" AND B.publicoAlvo <> :publicoAlvo ");
		sb.append(" GROUP  BY A.numeroModeloNota, C.numeroAcao, C.descricao, D.numeroTipoNota, D.nomeTipoNota ");
		sb.append(" ORDER  BY 1 DESC ");

		TypedQuery<Object[]> query = em.createQuery(sb.toString(), Object[].class);
		query.setParameter("publicoAlvo", publicoAlvo);
		query.setParameter("dataCriacao", date);
		query.setMaxResults(5);

		return query.getResultList();

	}

}
