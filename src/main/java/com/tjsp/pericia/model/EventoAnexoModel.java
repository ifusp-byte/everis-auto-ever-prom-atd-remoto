package com.tjsp.pericia.model;

import java.sql.Blob;

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
@Table(name = "TB_EVENTO_ANEXO")
@SequenceGenerator(name = "seqEventoAnexo", sequenceName = "SQ_TB_EVENTO_ANEXO", allocationSize = 1)
public class EventoAnexoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEventoAnexo")
	@Column(name = "EVENTO_ANEXO_ID")
	private Long id;
	
	private Blob arquivoEventoAnexo;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "EVENTO_ID")
	private EventoModel evento;

}
