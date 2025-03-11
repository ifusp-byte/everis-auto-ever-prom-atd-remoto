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
@Entity(name = "PendenciaAtendimentoNota")
@Table(name = "AVLTB027_PENDENCIA_ATNTO_NOTA", schema = "AVL")
public class PendenciaAtendimentoNota {

	@Id
	@Column(name = "NU_NOTA_NEGOCIACAO_CRIACAO")
	private Long numeroNota;

	@Column(name = "NU_MATRICULA_ATENDIMENTO_CRIAC")
	private Long matriculaAtendente;

	@Column(name = "TS_INICIO_ATENDIMENTO_CRIACAO")
	private Date dtInicioAtendimentoNota;

	@Column(name = "TS_INCLUSAO_PENDENCIA_ATNTO")
	private Date dtInclusaoPendencia;

	@Column(name = "NU_TIPO_PENDENCIA")
	private Long tipoPendencia;

}
