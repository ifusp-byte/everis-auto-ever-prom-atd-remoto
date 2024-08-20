package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;
import java.util.Date;

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
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
@Builder(toBuilder = true)
@Entity(name = "AtendimentoCliente")
@Table(name = "AVLTB001_ATENDIMENTO_CLIENTE", schema = "AVL")
public class AtendimentoCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_PROTOCOLO_ATNTO_CLNTE")
	@SequenceGenerator(name = "AVLSQ001_NU_PROTOCOLO", sequenceName = "AVLSQ001_NU_PROTOCOLO", allocationSize = 1, schema = "AVL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVLSQ001_NU_PROTOCOLO")
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

	@Column(name = "IC_CANAL_ATENDIMENTO")
	private char canalAtendimento;

	@Column(name = "DT_INICIAL_ATENDIMENTO_CLNTE")
	private Date dataInicialAtendimento;

	@Column(name = "DT_FINAL_ATENDIMENTO_CLNTE")
	private Date dataFinalAtendimento;

	@Column(name = "IC_SITUACAO_IDNFO_POSITIVA")
	private Long situacaoIdPositiva = 0L;

	@Column(name = "IC_VALIDACAO_TOKEN_ATENDIMENTO")
	private Long validacaoTokenAtendimento = 0L;

	@Column(name = "DT_INICIAL_CONTATO_CLNTE")
	private Date dataContatoCliente;

	@Column(name = "NU_UNIDADE_ATENDIMENTO")
	private Long numeroUnidade;

	@Column(name = "DT_IDNFO_POSITIVA_CLIENTE")
	private Date dataIdentificacaoPositiva;

	@Column(name = "TS_VALIDACAO_IDNFO_POSITIVA")
	private Date dataValidacaoPositiva;

	@Column(name = "DE_SITUACAO_IDNFO_POSITIVA")
	private String descricaoIdentificacaoPositiva;

}
