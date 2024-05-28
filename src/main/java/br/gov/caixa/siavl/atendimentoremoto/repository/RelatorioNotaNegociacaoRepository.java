package br.gov.caixa.siavl.atendimentoremoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.RelatorioNotaNegociacao;

@Repository
public interface RelatorioNotaNegociacaoRepository extends JpaRepository<RelatorioNotaNegociacao, Long> {
	
	@Query(value="SELECT * FROM AVL.AVLTB065_RLTRO_NOTA_NEGOCIACAO A WHERE NU_NOTA_NEGOCIACAO = ?1", nativeQuery=true)
	RelatorioNotaNegociacao findByNumeroNota(Long idModeloNota);
	

}
