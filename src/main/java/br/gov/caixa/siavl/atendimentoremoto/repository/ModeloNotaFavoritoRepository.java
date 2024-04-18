package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocioFavorito;

@Repository
public interface ModeloNotaFavoritoRepository extends JpaRepository<ModeloNotaNegocioFavorito, Long> {
	
	
	@Query("SELECT A.numeroModeloNota, B.numeroAcao, B.descricao FROM ModeloNotaNegocio A, AcaoProduto B WHERE A.numeroAcao = B.numeroAcao AND B.acaoProdutoAtivo = 1 AND A.modeloNotaAtivo = 1 AND A.situacaoModeloNota = 'P' ORDER BY B.descricao ASC")
	List<Object[]> findModeloNota();
	
	
	@Query("SELECT A.numeroModeloNota, B.numeroAcao, B.descricao, C.dataEscolhaFavorito FROM ModeloNotaNegocio A, AcaoProduto B, ModeloNotaNegocioFavorito C WHERE A.numeroAcao = B.numeroAcao AND A.numeroModeloNota = C.numeroModeloNota AND B.acaoProdutoAtivo = 1 AND A.modeloNotaAtivo = 1 AND A.situacaoModeloNota = 'P' AND C.matriculaFavorito = ?1 ORDER BY C.dataEscolhaFavorito DESC")
	List<Object[]> findModeloNotaFavorita(Long matriculaAtendente);
	
	@Query("SELECT COUNT(A.numeroModeloNota) AS NUMEROFAVORITADAS, A.numeroModeloNota, B.numeroAcao, B.descricao FROM ModeloNotaNegocio A, AcaoProduto B, ModeloNotaNegocioFavorito C WHERE A.numeroAcao = B.numeroAcao AND A.numeroModeloNota = C.numeroModeloNota AND B.acaoProdutoAtivo = 1 AND A.modeloNotaAtivo = 1 AND A.situacaoModeloNota = 'P' GROUP BY A.numeroModeloNota, B.numeroAcao, B.descricao ORDER BY NUMEROFAVORITADAS DESC")
	List<Object[]> findModeloNotaMaisUtilizada();
	
	
	@Query(value="SELECT A.NU_MODELO_NOTA_NEGOCIO, B.NU_CAMPO_MODELO_NOTA, B.NU_ORDEM_CAMPO_MODELO, C.NO_CAMPO_MODELO_NOTA, C.IC_CAMPO_DEFINIDO, C.IC_CAMPO_EDITAVEL, C.IC_CAMPO_OBRIGATORIO, C.DE_ESPACO_RESERVADO, C.IC_TIPO_DADO_CAMPO, C.IC_TIPO_ENTRADA_CAMPO, C.DE_CAMPO, C.QT_CARACTER_CAMPO, C.VR_INICIAL_CAMPO, C.CO_MASCARA_CAMPO FROM AVL.AVLTB005_MODELO_NOTA_NEGOCIO A, AVL.AVLTB018_CAMPO_MODELO B,  AVL.AVLTB017_CAMPO_MODELO_NOTA C WHERE A.NU_MODELO_NOTA_NEGOCIO = B.NU_MODELO_NOTA_NEGOCIO AND  B.NU_CAMPO_MODELO_NOTA = C.NU_CAMPO_MODELO_NOTA AND A.NU_MODELO_NOTA_NEGOCIO =?1 GROUP BY A.NU_MODELO_NOTA_NEGOCIO, B.NU_CAMPO_MODELO_NOTA, B.NU_ORDEM_CAMPO_MODELO, C.NO_CAMPO_MODELO_NOTA, C.IC_CAMPO_DEFINIDO, C.IC_CAMPO_EDITAVEL, C.IC_CAMPO_OBRIGATORIO, C.DE_ESPACO_RESERVADO, C.IC_TIPO_DADO_CAMPO, C.IC_TIPO_ENTRADA_CAMPO, C.DE_CAMPO, C.QT_CARACTER_CAMPO, C.VR_INICIAL_CAMPO, C.CO_MASCARA_CAMPO ORDER BY B.NU_ORDEM_CAMPO_MODELO ASC", nativeQuery=true)
	List<Object[]> modeloNotaDinamico(Long idModeloNota);
	
	
	@Query(value="SELECT A.NO_MODELO_NOTA_NEGOCIO, C.NO_ABREVIADO_PRDTO FROM AVL.AVLTB005_MODELO_NOTA_NEGOCIO A, AVL.AVLTB020_ACAO_PRODUTO B, AVL.AVLVM003_PRODUTO C WHERE A.NU_ACAO_PRODUTO = B.NU_ACAO_PRODUTO AND B.NU_PRODUTO = C.NU_PRODUTO AND A.NU_MODELO_NOTA_NEGOCIO = ?1", nativeQuery=true)
	List<Object[]> notaProduto(Long idModeloNota);
	
}
