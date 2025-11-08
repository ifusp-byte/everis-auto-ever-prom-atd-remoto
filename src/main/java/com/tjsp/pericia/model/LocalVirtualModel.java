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
@Table(name = "TB_LOCAL_VIRTUAL")
@SequenceGenerator(name = "seqLocalVirtual", sequenceName = "SQ_TB_LOCAL_VIRTUAL", allocationSize = 1)
public class LocalVirtualModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLocalVirtual")
	@Column(name = "LOCAL_VIRTUAL_ID")
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LOCAL_VIRTUAL_ID")
	private EnderecoVirtualModel enderecoVirtualModel;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "LOCAL_ID")
	private LocalModel local;

}
