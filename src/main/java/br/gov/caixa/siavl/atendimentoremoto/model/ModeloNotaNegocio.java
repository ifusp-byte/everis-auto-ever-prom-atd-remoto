package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Entity(name = "ModeloNotaNegocio")
@Table(name = "AVLTB005_MODELO_NOTA_NEGOCIO", schema = "AVL")
public class ModeloNotaNegocio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_MODELO_NOTA_NEGOCIO")
	private Long numeroModeloNota;

	@Column(name = "NU_ACAO_PRODUTO")
	private Long numeroAcao;

	@Column(name = "IC_CONTRATACAO_AUTOMATICA")
	private char contratacaoAutomatica;

	@Column(name = "IC_SITUACAO_MODELO_NOTA")
	private char situacaoModeloNota;

	@Column(name = "IC_MODELO_NOTA_ATIVO")
	private Long modeloNotaAtivo;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "modeloNotaNegociacao")
	private NotaNegociacao notaNegociacao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_ACAO_PRODUTO", nullable = true, insertable = false, updatable = false)
	private AcaoProduto acaoProduto;

}
