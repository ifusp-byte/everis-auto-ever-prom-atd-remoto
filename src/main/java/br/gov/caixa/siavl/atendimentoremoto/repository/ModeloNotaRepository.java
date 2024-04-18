package br.gov.caixa.siavl.atendimentoremoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;

@Repository
public interface ModeloNotaRepository extends JpaRepository<ModeloNotaNegocio, Long> {
	

}
