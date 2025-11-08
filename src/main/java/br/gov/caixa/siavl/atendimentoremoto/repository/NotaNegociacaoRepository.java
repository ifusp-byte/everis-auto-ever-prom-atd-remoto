package br.gov.caixa.siavl.atendimentoremoto.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.NotaNegociacao;
import jakarta.transaction.Transactional;

@Repository
public interface NotaNegociacaoRepository extends JpaRepository<NotaNegociacao, Long> {

	@Query("SELECT " + "B.contratacaoAutomatica " + "FROM " + "NotaNegociacao A, " + "ModeloNotaNegocio B "
			+ "WHERE " + "A.numeroModeloNota = B.numeroModeloNota " + "AND " + "A.numeroNota = ?1")
	char findByContratacaoAutomatica(Long idNegociacao);

	@Modifying
	@Transactional
	@Query("UPDATE NotaNegociacao A SET A.numeroSituacaoNota = 14 WHERE A.numeroNota = ?1")
	void updateSituacaoNota(Long idNegociacao); 
	
	@Modifying
	@Transactional
	@Query("UPDATE NotaNegociacao A SET A.numeroSituacaoNota = 22 WHERE A.numeroNota = ?1")
	void enviaNotaCliente(Long numeroNota); 
	
	@Modifying
	@Transactional
	@Query("UPDATE NotaNegociacao A SET A.numeroSituacaoNota = 14 WHERE A.numeroNota = ?1")
	void contrataNota(Long numeroNota); 
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE AVL.AVLTB019_NOTA_NEGOCIACAO " +
								 " SET VR_CAMPO_NOTA_NEGOCIACAO = ?1 " +
								 " WHERE NU_NOTA_NEGOCIACAO = ?2 ", nativeQuery = true)
	void updateXmlData(String xmlData, Long id);
	
	@Query("SELECT A FROM NotaNegociacao A WHERE A.numeroNota = ?1")
	Optional<NotaNegociacao> findByNumeroNota(Long numeroNota);
}


