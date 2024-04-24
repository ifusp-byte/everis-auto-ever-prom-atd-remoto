package br.gov.caixa.siavl.atendimentoremoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.gov.caixa.siavl.atendimentoremoto.model.EquipeAtendimento;

@Repository
public interface EquipeAtendimentoRepository extends JpaRepository<EquipeAtendimento, Long> {
	
	@Query("SELECT " + "A.numeroEquipe " + "FROM " + "EquipeAtendimento A " 
			+ "WHERE " + "A.equipeAtiva = 1 " + "AND " + "A.numeroUnidadeEquipe = ?1")
	Long findEquipeByUnidade(Long unidade);

}
