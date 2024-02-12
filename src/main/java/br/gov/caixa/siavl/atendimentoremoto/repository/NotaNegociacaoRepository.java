package br.gov.caixa.siavl.atendimentoremoto.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.NotaNegociacao;

@Repository
public interface NotaNegociacaoRepository extends JpaRepository<NotaNegociacao, Long> {

	@Query("SELECT " + "B.contratacaoAutomatica " + "FROM " + "NotaNegociacao A, " + "ModeloNotaNegocio B "
			+ "WHERE " + "A.numeroModeloNota = B.numeroModeloNota " + "AND " + "A.numeroNota = ?1")
	char findByContratacaoAutomatica(Long idNegociacao);

	@Modifying
	@Transactional
	@Query("UPDATE NotaNegociacao A SET A.numeroSituacaoNota = 14 WHERE A.numeroNota = ?1")
	void updateSituacaoNota(Long idNegociacao); 
	
}


