package com.empresa.immediate.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Setter;

@Setter
@Entity
@Table(name = "TB_IMMEDIATE_INFO")
@SequenceGenerator(name = "seqImmediateInfo", sequenceName = "SQ_TB_IMMEDIATE_INFO", allocationSize = 1)
public class InfoAdicionalModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqImmediateInfo")
	@Column(name = "IMMEDIATE_INFO_ID", nullable = false)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "valor")
	private String valor;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "IMMEDIATE_ID")
	private ImmediateModel immediate;

	public InfoAdicionalModel() {
		super();
	}

}
