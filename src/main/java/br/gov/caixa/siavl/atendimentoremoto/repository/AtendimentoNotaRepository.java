package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoNota;
import jakarta.transaction.Transactional;

@Repository
public interface AtendimentoNotaRepository extends JpaRepository<AtendimentoNota, Long> {

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO AVL.AVLTB023_ATENDIMENTO_NOTA (NU_NOTA_NEGOCIACAO, NU_MATRICULA_ATENDIMENTO_NOTA, TS_INICIO_ATENDIMENTO_NOTA, TS_CONCLUSAO_ATENDIMENTO_NOTA, IC_TIPO_UNDDE_DESIGNADA_ATNTO, NU_MATRICULA_CONCLUSAO_ATNTO, TS_ENCERRAMENTO_NOTA, NU_EQUIPE_ATENDIMENTO, CO_ACAO_ATENDIMENTO) VALUES(?1, ?2, ?4, ?5, 'A' , ?6, ?5, ?3, ?7)", nativeQuery = true)
	void insereHistoricoAtendimento(Long numeroNota, Long matriculaCriacaoNota, Long equipe,
			Date dataInicioAtendimento, Date dataFimAtendimento, Long matriculaFimAtendimento, String coAcao);

}
