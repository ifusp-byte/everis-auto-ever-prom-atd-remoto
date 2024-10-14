package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Entity(name = "AtendimentoNegocio")
@Table(name = "AVLTB003_ATENDIMENTO_NEGOCIO", schema = "AVL")
public class AtendimentoNegocio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_PROTOCOLO_ATNTO_CLNTE")
	private Long numeroProtocolo;

	@Column(name = "NU_NEGOCIO_AGENCIA_VIRTUAL")
	private Long numeroNegocio;

	/*
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "atendimentoNegocio")
	private AtendimentoCliente atendimentoCliente;
	*/

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_NEGOCIO_AGENCIA_VIRTUAL", nullable = true, insertable = false, updatable = false)
	private NegocioAgenciaVirtual negocioAgenciaVirtual;

}
