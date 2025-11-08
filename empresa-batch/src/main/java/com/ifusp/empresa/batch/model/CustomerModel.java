package com.ifusp.empresa.batch.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "TB_CUSTOMER")
@SequenceGenerator(name = "seqCustomer", sequenceName = "SQ_TB_CUSTOMER", allocationSize = 1)
public class CustomerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCustomer")
	@Column(name = "CUSTOMER_ID")
	private Long id;

	@Column(name = "NUMERO_CPF")
	private String numeroCpf;

	@Column(name = "NUMERO_PASSAPORTE")
	private String numeroPassaporte;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
	private List<PhoneMobileModel> phoneMobiles; // = new ArrayList<PhoneMobileModel>();

	public CustomerModel() {
		super();
	}

	public CustomerModel(Long id, String numeroCpf, String numeroPassaporte, List<PhoneMobileModel> phoneMobiles) {
		super();
		this.id = id;
		this.numeroCpf = numeroCpf;
		this.numeroPassaporte = numeroPassaporte;
		this.phoneMobiles = phoneMobiles;
	}

}
