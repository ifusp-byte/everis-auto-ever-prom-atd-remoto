package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoCliente;

@Repository
public interface DocumentoClienteRepository extends JpaRepository<DocumentoCliente, Long> {
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO AVL.AVLTB034_DCMNTO_NOTA_NGCCO (NU_NOTA_NEGOCIACAO, NU_CPF_CNPJ_CLIENTE, IC_TIPO_PESSOA, TS_INCLUSAO_DOCUMENTO, NU_TIPO_DOCUMENTO_CLIENTE) SELECT ?1, NU_CPF_CNPJ_CLIENTE, IC_TIPO_PESSOA, TS_INCLUSAO_DOCUMENTO, NU_TIPO_DOCUMENTO_CLIENTE FROM AVL.AVLTB033_DOCUMENTO_CLIENTE WHERE NU_CPF_CNPJ_CLIENTE = ?2 AND IC_TIPO_PESSOA = ?3 AND TS_INCLUSAO_DOCUMENTO = ?4 AND NU_TIPO_DOCUMENTO_CLIENTE = ?5", nativeQuery=true)
	void insereDocumentoNota(Long numeroNota, Long cpfCnpj, String tipoPessoa, Date inclusaoDocumento, Long tipoDocumento); 

}
