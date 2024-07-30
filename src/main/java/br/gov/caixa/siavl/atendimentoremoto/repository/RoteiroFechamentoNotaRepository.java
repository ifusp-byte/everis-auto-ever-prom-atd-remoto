package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.sql.Clob;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.RoteiroFechamentoNota;

@Repository
public interface RoteiroFechamentoNotaRepository extends JpaRepository<RoteiroFechamentoNota, Long> {
	
	@Query(value="SELECT A.roteiroFechamento FROM RoteiroFechamentoNota A WHERE A.numeroModeloNota = ?1")
	Optional<List<Clob>> roteiro(Long idModeloNota);

}
