package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
