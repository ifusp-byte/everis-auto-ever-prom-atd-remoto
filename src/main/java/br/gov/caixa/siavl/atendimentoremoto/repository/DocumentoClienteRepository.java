package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoCliente;

@Repository
public interface DocumentoClienteRepository extends JpaRepository<DocumentoCliente, Long> {

	@Query(value = "SELECT A.NU_TIPO_DOCUMENTO_CLIENTE, A.NO_ANEXO FROM AVL.AVLTB033_DOCUMENTO_CLIENTE A, AVL.AVLTB034_DCMNTO_NOTA_NGCCO B WHERE B.NU_NOTA_NEGOCIACAO = ?1", nativeQuery = true)
	Optional<List<Object[]>> numeroNomeDocumento(Long numeroNota);

}
