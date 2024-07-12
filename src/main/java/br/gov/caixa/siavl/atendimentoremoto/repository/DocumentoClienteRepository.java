package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoCliente;

@Repository
public interface DocumentoClienteRepository extends JpaRepository<DocumentoCliente, Long> {

	@Query(value = "SELECT C.NO_TIPO_DOCUMENTO_CLIENTE, A.NO_ANEXO FROM AVL.AVLTB033_DOCUMENTO_CLIENTE A, AVL.AVLTB034_DCMNTO_NOTA_NGCCO B, AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE C WHERE A.NU_CPF_CNPJ_CLIENTE = B.NU_CPF_CNPJ_CLIENTE AND A.NU_TIPO_DOCUMENTO_CLIENTE = C.NU_TIPO_DOCUMENTO_CLIENTE AND B.NU_NOTA_NEGOCIACAO = ?1 AND A.NU_CPF_CNPJ_CLIENTE = ?2 ORDER BY A.TS_INCLUSAO_DOCUMENTO DESC", nativeQuery = true)
	List<Object[]> numeroNomeDocumento(Long numeroNota, Long cpfCnpj);

}
