package br.gov.caixa.siavl.atendimentoremoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoCliente;

@Repository
public interface DocumentoClienteRepository extends JpaRepository<DocumentoCliente, Long> {

}
