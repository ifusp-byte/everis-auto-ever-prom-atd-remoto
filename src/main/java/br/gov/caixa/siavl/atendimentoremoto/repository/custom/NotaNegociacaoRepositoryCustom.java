package br.gov.caixa.siavl.atendimentoremoto.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO;

public class NotaNegociacaoRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	public List<NotasByProtocoloOutputDTO> notasByProtocolo(Long numeroProtocolo) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT DISTINCT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO ( ");
		sb.append(" D.numeroNota ");
		sb.append(" , A.nomeCliente ");
		sb.append(" , A.cpfCliente ");
		sb.append(" , A.cnpjCliente ");
		sb.append(" , G.descricao ");
		sb.append(" , E.descricao ");
		sb.append(" , H.relatorioNota ");
		sb.append(" ) ");
		sb.append(" FROM");
		sb.append(" AtendimentoCliente A ");
		sb.append(" , AtendimentoNegocio B ");
		sb.append(" , NegocioAgenciaVirtual C ");
		sb.append(" , NotaNegociacao D ");
		sb.append(" , SituacaoNotaNegociacao E ");
		sb.append(" , ModeloNotaNegocio F ");
		sb.append(" , AcaoProduto G ");
		sb.append(" , RelatorioNotaNegociacao H ");
		sb.append(" WHERE ");
		sb.append(" A.numeroProtocolo = B.numeroProtocolo ");
		sb.append(" AND B.numeroNegocio = C.numeroNegocio ");
		sb.append(" AND C.numeroNegocio = D.numeroNegocio ");
		sb.append(" AND D.numeroNota = H.numeroNota ");
		sb.append(" AND D.numeroSituacaoNota = E.numeroSituacaoNota ");
		sb.append(" AND D.numeroModeloNota = F.numeroModeloNota ");
		sb.append(" AND F.numeroAcao = G.numeroAcao ");
		sb.append(" AND A.numeroProtocolo = :numeroProtocolo ");
		sb.append(" ORDER BY D.numeroNota DESC ");


		TypedQuery<NotasByProtocoloOutputDTO> query = em.createQuery(sb.toString(), NotasByProtocoloOutputDTO.class);
		query.setParameter("numeroProtocolo", numeroProtocolo);

		return query.getResultList();

	}

}
