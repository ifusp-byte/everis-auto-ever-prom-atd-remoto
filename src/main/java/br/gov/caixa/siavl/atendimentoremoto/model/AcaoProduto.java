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
@Entity(name = "AcaoProduto")
@Table(name = "AVLTB020_ACAO_PRODUTO", schema = "AVL")
public class AcaoProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_ACAO_PRODUTO")
	private Long numeroAcao;

	@Column(name = "DE_ACAO_PRODUTO")
	private String descricao;

	@Column(name = "IC_ATIVO")
	private Long acaoProdutoAtivo;

	/*
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "acaoProduto")
	private ModeloNotaNegocio modeloNotaNegociacao;
	*/

}
