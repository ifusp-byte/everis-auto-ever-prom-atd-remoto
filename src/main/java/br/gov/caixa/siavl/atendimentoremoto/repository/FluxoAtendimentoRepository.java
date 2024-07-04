package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.FluxoAtendimento;

@Repository
public interface FluxoAtendimentoRepository extends JpaRepository<FluxoAtendimento, Long> {

	@Query("SELECT F FROM FluxoAtendimento F WHERE F.numeroModeloNota = ?1")
	Optional<FluxoAtendimento> possuiFluxo(Long numeroModeloNota);

}
