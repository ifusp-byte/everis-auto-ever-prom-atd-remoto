package com.tjsp.pericia.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_EVENTO")
@SequenceGenerator(name = "seqEvento", sequenceName = "SQ_TB_EVENTO", allocationSize = 1)
public class EventoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEvento")
	@Column(name = "EVENTO_ID")
	private Long id;
	
	private String descricao;
	private Date data;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "EVENTO_ID")
	private LocalModel local;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "EVENTO_ID")
	private Set<EventoAnexoModel> anexos;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "EVENTO_ID")
	private Set<CrimeModel> crimes;
	

}
