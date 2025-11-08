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
@Table(name = "TB_ENDERECO_VIRTUAL")
@SequenceGenerator(name = "seqEnderecoVirtual", sequenceName = "SQ_TB_ENDERECO_VIRTUAL", allocationSize = 1)
public class EnderecoVirtualModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEnderecoVirtual")
	@Column(name = "ENDERECO_VIRTUAL_ID")
	private Long id;
	
	private String link;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ENDERECO_VIRTUAL_ID")
	private ProvedorConteudoModel provedorConteudoModel;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "LOCAL_VIRTUAL_ID")
	private LocalVirtualModel localVirtual;

}
