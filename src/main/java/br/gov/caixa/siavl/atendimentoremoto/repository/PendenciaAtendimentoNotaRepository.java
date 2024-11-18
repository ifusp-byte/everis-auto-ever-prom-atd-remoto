package br.gov.caixa.siavl.atendimentoremoto.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.gov.caixa.siavl.atendimentoremoto.model.PendenciaAtendimentoNota;

@Repository
public interface PendenciaAtendimentoNotaRepository extends JpaRepository<PendenciaAtendimentoNota, Long> {
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO AVL.AVLTB027_PENDENCIA_ATNTO_NOTA (NU_NOTA_NEGOCIACAO_CRIACAO, NU_MATRICULA_ATENDIMENTO_CRIAC, TS_INICIO_ATENDIMENTO_CRIACAO, TS_INCLUSAO_PENDENCIA_ATNTO, NU_TIPO_PENDENCIA, DE_PENDENCIA_ATNTO_NOTA, NU_NOTA_NEGOCIACAO_SOLUCAO, NU_MATRICULA_ATNTO_SOLUCAO, TS_INICIO_ATENDIMENTO_SOLUCAO, TS_SOLUCAO_PENDENCIA_ATNTO) SELECT NU_NOTA_NEGOCIACAO, NU_MATRICULA_ATENDIMENTO_NOTA, TS_INICIO_ATENDIMENTO_NOTA, CURRENT_DATE, 9, NULL, NULL, NULL, TS_INICIO_ATENDIMENTO_NOTA, NULL FROM AVL.AVLTB023_ATENDIMENTO_NOTA WHERE NU_NOTA_NEGOCIACAO = ?1 AND ROWNUM=1", nativeQuery=true)
	void inserePendenciaAtendimento(Long numeroNota); 

}
