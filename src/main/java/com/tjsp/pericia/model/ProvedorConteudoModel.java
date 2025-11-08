package com.tjsp.pericia.model;

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
@Table(name = "TB_PROVEDOR_CONTEUDO")
@SequenceGenerator(name = "seqProvedorConteudo", sequenceName = "SQ_TB_PROVEDOR_CONTEUDO", allocationSize = 1)
public class ProvedorConteudoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProvedorConteudo")
	@Column(name = "PROVEDOR_CONTEUDO_ID")
	private Long id;	
	
	private String nomeProvedor;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PROVEDOR_CONTEUDO_ID")
	private Set<ProvedorConteudoUsuarioModel> usuarios;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ENDERECO_VIRTUAL_ID")
	private EnderecoVirtualModel enderecoVirtual;

	

}
