package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "atendimentoNegocio")
	private AtendimentoCliente atendimentoCliente;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_NEGOCIO_AGENCIA_VIRTUAL", nullable = true, insertable = false, updatable = false)
	private NegocioAgenciaVirtual negocioAgenciaVirtual;

}
