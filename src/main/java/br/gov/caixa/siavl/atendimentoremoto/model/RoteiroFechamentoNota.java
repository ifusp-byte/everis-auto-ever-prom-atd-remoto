package br.gov.caixa.siavl.atendimentoremoto.model;

import java.sql.Clob;

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
@Entity(name = "RoteiroFechamentoNota")
@Table(name = "AVLTB051_ROTEIRO_FCHMO_NOTA", schema = "AVL")
public class RoteiroFechamentoNota {

	@Id
	@Column(name = "NU_MODELO_NOTA_NEGOCIO")
	private Long numeroModeloNota;
	
	@Column(name = "DE_MODELO_ROTEIRO_FECHAMENTO")
	private Clob roteiroFechamento;

}
