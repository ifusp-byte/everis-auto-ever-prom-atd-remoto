package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO;

public class NotaNegociacaoRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public List<NotasByProtocoloOutputDTO> notasByProtocolo(Long numeroProtocolo) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT DISTINCT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO ( "
				+ ""
				+ ""
				+ " ) ");

		TypedQuery<NotasByProtocoloOutputDTO> query = em.createQuery(sb.toString(), NotasByProtocoloOutputDTO.class);
		query.setParameter("publicoAlvo", numeroProtocolo);

		return query.getResultList();

	}

}
