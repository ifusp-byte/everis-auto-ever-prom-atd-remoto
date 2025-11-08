package com.tjsp.pericia.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_PROVEDOR_CONTEUDO_IP")
@SequenceGenerator(name = "seqProvedorConteudoIp", sequenceName = "SQ_TB_PROVEDOR_CONTEUDO_IP", allocationSize = 1)
public class ProvedorConteudoIPModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProvedorConteudoIp")
	@Column(name = "PROVEDOR_CONTEUDO_IP_ID")
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PROVEDOR_CONTEUDO_IP_ID")
	private IPModel ip;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PROVEDOR_CONTEUDO_USUARIO_ID")
	private ProvedorConteudoIPAtividadeModel registroAtividade;

}
