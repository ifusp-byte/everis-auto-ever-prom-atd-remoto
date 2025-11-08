package com.tjsp.pericia.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TB_ENDERECO_FISICO")
@SequenceGenerator(name = "seqEnderecoFisico", sequenceName = "SQ_TB_ENDERECO_FISICO", allocationSize = 1)
public class EnderecoFisicoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEnderecoFisico")
	@Column(name = "ENDERECO_FISICO_ID")
	private Long id;
	
	private String pais;
	private String estado;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String numero;
	private String codigoPostal;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "LOCAL_FISICO_ID")
	private LocalFisicoModel localFisico;


}
