package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;

@Repository
public interface ModeloNotaRepository extends JpaRepository<ModeloNotaNegocio, Long> {

	@Query("SELECT N FROM ModeloNotaNegocio N WHERE ROWNUM <= 1 AND N.numeroModeloNota = ?1")
	ModeloNotaNegocio prazoValidade(Long numeroModeloNota);

	@Query(value = "SELECT A.NU_MODELO_NOTA_NEGOCIO FROM AVL.AVLTB005_MODELO_NOTA_NEGOCIO A, AVL.AVLTB020_ACAO_PRODUTO B WHERE A.NU_MODELO_NOTA_NEGOCIO NOT IN (SELECT C.NU_MODELO_NOTA_NEGOCIO FROM AVL.AVLTB030_FLUXO_ATNTO_MODELO C) AND A.IC_MODELO_VINCULA_DOCUMENTO = 0 AND A.IC_MODELO_NOTA_ATIVO = 1 AND A.IC_SITUACAO_MODELO_NOTA = 'P' AND A.NU_ACAO_PRODUTO = B.NU_ACAO_PRODUTO AND B.IC_ATIVO = 1 AND A.NU_MODELO_NOTA_NEGOCIO = ?1 ORDER BY A.NU_MODELO_NOTA_NEGOCIO DESC", nativeQuery = true)
	Optional<Long> vinculaDocumento(Long numeroModeloNota);

}
