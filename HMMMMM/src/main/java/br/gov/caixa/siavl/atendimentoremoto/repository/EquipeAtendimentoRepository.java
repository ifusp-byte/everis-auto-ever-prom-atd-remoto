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
	
	@Query(value = "SELECT B.NU_EQUIPE_ATENDIMENTO FROM AVL.AVLTB014_UNIDADE_DIGITAL_ATNTO A, AVL.AVLTB009_EQUIPE_ATENDIMENTO B WHERE A.NU_UNIDADE_DIGITAL_ATNTO_VRTL = B.NU_UNIDADE_EQUIPE AND B.IC_EQUIPE_ATIVA = 1 AND ROWNUM = 1 AND A.NU_UNIDADE_SR = ?1", nativeQuery = true)
	Long findEquipeByUnidadeSR(Long unidade);

}
