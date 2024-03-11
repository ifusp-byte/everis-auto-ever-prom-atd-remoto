package br.gov.caixa.siavl.atendimentoremoto.auditoria.model;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LogPlataforma")
@Table(name = "AVLTB078_LOG_PLATAFORMA", schema = "AVL")
public class LogPlataforma implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_LOG_PLATAFORMA")
	@SequenceGenerator(name = "AVLSQ078_NU_LOG_PLATAFORMA", sequenceName = "AVLSQ078_NU_LOG_PLATAFORMA", allocationSize = 1, schema = "AVL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVLSQ078_NU_LOG_PLATAFORMA")
	private Long idLogPlataforma;

	@Column(name = "NU_TRANSACAO_SISTEMA")
	private Long transacaoSistema;

	@Column(name = "NU_MATRICULA_ACAO")
	private Long matriculaAtendente;

	@Column(name = "TS_CRIACAO_LOG_PLATAFORMA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacaoLogPlataforma;

	@Column(name = "CO_ENDERECO_IP_USUARIO")
	private String ipUsuario;

	@Column(name = "CO_VERSAO_SSTMA_AGNCA_VIRTUAL")
	private String versaoSistemaAgenciaVirtual;

	@Column(name = "IC_TIPO_PESSOA", columnDefinition = "CHAR(2)")
	private String tipoPessoa;

	@Column(name = "NU_ANO_MES_REFERENCIA")
	private Long anoMesReferencia;

	@Column(name = "DE_LOG_PLATAFORMA")
	private Clob jsonLogPlataforma;
	
	@Column(name = "NU_CPF_CNPJ_CLIENTE")
	private Long cpfCnpj;

}
