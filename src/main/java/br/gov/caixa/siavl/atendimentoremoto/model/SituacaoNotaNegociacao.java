package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SituacaoNotaNegociacao")
@Table(name = "AVLTB007_SITUACAO_NOTA_NGCAO", schema = "AVL")
public class SituacaoNotaNegociacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_SITUACAO_NOTA")
	private Long numeroSituacaoNota;

	@Column(name = "DE_SITUACAO_NOTA")
	private String descricao;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "situacaoNotaNegociacao")
	private NotaNegociacao notaNegociacao;

}
