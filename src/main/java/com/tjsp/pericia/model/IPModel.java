package com.tjsp.pericia.model;

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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_IP")
@SequenceGenerator(name = "seqIp", sequenceName = "SQ_TB_IP", allocationSize = 1)
public class IPModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqIp")
	@Column(name = "IP_ID")
	private Long id;
	
	private String numero;
	
	private String country;
	private String state;
	private String city;
	private String zipPostal;
	private String latitude;
	private String longitude;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PROVEDOR_CONTEUDO_IP_ID")
	private ProvedorConteudoIPModel provedorConteudoIPModel;
	

}
