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
@Entity(name = "AtendimentoCliente")
@Table(name = "AVLTB001_ATENDIMENTO_CLIENTE", schema = "AVL")
public class AtendimentoCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_PROTOCOLO_ATNTO_CLNTE")
	private Long numeroProtocolo;

	@Column(name = "NU_MATRICULA_ATENDENTE")
	private Long matriculaAtendente;

	@Column(name = "NO_CLIENTE")
	private String nomeCliente;

	@Column(name = "NU_CPF_CLIENTE")
	private Long cpfCliente;

	@Column(name = "NU_CNPJ_CLIENTE")
	private Long cnpjCliente;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NU_PROTOCOLO_ATNTO_CLNTE", nullable = true, insertable = false, updatable = false)
	private AtendimentoNegocio atendimentoNegocio;

}
