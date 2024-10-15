package br.gov.caixa.siavl.atendimentoremoto.repository.custom;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ModeloNotaRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	public List<Object[]> modeloNotaMaisUtilizada(Long publicoAlvo) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT COUNT(A.numeroModeloNota), A.numeroModeloNota, C.numeroAcao, C.descricao ");
		sb.append(" FROM  NotaNegociacao A, ModeloNotaNegocio B, AcaoProduto C ");
		sb.append(" WHERE A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroAcao = C.numeroAcao ");
		sb.append(" AND A.dataCriacaoNota > CURRENT_DATE - 365 ");
		sb.append(" AND B.modeloNotaAtivo = 1 ");
		sb.append(" AND B.contratacaoAutomatica = 'N' ");
		sb.append(" AND B.situacaoModeloNota = 'P' ");
		sb.append(" AND C.acaoProdutoAtivo = 1 ");
		sb.append(" AND B.numeroModeloNota NOT IN (SELECT C.numeroModeloNota FROM FluxoAtendimento C) ");
		sb.append(" AND B.publicoAlvo <> :publicoAlvo ");
		sb.append(" GROUP  BY A.numeroModeloNota, C.numeroAcao, C.descricao ");
		sb.append(" ORDER  BY 1 DESC ");

		TypedQuery<Object[]> query = em.createQuery(sb.toString(), Object[].class);
		query.setParameter("publicoAlvo", publicoAlvo);
		query.setMaxResults(5);

		return query.getResultList();

	}

}
