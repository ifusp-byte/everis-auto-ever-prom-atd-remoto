package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.RoteiroFechamentoNota;

@Repository
public interface RoteiroFechamentoNotaRepository extends JpaRepository<RoteiroFechamentoNota, Long> {
	
	@Query(value="SELECT A.DE_MODELO_ROTEIRO_FECHAMENTO FROM AVL.AVLTB051_ROTEIRO_FCHMO_NOTA A WHERE A.NU_MODELO_NOTA_NEGOCIO = ?1", nativeQuery=true)
	List<Object[]> roteiro(Long idModeloNota);

}
