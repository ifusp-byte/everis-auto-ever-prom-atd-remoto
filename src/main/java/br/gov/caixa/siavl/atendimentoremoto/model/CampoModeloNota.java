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
@Entity(name = "CampoModeloNota")
@Table(name = "AVLTB017_CAMPO_MODELO_NOTA", schema = "AVL")
public class CampoModeloNota {

	@Id
	@Column(name = "NU_CAMPO_MODELO_NOTA")
	private Long numeroCampoModeloNota;

	@Column(name = "NO_CAMPO_MODELO_NOTA")
	private String nomeCampoModeloNota;

	@Column(name = "IC_CAMPO_OBRIGATORIO")
	private Long flagbrigatorio;

	@Column(name = "IC_TIPO_DADO_CAMPO")
	private String flagTipoDado;

	@Column(name = "QT_CARACTER_CAMPO")
	private Long quantidadeCaracteres;

	@Column(name = "CO_MASCARA_CAMPO")
	private String mascaraCampo;
}
