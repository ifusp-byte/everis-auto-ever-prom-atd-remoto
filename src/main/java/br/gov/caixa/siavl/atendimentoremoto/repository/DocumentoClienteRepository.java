package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoCliente;
import jakarta.transaction.Transactional;

@Repository
public interface DocumentoClienteRepository extends JpaRepository<DocumentoCliente, Long> {

	@Query(value = "SELECT C.NO_TIPO_DOCUMENTO_CLIENTE, A.NO_ANEXO FROM AVL.AVLTB033_DOCUMENTO_CLIENTE A, AVL.AVLTB034_DCMNTO_NOTA_NGCCO B, AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE C WHERE A.NU_CPF_CNPJ_CLIENTE = B.NU_CPF_CNPJ_CLIENTE AND A.NU_TIPO_DOCUMENTO_CLIENTE = C.NU_TIPO_DOCUMENTO_CLIENTE AND B.NU_NOTA_NEGOCIACAO = ?1 AND A.NU_CPF_CNPJ_CLIENTE = ?2 ORDER BY A.TS_INCLUSAO_DOCUMENTO DESC", nativeQuery = true)
	List<Object[]> numeroNomeDocumento(Long numeroNota, Long cpfCnpj);

	@Query(value = "SELECT TB39.NO_TIPO_DOCUMENTO_CLIENTE, TB33.NO_ANEXO FROM AVL.AVLTB033_DOCUMENTO_CLIENTE TB33 INNER JOIN AVL.AVLTB034_DCMNTO_NOTA_NGCCO TB34 ON(TB34.NU_CPF_CNPJ_CLIENTE = TB33.NU_CPF_CNPJ_CLIENTE AND TB34.IC_TIPO_PESSOA = TB33.IC_TIPO_PESSOA AND TB34.TS_INCLUSAO_DOCUMENTO = TB33.TS_INCLUSAO_DOCUMENTO AND TB34.NU_TIPO_DOCUMENTO_CLIENTE = TB33.NU_TIPO_DOCUMENTO_CLIENTE) INNER JOIN AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE TB39 ON TB34.NU_TIPO_DOCUMENTO_CLIENTE = TB39.NU_TIPO_DOCUMENTO_CLIENTE INNER JOIN AVL.AVLTB019_NOTA_NEGOCIACAO TB19 ON TB34.NU_NOTA_NEGOCIACAO = TB19.NU_NOTA_NEGOCIACAO WHERE TB19.IC_SISTEMA_CADASTRO_NOTA = 'P'AND TB19.NU_NOTA_NEGOCIACAO = ?1 ORDER  BY TB19.NU_NOTA_NEGOCIACAO DESC", nativeQuery = true)
	List<Object[]> findNomeDocumentoNomeAnexo(Long numeroNota);

	Optional<DocumentoCliente> findByCodGED(String codGED);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DocumentoCliente A WHERE A.inclusaoDocumento = ?1 AND A.tipoPessoa = ?2 AND A.cpfCnpjCliente = ?3 AND A.tipoDocumentoCliente = ?4 AND A.codGED = ?5")
	void excluiDocumento(Date inclusaoDocumento, String tipoPessoa, Long cpfCnpjCliente, Long tipoDocumentoCliente,
			String codGED);
}
