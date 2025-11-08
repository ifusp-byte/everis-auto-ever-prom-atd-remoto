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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_PROVEDOR_CONTEUDO_USUARIO")
@SequenceGenerator(name = "seqProvedorConteudoUsuario", sequenceName = "SQ_TB_PROVEDOR_CONTEUDO_USUARIO", allocationSize = 1)
public class ProvedorConteudoUsuarioModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProvedorConteudoUsuario")
	@Column(name = "PROVEDOR_CONTEUDO_USUARIO_ID")
	private Long id;
	
	private String nome;
	private String nickNameUsuario;
	private String telefone;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PROVEDOR_CONTEUDO_USUARIO_ID")
	private Set<ProvedorConteudoIPModel> ips;

}
