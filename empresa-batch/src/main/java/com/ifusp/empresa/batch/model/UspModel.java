package com.ifusp.empresa.batch.model;


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

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "TB_USP")
@SequenceGenerator(name = "seqUsp", sequenceName = "SQ_TB_USP", allocationSize = 1)
public class UspModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCustomer")
	@Column(name = "USP_ID")
	private Long id;

	@Column(name = "NUMERO_USP")
	private String numeroUsp;

	@Column(name = "EMAIL_USP")
	private String emailUsp;

	@Column(name = "ID_EDISCIPLINAS_USP")
	private String idEdisciplinasUsp;

	@Column(name = "SENHA_USP")
	private String senhaUsp;

	@Column(name = "SENHA_JUPITER_WEB")
	private String senhaJupiterWeb;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "PHONE_MOBILE_ID")
	private PhoneMobileModel phoneMobile;

	public UspModel() {
		super();
	}

	public UspModel(Long id, String numeroUsp, String emailUsp, String idEdisciplinasUsp, String senhaUsp,
			String senhaJupiterWeb, PhoneMobileModel phoneMobile) {
		super();
		this.id = id;
		this.numeroUsp = numeroUsp;
		this.emailUsp = emailUsp;
		this.idEdisciplinasUsp = idEdisciplinasUsp;
		this.senhaUsp = senhaUsp;
		this.senhaJupiterWeb = senhaJupiterWeb;
		this.phoneMobile = phoneMobile;
	}

}
