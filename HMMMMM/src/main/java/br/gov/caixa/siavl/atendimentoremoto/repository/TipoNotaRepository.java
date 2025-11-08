package br.gov.caixa.siavl.atendimentoremoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.TipoNota;

@Repository
public interface TipoNotaRepository extends JpaRepository<TipoNota, Long> {

}
