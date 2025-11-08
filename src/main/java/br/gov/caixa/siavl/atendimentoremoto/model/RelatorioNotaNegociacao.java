package br.gov.caixa.siavl.atendimentoremoto.model;

import java.sql.Clob;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RelatorioNotaNegociacao")
@Table(name = "AVLTB065_RLTRO_NOTA_NEGOCIACAO", schema = "AVL")
public class RelatorioNotaNegociacao {

	@Id
	@Column(name = "NU_RELATORIO_NOTA_NEGOCIACAO")
	@SequenceGenerator(name = "AVLSQ011_NU_NOTA_NEGOCIACAO", sequenceName = "AVLSQ011_NU_NOTA_NEGOCIACAO", allocationSize = 1, schema = "AVL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVLSQ011_NU_NOTA_NEGOCIACAO")
	private Long numeroRelatorioNota;

	@Column(name = "NU_EQUIPE_ATENDIMENTO")
	private Long numeroEquipe;

	@Column(name = "DE_RELATORIO_NOTA_NEGOCIACAO")
	private Clob relatorioNota;

	@Column(name = "NU_NOTA_NEGOCIACAO")
	private Long numeroNota;

	@Column(name = "NU_PROTOCOLO_ATNTO_CLNTE")
	private Long numeroProtocolo;

	@Column(name = "NO_CLIENTE")
	private String nomeCliente;

	@Column(name = "NU_MATRICULA_CRIACAO_NOTA")
	private Long matriculaAtendente;

	@Column(name = "NU_MATRICULA_ATENDIMENTO_NOTA")
	private Long matriculaAlteracao;

	@Column(name = "NU_CPF_CLIENTE")
	private Long cpf;

	@Column(name = "NU_CNPJ_CLIENTE")
	private Long cnpj;

	@Column(name = "NO_PRODUTO")
	private String produto;

	@Column(name = "NU_SITUACAO_NOTA")
	private Long situacaoNota;

	@Column(name = "DT_CRIACAO_NOTA")
	private Date dataCriacaoNota;

	@Column(name = "TS_INICIO_ATENDIMENTO_NOTA")
	private Date inicioAtendimentoNota;

	@Column(name = "DT_PRAZO_VALIDADE")
	private Date dataValidade;

	@Column(name = "DE_ACAO_PRODUTO")
	private String acaoProduto;

}
