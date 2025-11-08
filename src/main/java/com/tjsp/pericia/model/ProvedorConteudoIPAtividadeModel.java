package com.tjsp.pericia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_PROVEDOR_CONTEUDO_IP_ATIVIDADE")
@SequenceGenerator(name = "seqProvedorConteudoIpAtividade", sequenceName = "SQ_TB_PROVEDOR_CONTEUDO_IP_ATIVIDADE", allocationSize = 1)
public class ProvedorConteudoIPAtividadeModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProvedorConteudoIpAtividade")
	@Column(name = "PROVEDOR_CONTEUDO_IP_ATIVIDADE_ID")
	private Long id;
	
	private String tipoAtividade;
	
	private Date login;
	
	private Date logout;
	
	private Date timestampNaoEspecificado;
	
	//intervalo de execucao

}
