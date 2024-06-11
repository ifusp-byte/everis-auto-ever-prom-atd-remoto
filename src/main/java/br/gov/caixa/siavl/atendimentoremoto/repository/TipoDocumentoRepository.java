package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.TipoDocumentoCliente;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoCliente, Long> {

	@Query(value = "SELECT * FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A ORDER BY A.NO_TIPO_DOCUMENTO_CLIENTE ASC", nativeQuery = true)
	List<TipoDocumentoCliente> tipoDocumento();
}
