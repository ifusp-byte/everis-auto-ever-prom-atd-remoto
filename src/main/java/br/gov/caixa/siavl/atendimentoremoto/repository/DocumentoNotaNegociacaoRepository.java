package br.gov.caixa.siavl.atendimentoremoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoNotaNegociacao;

@Repository
public interface DocumentoNotaNegociacaoRepository extends JpaRepository<DocumentoNotaNegociacao, Long> {
	
}
