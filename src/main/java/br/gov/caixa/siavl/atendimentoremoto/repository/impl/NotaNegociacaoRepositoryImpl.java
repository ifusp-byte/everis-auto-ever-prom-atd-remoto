package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;

import br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ResultadoUnicoDTO;

@SuppressWarnings("all")
public class NotaNegociacaoRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public List<NotasByProtocoloOutputDTO> notasByProtocolo(Long numeroProtocolo) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO ( ");
		sb.append(" D.numeroNota ");
		sb.append(" , A.nomeCliente ");
		sb.append(" , A.cpfCliente ");
		sb.append(" , A.cnpjCliente ");
		sb.append(" , G.descricao ");
		sb.append(" , E.descricao ");
		sb.append(" , H.relatorioNota ");
		sb.append(" , F.numeroModeloNota ");
		sb.append(" , L.jsonLogPlataforma ");
		sb.append(" ) ");
		sb.append(" FROM ");
		sb.append(" AtendimentoCliente A ");
		sb.append(" , AtendimentoNegocio B ");
		sb.append(" , NegocioAgenciaVirtual C ");
		sb.append(" , NotaNegociacao D ");
		sb.append(" , SituacaoNotaNegociacao E ");
		sb.append(" , ModeloNotaNegocio F ");
		sb.append(" , AcaoProduto G ");
		sb.append(" , RelatorioNotaNegociacao H ");
		sb.append(" , LogPlataforma L ");
		sb.append(" WHERE ");
		sb.append(" A.numeroProtocolo = B.numeroProtocolo ");
		sb.append(" AND B.numeroNegocio = C.numeroNegocio ");
		sb.append(" AND C.numeroNegocio = D.numeroNegocio ");
		sb.append(" AND D.numeroNota = H.numeroNota ");
		sb.append(" AND D.numeroSituacaoNota = E.numeroSituacaoNota ");
		sb.append(" AND D.numeroModeloNota = F.numeroModeloNota ");
		sb.append(" AND F.numeroAcao = G.numeroAcao ");
		sb.append(" AND D.numeroNota = L.numeroNota ");
		sb.append(" AND H.numeroNota = L.numeroNota ");
		sb.append(" AND (L.transacaoSistema = 299 OR L.transacaoSistema = 304 ) ");
		sb.append(" AND A.numeroProtocolo = :numeroProtocolo ");
		sb.append(" ORDER BY D.numeroNota DESC ");

		TypedQuery<NotasByProtocoloOutputDTO> query = em.createQuery(sb.toString(), NotasByProtocoloOutputDTO.class);
		query.setParameter("numeroProtocolo", numeroProtocolo);

		List<NotasByProtocoloOutputDTO> notasByProtocolo = query.getResultList();

		if (!notasByProtocolo.isEmpty()) {
			notasByProtocolo.forEach(nota -> nota.setCodGED(anexoAceite(Long.parseLong(nota.getNumeroNota()))));
		}

		return notasByProtocolo;
	}

	public List<NotasByProtocoloOutputDTO> notasByProtocoloTokenSms(Long numeroProtocolo) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO ( ");
		sb.append(" D.numeroNota ");
		sb.append(" , A.nomeCliente ");
		sb.append(" , A.cpfCliente ");
		sb.append(" , A.cnpjCliente ");
		sb.append(" , G.descricao ");
		sb.append(" , E.descricao ");
		sb.append(" , H.relatorioNota ");
		sb.append(" , F.numeroModeloNota ");
		sb.append(" ) ");
		sb.append(" FROM ");
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

		List<NotasByProtocoloOutputDTO> notasByProtocolo = query.getResultList();

		if (!notasByProtocolo.isEmpty()) {
			notasByProtocolo.forEach(nota -> nota.setCodGED(anexoAceite(Long.parseLong(nota.getNumeroNota()))));
		}

		return notasByProtocolo;
	}

	public String anexoAceite(Long numeroNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW br.gov.caixa.siavl.atendimentoremoto.dto.ResultadoUnicoDTO (M.codGED) ");
		sb.append(" FROM ");
		sb.append(" NotaNegociacao D ");
		sb.append(" , DocumentoCliente M ");
		sb.append(" , DocumentoNotaNegociacao N ");
		sb.append(" WHERE ");
		sb.append(" D.numeroNota = N.numeroNota ");
		sb.append(" AND M.tipoPessoa = N.tipoPessoa ");
		sb.append(" AND M.inclusaoDocumento = N.inclusaoDocumento ");
		sb.append(" AND M.tipoDocumentoCliente = N.tipoDocumentoCliente ");
		sb.append(" AND M.cpfCnpjCliente = N.cpfCnpjCliente ");
		sb.append(" AND M.nomeAnexo LIKE '%ACEITE%' ");
		sb.append(" AND D.numeroNota = :numeroNota ");

		TypedQuery<ResultadoUnicoDTO> anexoAceite = em.createQuery(sb.toString(), ResultadoUnicoDTO.class);
		anexoAceite.setParameter("numeroNota", numeroNota);
		
		String anexo = String.valueOf(!anexoAceite.getResultList().isEmpty() ? anexoAceite.getResultList().get(0).getResultado() : StringUtils.EMPTY);
		return anexo; 

	}

}
