package br.gov.caixa.siavl.atendimentoremoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.RelatorioNotaNegociacao;
import jakarta.transaction.Transactional;

@Repository
public interface RelatorioNotaNegociacaoRepository extends JpaRepository<RelatorioNotaNegociacao, Long> {
	
	@Query(value="SELECT * FROM AVL.AVLTB065_RLTRO_NOTA_NEGOCIACAO A WHERE NU_NOTA_NEGOCIACAO = ?1", nativeQuery=true)
	RelatorioNotaNegociacao findByNumeroNota(Long idModeloNota);
	
	@Modifying
	@Transactional
	@Query("UPDATE RelatorioNotaNegociacao A SET A.situacaoNota = 22 WHERE A.numeroNota = ?1")
	void enviaNotaCliente(Long numeroNota); 
	
	@Modifying
	@Transactional
	@Query("UPDATE RelatorioNotaNegociacao A SET A.situacaoNota = 14 WHERE A.numeroNota = ?1")
	void contrataNota(Long numeroNota); 
	
}
