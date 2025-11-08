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
@Table(name = "TB_LOCAL")
@SequenceGenerator(name = "seqLocal", sequenceName = "SQ_TB_LOCAL", allocationSize = 1)
public class LocalModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLocal")
	@Column(name = "LOCAL_ID")
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LOCAL_ID")
	private LocalFisicoModel localFisicoModel;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LOCAL_ID")
	private LocalVirtualModel localVirtualModel;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "EVENTO_ID")
	private EventoModel evento;

}
