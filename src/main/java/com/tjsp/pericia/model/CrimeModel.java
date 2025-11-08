package com.tjsp.pericia.model;

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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_CRIME")
@SequenceGenerator(name = "seqCrime", sequenceName = "SQ_TB_CRIME", allocationSize = 1)
public class CrimeModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCrime")
	@Column(name = "CRIME_ID")
	private Long id;
	
	private String art_nr;
	private String art_caput;
	private String art_paragrafo;
	private String art_inciso;
	private String art_alinea;
	private String pescricao;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "EVENTO_ID")
	private EventoModel evento;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "LEI_ID")
	private LeiModel lei;
	

}
