package br.gov.caixa.siavl.atendimentoremoto.model;

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
@Entity(name = "CampoModelo")
@Table(name = "AVLTB018_CAMPO_MODELO", schema = "AVL")
public class CampoModelo {

	@Id
	@Column(name = "NU_CAMPO_MODELO_NOTA")
	private Long numeroCampoModeloNota;
	
	@Column(name = "NU_MODELO_NOTA_NEGOCIO")
	private Long numeroModeloNota;

}
