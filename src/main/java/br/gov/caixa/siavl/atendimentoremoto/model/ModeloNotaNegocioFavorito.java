package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ModeloNotaNegocioFavorito")
@Table(name = "AVLTB064_MODELO_NOTA_FAVORITO", schema = "AVL")
public class ModeloNotaNegocioFavorito implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_MODELO_NOTA_FAVORITA")
	@SequenceGenerator(name = "AVLSQ064_MODELO_NOTA_FAVORITA", sequenceName = "AVLSQ064_MODELO_NOTA_FAVORITA", allocationSize = 1, schema = "AVL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVLSQ064_MODELO_NOTA_FAVORITA")
	private Long numeroModeloNotaFavorita;

	@Column(name = "NU_MATRICULA_FAVORITO")
	private Long matriculaFavorito;

	@Column(name = "DT_ESCOLHA_FAVORITO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEscolhaFavorito;

	@Column(name = "NU_MODELO_NOTA_NEGOCIO")
	private Long numeroModeloNota;

}
