package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;

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

}
