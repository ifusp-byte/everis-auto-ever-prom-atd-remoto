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
@Entity(name = "NegocioAgenciaVirtual")
@Table(name = "AVLTB002_NEGOCIO_AGNCA_VIRTUAL", schema = "AVL")
public class NegocioAgenciaVirtual implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "NU_NEGOCIO_AGENCIA_VIRTUAL")
	@SequenceGenerator(name = "AVLSQ002_NU_NEGOCIO", sequenceName = "AVLSQ002_NU_NEGOCIO", allocationSize = 1, schema = "AVL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVLSQ002_NU_NEGOCIO")
	private Long numeroNegocio;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "negocioAgenciaVirtual")
	private AtendimentoNegocio atendimentoNegocio;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_NEGOCIO_AGENCIA_VIRTUAL", nullable = true, insertable = false, updatable = false)
	private NotaNegociacao notaNegociacao;
	
	@Column(name = "IC_SITUACAO_NEGOCIO")
	private char situacaoNegocio;
	
	@Column(name = "DT_CRIACAO_NEGOCIO")
	private Date dataCriacaoNegocio;

}
