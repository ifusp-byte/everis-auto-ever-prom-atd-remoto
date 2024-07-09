package br.gov.caixa.siavl.atendimentoremoto.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoNotaNegociacao;

@Repository
public interface DocumentoNotaNegociacaoRepository extends JpaRepository<DocumentoNotaNegociacao, Long> {
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO AVL.AVLTB034_DCMNTO_NOTA_NGCCO (NU_NOTA_NEGOCIACAO, NU_CPF_CNPJ_CLIENTE, IC_TIPO_PESSOA, TS_INCLUSAO_DOCUMENTO, NU_TIPO_DOCUMENTO_CLIENTE) SELECT NU_CPF_CNPJ_CLIENTE, IC_TIPO_PESSOA, TS_INCLUSAO_DOCUMENTO, NU_TIPO_DOCUMENTO_CLIENTE FROM AVL.AVLTB033_DOCUMENTO_CLIENTE WHERE NU_NOTA_NEGOCIACAO = ?1 AND ROWNUM=1", nativeQuery=true)
	void insereDocumentoNota(Long numeroNota); 

}
