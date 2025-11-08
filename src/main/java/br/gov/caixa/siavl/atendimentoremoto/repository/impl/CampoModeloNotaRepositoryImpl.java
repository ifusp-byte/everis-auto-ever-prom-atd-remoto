package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.List;
import java.util.Optional;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@SuppressWarnings("all")
public class CampoModeloNotaRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public Optional<List<CamposDinamicosModeloNotaDTO>> camposDinamicos(Long idCampoDinamico) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaDTO ( ");
		sb.append(" B.numeroCampoModeloNota ");
		sb.append(" , C.nomeCampoModeloNota ");
		sb.append(" ) ");
		sb.append(" FROM  ");
		sb.append(" ModeloNotaNegocio A ");
		sb.append(" , CampoModelo B ");
		sb.append(" , CampoModeloNota C  ");
		sb.append(" WHERE  ");
		sb.append(" A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = C.numeroCampoModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = :idCampoDinamico ");
		sb.append(" GROUP BY B.numeroCampoModeloNota, C.nomeCampoModeloNota ");
		sb.append(" ORDER BY B.numeroCampoModeloNota DESC ");

		TypedQuery<CamposDinamicosModeloNotaDTO> query = em.createQuery(sb.toString(),
				CamposDinamicosModeloNotaDTO.class);
		query.setParameter("idCampoDinamico", idCampoDinamico);

		return Optional.of(query.getResultList());

	}

	public List<CamposDinamicosModeloNotaValidacaoDTO> camposDinamicosValidacao(Long numeroModeloNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO ( ");
		sb.append(" B.numeroCampoModeloNota ");
		sb.append(" , C.nomeCampoModeloNota ");
		sb.append(" , C.flagbrigatorio ");
		sb.append(" , C.flagTipoDado ");
		sb.append(" , C.quantidadeCaracteres ");
		sb.append(" ) ");
		sb.append(" FROM  ");
		sb.append(" ModeloNotaNegocio A ");
		sb.append(" , CampoModelo B ");
		sb.append(" , CampoModeloNota C  ");
		sb.append(" WHERE  ");
		sb.append(" A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = C.numeroCampoModeloNota ");
		sb.append(" AND A.numeroModeloNota = :numeroModeloNota ");
		sb.append(" GROUP BY B.numeroCampoModeloNota, C.nomeCampoModeloNota, C.flagbrigatorio, C.flagTipoDado, C.quantidadeCaracteres ");
		sb.append(" ORDER BY B.numeroCampoModeloNota DESC ");

		TypedQuery<CamposDinamicosModeloNotaValidacaoDTO> query = em.createQuery(sb.toString(),
				CamposDinamicosModeloNotaValidacaoDTO.class);
		query.setParameter("numeroModeloNota", numeroModeloNota);

		return query.getResultList();

	}

	public List<Long> camposDinamicosValidacaoFiltro(Long numeroModeloNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT ");
		sb.append(" B.numeroCampoModeloNota ");
		sb.append(" FROM  ");
		sb.append(" ModeloNotaNegocio A ");
		sb.append(" , CampoModelo B ");
		sb.append(" , CampoModeloNota C  ");
		sb.append(" WHERE  ");
		sb.append(" A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = C.numeroCampoModeloNota ");
		sb.append(" AND A.numeroModeloNota = :numeroModeloNota ");
		sb.append(" GROUP BY B.numeroCampoModeloNota, C.nomeCampoModeloNota ");
		sb.append(" ORDER BY B.numeroCampoModeloNota DESC ");

		TypedQuery<Long> query = em.createQuery(sb.toString(), Long.class);
		query.setParameter("numeroModeloNota", numeroModeloNota);

		return query.getResultList();

	}

	public List<Long> camposDinamicosValidacaoObrigatorio(Long numeroModeloNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT ");
		sb.append(" B.numeroCampoModeloNota ");
		sb.append(" FROM  ");
		sb.append(" ModeloNotaNegocio A ");
		sb.append(" , CampoModelo B ");
		sb.append(" , CampoModeloNota C  ");
		sb.append(" WHERE  ");
		sb.append(" A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = C.numeroCampoModeloNota ");
		sb.append(" AND C.flagbrigatorio = 1 ");
		sb.append(" AND A.numeroModeloNota = :numeroModeloNota ");
		sb.append(" GROUP BY B.numeroCampoModeloNota, C.nomeCampoModeloNota ");
		sb.append(" ORDER BY B.numeroCampoModeloNota DESC ");

		TypedQuery<Long> query = em.createQuery(sb.toString(), Long.class);
		query.setParameter("numeroModeloNota", numeroModeloNota);

		return query.getResultList();

	}

	public List<CamposDinamicosModeloNotaValidacaoDTO> camposDinamicosValidacaoTamanho(Long numeroModeloNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO ( ");
		sb.append(" B.numeroCampoModeloNota ");
		sb.append(" , C.nomeCampoModeloNota ");
		sb.append(" , C.flagbrigatorio ");
		sb.append(" , C.flagTipoDado ");
		sb.append(" , C.quantidadeCaracteres ");
		sb.append(" ) ");
		sb.append(" FROM  ");
		sb.append(" ModeloNotaNegocio A ");
		sb.append(" , CampoModelo B ");
		sb.append(" , CampoModeloNota C  ");
		sb.append(" WHERE  ");
		sb.append(" A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = C.numeroCampoModeloNota ");
		sb.append(" AND A.numeroModeloNota = :numeroModeloNota ");
		sb.append(" GROUP BY B.numeroCampoModeloNota, C.nomeCampoModeloNota, C.flagbrigatorio, C.flagTipoDado, C.quantidadeCaracteres ");
		sb.append(" ORDER BY B.numeroCampoModeloNota DESC ");

		TypedQuery<CamposDinamicosModeloNotaValidacaoDTO> query = em.createQuery(sb.toString(),
				CamposDinamicosModeloNotaValidacaoDTO.class);
		query.setParameter("numeroModeloNota", numeroModeloNota);

		return query.getResultList();

	}
	
	
	
	public List<CamposDinamicosModeloNotaValidacaoDTO> camposDinamicosValidacaoMascara(Long numeroModeloNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO ( ");
		sb.append(" B.numeroCampoModeloNota ");
		sb.append(" , C.nomeCampoModeloNota ");
		sb.append(" , C.flagbrigatorio ");
		sb.append(" , C.flagTipoDado ");
		sb.append(" , C.quantidadeCaracteres ");
		sb.append(" , C.mascaraCampo ");
		sb.append(" ) ");
		sb.append(" FROM  ");
		sb.append(" ModeloNotaNegocio A ");
		sb.append(" , CampoModelo B ");
		sb.append(" , CampoModeloNota C  ");
		sb.append(" WHERE  ");
		sb.append(" A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = C.numeroCampoModeloNota ");
		sb.append(" AND A.numeroModeloNota = :numeroModeloNota ");
		sb.append(" GROUP BY B.numeroCampoModeloNota, C.nomeCampoModeloNota, C.flagbrigatorio, C.flagTipoDado, C.quantidadeCaracteres, C.mascaraCampo ");
		sb.append(" ORDER BY B.numeroCampoModeloNota DESC ");

		TypedQuery<CamposDinamicosModeloNotaValidacaoDTO> query = em.createQuery(sb.toString(),
				CamposDinamicosModeloNotaValidacaoDTO.class);
		query.setParameter("numeroModeloNota", numeroModeloNota);

		return query.getResultList();

	}

	public Optional<CamposDinamicosModeloNotaDTO> campoDinamico(Long idCampoDinamico) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT NEW  br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaDTO ( ");
		sb.append(" B.numeroCampoModeloNota ");
		sb.append(" , C.nomeCampoModeloNota ");
		sb.append(" ) ");
		sb.append(" FROM  ");
		sb.append(" ModeloNotaNegocio A ");
		sb.append(" , CampoModelo B ");
		sb.append(" , CampoModeloNota C  ");
		sb.append(" WHERE  ");
		sb.append(" A.numeroModeloNota = B.numeroModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = C.numeroCampoModeloNota ");
		sb.append(" AND B.numeroCampoModeloNota = :idCampoDinamico ");
		sb.append(" GROUP BY B.numeroCampoModeloNota, C.nomeCampoModeloNota ");
		sb.append(" ORDER BY B.numeroCampoModeloNota DESC ");

		TypedQuery<CamposDinamicosModeloNotaDTO> query = em.createQuery(sb.toString(),
				CamposDinamicosModeloNotaDTO.class);
		query.setParameter("idCampoDinamico", idCampoDinamico);

		return query.getResultList().stream().findFirst();

	}

}
