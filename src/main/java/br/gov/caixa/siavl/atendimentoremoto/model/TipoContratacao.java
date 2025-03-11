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
@Entity(name = "TipoContratacao")
@Table(name = "AVLTB084_TIPO_FRMLO_CNTCO", schema = "AVL")
public class TipoContratacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_FORMALIZACAO_CONTRATACAO")
	private Long numeroFormalizacao;

	@Column(name = "DE_FORMALIZACAO_CONTRATACAO")
	private String descricaoFormalizacao;

	@Column(name = "IC_FORMALIZACAO_CNTCO_ATIVO", columnDefinition = "CHAR(1)")
	private String ativo;
}
