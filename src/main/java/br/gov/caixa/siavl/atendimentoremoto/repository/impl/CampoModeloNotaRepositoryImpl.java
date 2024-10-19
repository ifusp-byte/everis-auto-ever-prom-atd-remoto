package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.RoteiroNotaCampoDinamicoDTO;

@SuppressWarnings("all")
public class CampoModeloNotaRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public List<RoteiroNotaCampoDinamicoDTO> camposDinamicosByModelo(Long numeroModeloNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.report.dto.RoteiroNotaCampoDinamicoDTO ( ");
		sb.append(" B.numeroCampoModeloNota ");
		sb.append(" , C.nomeCampoModeloNota ");
		sb.append(" ) ");
		sb.append(" FROM  ");
		sb.append(" ModeloNotaNegocio A ");
		sb.append(" , CampoModelo B ");
		sb.append(" , CampoModeloNota C  ");
		sb.append(" WHERE  ");
		sb.append(" A.numeroModeloNota = B.numeroCampoModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = C.numeroCampoModeloNota ");
		sb.append(" AND A.numeroModeloNota = :numeroModeloNota ");
		sb.append(" GROUP BY B.numeroCampoModeloNota, C.numeroCampoModeloNota ");
		sb.append(" ORDER BY B.numeroCampoModeloNota DESC ");

		TypedQuery<RoteiroNotaCampoDinamicoDTO> query = em.createQuery(sb.toString(), RoteiroNotaCampoDinamicoDTO.class);
		query.setParameter("numeroModeloNota", numeroModeloNota);

		return query.getResultList();

	}

}
