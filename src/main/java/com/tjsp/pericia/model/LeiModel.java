package com.tjsp.pericia.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_LEI")
@SequenceGenerator(name = "seqLei", sequenceName = "SQ_TB_LEI", allocationSize = 1)
public class LeiModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLei")
	@Column(name = "LEI_ID")
	private Long id;
	private String lei_nr;	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LEI_ID")
	private List<CrimeModel> crimes;
}
