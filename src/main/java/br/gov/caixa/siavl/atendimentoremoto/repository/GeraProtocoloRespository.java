package br.gov.caixa.siavl.atendimentoremoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;

@Repository
public interface GeraProtocoloRespository extends JpaRepository<AtendimentoCliente, Long> {

}
