package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.dto.TipoContratacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.model.TipoContratacao;

@Repository
public interface TipoContratacaoRepository extends JpaRepository<TipoContratacao, Long> {

	@Query(value = "SELECT B.NU_FORMALIZACAO_CONTRATACAO, B.DE_FORMALIZACAO_CONTRATACAO FROM AVL.AVLTB085_FRMLO_CNTCO_MODELO A, AVL.AVLTB084_TIPO_FRMLO_CNTCO B  WHERE A.NU_FORMALIZACAO_CONTRATACAO = B.NU_FORMALIZACAO_CONTRATACAO AND B.IC_FORMALIZACAO_CNTCO_ATIVO = 'S' AND A.NU_MODELO_NOTA_NEGOCIO = ?1", nativeQuery = true)
	List<TipoContratacaoDTO> tipoContratacao(Long numeroModeloNota);

}
