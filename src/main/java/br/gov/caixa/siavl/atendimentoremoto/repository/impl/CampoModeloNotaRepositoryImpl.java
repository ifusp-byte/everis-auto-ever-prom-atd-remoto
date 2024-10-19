package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.RoteiroNotaCampoDinamicoDTO;

@SuppressWarnings("all")
public class CampoModeloNotaRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public List<RoteiroNotaCampoDinamicoDTO> camposDinamicosByModelo(Long numeroModeloNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.report.dto.RoteiroNotaCampoDinamicoDTO ( ");

		sb.append(" B.NU_CAMPO_MODELO_NOTA ");
		sb.append(" , C.NO_CAMPO_MODELO_NOTA ");
		sb.append(" ) ");
		sb.append(" FROM ");
		sb.append(" AVL.AVLTB005_MODELO_NOTA_NEGOCIO A ");
		sb.append(" , AVL.AVLTB018_CAMPO_MODELO B ");
		sb.append(" , AVL.AVLTB017_CAMPO_MODELO_NOTA C ");
		sb.append(" WHERE ");
		sb.append(" A.NU_MODELO_NOTA_NEGOCIO = B.NU_MODELO_NOTA_NEGOCIO ");
		sb.append(" AND B.NU_CAMPO_MODELO_NOTA = C.NU_CAMPO_MODELO_NOTA ");
		sb.append(" AND A.NU_MODELO_NOTA_NEGOCIO = :numeroModeloNota ");
		sb.append(" GROUP BY B.NU_CAMPO_MODELO_NOTA, C.NO_CAMPO_MODELO_NOTA ");
		sb.append(" ORDER BY B.NU_CAMPO_MODELO_NOTA DESC ");

		Query query = em.createNativeQuery(sb.toString(), RoteiroNotaCampoDinamicoDTO.class);

		query.setParameter("numeroModeloNota", numeroModeloNota);

		return query.getResultList();

	}

}
