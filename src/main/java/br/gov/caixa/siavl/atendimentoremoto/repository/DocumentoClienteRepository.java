package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoCliente;

@Repository
public interface DocumentoClienteRepository extends JpaRepository<DocumentoCliente, Long> {
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO AVL.AVLTB034_DCMNTO_NOTA_NGCCO (NU_NOTA_NEGOCIACAO, NU_CPF_CNPJ_CLIENTE, IC_TIPO_PESSOA, TS_INCLUSAO_DOCUMENTO, NU_TIPO_DOCUMENTO_CLIENTE) SELECT :numeroNota, NU_CPF_CNPJ_CLIENTE, IC_TIPO_PESSOA, TS_INCLUSAO_DOCUMENTO, NU_TIPO_DOCUMENTO_CLIENTE FROM AVL.AVLTB033_DOCUMENTO_CLIENTE WHERE NU_CPF_CNPJ_CLIENTE = :cpfCnpj AND IC_TIPO_PESSOA = :tipoPessoa AND TS_INCLUSAO_DOCUMENTO = :inclusaoDocumento AND NU_TIPO_DOCUMENTO_CLIENTE = :tipoDocumento", nativeQuery=true)
	void insereDocumentoNota(@Param("numeroNota") Long numeroNota, @Param("cpfCnpj") Long cpfCnpj, @Param("tipoPessoa") String tipoPessoa, @Param("inclusaoDocumento") Date inclusaoDocumento, @Param("tipoDocumento") Long tipoDocumento); 

	
	@Query(value="SELECT A.NU_TIPO_DOCUMENTO_CLIENTE, A.NO_ANEXO FROM AVL.AVLTB033_DOCUMENTO_CLIENTE A, AVL.AVLTB034_DCMNTO_NOTA_NGCCO B WHERE B.NU_NOTA_NEGOCIACAO = ?1", nativeQuery=true)
	Optional<List<Object[]>> numeroNomeDocumento (Long numeroNota);
	
}
