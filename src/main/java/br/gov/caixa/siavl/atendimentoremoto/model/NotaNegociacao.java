package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "NotaNegociacao")
@Table(name = "AVLTB019_NOTA_NEGOCIACAO", schema = "AVL")
public class NotaNegociacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "NU_NOTA_NEGOCIACAO")
	@SequenceGenerator(name = "AVLSQ011_NU_NOTA_NEGOCIACAO", sequenceName = "AVLSQ011_NU_NOTA_NEGOCIACAO", allocationSize = 1, schema = "AVL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVLSQ011_NU_NOTA_NEGOCIACAO")
	private Long numeroNota;

	@Column(name = "NU_NEGOCIO_AGENCIA_VIRTUAL")
	private Long numeroNegocio;

	@Column(name = "DT_CRIACAO_NOTA")
	private Date dataCriacaoNota;

	@Column(name = "NU_SITUACAO_NOTA")
	private Long numeroSituacaoNota;

	@Column(name = "NU_MODELO_NOTA_NEGOCIO")
	private Long numeroModeloNota;

	@Column(name = "NU_MATRICULA_CRIACAO_NOTA")
	private Long numeroMatriculaCriacaoNota;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "notaNegociacao")
	private NegocioAgenciaVirtual negocioAgenciaVirtual;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_NOTA_NEGOCIACAO", nullable = true, insertable = false, updatable = false)
	private SituacaoNotaNegociacao situacaoNotaNegociacao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_MODELO_NOTA_NEGOCIO", nullable = true, insertable = false, updatable = false)
	private ModeloNotaNegocio modeloNotaNegociacao;
	
	@Column(name = "DT_ULTIMA_MODIFICACAO_NOTA")
	private Date dataModificacaoNota;
	
	@Column(name = "NU_MATRICULA_MODIFICACAO_NOTA")
	private Long numeroMatriculaModificacaoNota;
	
	@Column(name = "QT_ITEM_NOTA_NEGOCIACAO")
	private Long qtdItemNegociacao;
	
	@Column(name = "IC_ORIGEM_NOTA_NEGOCIACAO")
	private Long icOrigemNota;
	
	@Column(name = "NU_EQUIPE_CRIACAO_NOTA")
	private Long numeroEquipe;
	
	@Column(name = "VR_SOLICITADO_NOTA_NEGOCIACAO")
	private Double valorSolicitadoNota;

}
