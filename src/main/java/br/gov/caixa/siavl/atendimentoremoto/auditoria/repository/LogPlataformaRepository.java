package br.gov.caixa.siavl.atendimentoremoto.auditoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.model.LogPlataforma;

@Repository
public interface LogPlataformaRepository extends JpaRepository<LogPlataforma, Long> {

}
	