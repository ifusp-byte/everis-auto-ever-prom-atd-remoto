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
@Table(name = "TB_EMPRESA")
@SequenceGenerator(name = "seqEmpresa", sequenceName = "SQ_TB_EMPRESA", allocationSize = 1)
public class EmpresaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCustomer")
	@Column(name = "EMPRESA_ID")
	private Long id;

	@Column(name = "NUMERO_CNPJ")
	private String numeroCnpj;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "PHONE_MOBILE_ID")
	private PhoneMobileModel phoneMobile;

	public EmpresaModel() {
		super();
	}

	public EmpresaModel(Long id, String numeroCnpj, PhoneMobileModel phoneMobile) {
		super();
		this.id = id;
		this.numeroCnpj = numeroCnpj;
		this.phoneMobile = phoneMobile;
	}

}
