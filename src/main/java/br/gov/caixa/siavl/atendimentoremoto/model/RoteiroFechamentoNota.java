package br.gov.caixa.siavl.atendimentoremoto.model;

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
@Entity(name = "RoteiroFechamentoNota")
@Table(name = "DE_MODELO_ROTEIRO_FECHAMENTO", schema = "AVL")
public class RoteiroFechamentoNota {

	@Id
	@Column(name = "NU_MODELO_NOTA_NEGOCIO")
	private Long numeroModeloNota;

}
