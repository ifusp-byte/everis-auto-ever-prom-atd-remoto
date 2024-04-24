package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EquipeAtendimento")
@Table(name = "AVLTB009_EQUIPE_ATENDIMENTO", schema = "AVL")
public class EquipeAtendimento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_EQUIPE_ATENDIMENTO")
	private Long numeroEquipe;
	
	@Column(name = "NU_UNIDADE_EQUIPE")
	private Long numeroUnidadeEquipe;
	
	@Column(name = "IC_EQUIPE_ATIVA")
	private Long equipeAtiva;

}
