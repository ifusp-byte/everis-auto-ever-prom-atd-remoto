package br.gov.caixa.siavl.atendimentoremoto.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AtendimentoNota")
@Table(name = "AVLTB023_ATENDIMENTO_NOTA", schema = "AVL")
public class AtendimentoNota {

	@Id
	@Column(name = "NU_NOTA_NEGOCIACAO")
	private Long numeroNota;

	@Column(name = "NU_MATRICULA_ATENDIMENTO_NOTA")
	private Long matriculaAtendente;

	@Column(name = "TS_INICIO_ATENDIMENTO_NOTA")
	private Date dtInicioAtendimentoNota;

	@Column(name = "IC_TIPO_UNDDE_DESIGNADA_ATNTO")
	private char unidadeDesignada;

	@Column(name = "TS_CONCLUSAO_ATENDIMENTO_NOTA")
	private Date dtConclusaoAtendimentoNota;

	@Column(name = "NU_MATRICULA_CONCLUSAO_ATNTO")
	private Long matriculaAtendenteConclusao;

	@Column(name = "NU_EQUIPE_ATENDIMENTO")
	private Long numeroEquipe;
	
	@Column(name = "CO_ACAO_ATENDIMENTO", columnDefinition = "CHAR(2)")
	private String acaoAtendimento;

}
