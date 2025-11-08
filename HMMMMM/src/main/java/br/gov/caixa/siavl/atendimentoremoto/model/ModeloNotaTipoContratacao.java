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
@Entity(name = "ModeloNotaTipoContratacao")
@Table(name = "AVLTB085_FRMLO_CNTCO_MODELO", schema = "AVL")
public class ModeloNotaTipoContratacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_MODELO_NOTA_NEGOCIO")
	private Long numeroModeloNota;

	@Column(name = "NU_FORMALIZACAO_CONTRATACAO")
	private Long numeroFormalizacao;

}
