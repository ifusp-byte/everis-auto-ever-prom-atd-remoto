package br.gov.caixa.siavl.atendimentoremoto.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoNegocio;

@Repository
public interface AtendimentoNegocioRepository extends JpaRepository<AtendimentoNegocio, Long> {
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO AVL.AVLTB003_ATENDIMENTO_NEGOCIO (NU_PROTOCOLO_ATNTO_CLNTE, NU_NEGOCIO_AGENCIA_VIRTUAL) VALUES (?1, ?2)", nativeQuery=true)
	void salvaAtendimentoNegocio(Long numeroProtocolo, Long numeroAgenciaVirtual); 

}
