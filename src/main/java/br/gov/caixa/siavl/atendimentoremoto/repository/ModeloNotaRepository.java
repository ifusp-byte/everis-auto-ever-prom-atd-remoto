package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocioFavorito;

@Repository
public interface ModeloNotaRepository extends JpaRepository<ModeloNotaNegocioFavorito, Long> {
	
	
	@Query("SELECT A.numeroModeloNota, B.numeroAcao, B.descricao FROM ModeloNotaNegocio A, AcaoProduto B WHERE A.numeroAcao = B.numeroAcao AND B.acaoProdutoAtivo = 1 AND A.modeloNotaAtivo = 1 AND A.situacaoModeloNota = 'P' ORDER BY B.descricao ASC")
	List<Object[]> findModeloNota();
	
	
	@Query("SELECT A.numeroModeloNota, B.numeroAcao, B.descricao, C.dataEscolhaFavorito FROM ModeloNotaNegocio A, AcaoProduto B, ModeloNotaNegocioFavorito C WHERE A.numeroAcao = B.numeroAcao AND A.numeroModeloNota = C.numeroModeloNota AND B.acaoProdutoAtivo = 1 AND A.modeloNotaAtivo = 1 AND A.situacaoModeloNota = 'P' AND C.matriculaFavorito = ?1 ORDER BY C.dataEscolhaFavorito DESC")
	List<Object[]> findModeloNotaFavorita(Long matriculaAtendente);
	
	@Query("SELECT COUNT(A.numeroModeloNota) AS NUMEROFAVORITADAS, A.numeroModeloNota, B.numeroAcao, B.descricao FROM ModeloNotaNegocio A, AcaoProduto B, ModeloNotaNegocioFavorito C WHERE A.numeroAcao = B.numeroAcao AND A.numeroModeloNota = C.numeroModeloNota AND B.acaoProdutoAtivo = 1 AND A.modeloNotaAtivo = 1 AND A.situacaoModeloNota = 'P' GROUP BY A.numeroModeloNota, B.numeroAcao, B.descricao ORDER BY NUMEROFAVORITADAS DESC")
	List<Object[]> findModeloNotaMaisUtilizada();
	

}
