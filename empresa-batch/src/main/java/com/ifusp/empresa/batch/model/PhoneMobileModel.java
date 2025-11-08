package com.ifusp.empresa.batch.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "TB_PHONE_MOBILE")
@SequenceGenerator(name = "seqPhoneMobile", sequenceName = "SQ_TB_PHONE_MOBILE", allocationSize = 1)
public class PhoneMobileModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCustomer")
	@Column(name = "PHONE_MOBILE_ID")
	private Long id;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "CUSTOMER_ID")
	private CustomerModel customer;

	@Column(name = "NUMERO")
	private String numero;

	@OneToMany(mappedBy = "phoneMobile", cascade = CascadeType.PERSIST)
	private List<WhatsAppModel> whatsapps; // new ArrayList<WhatsAppModel>();

	@OneToMany(mappedBy = "phoneMobile", cascade = CascadeType.PERSIST)
	private List<TelegramModel> telegrams; // new ArrayList<TelegramModel>();

	@OneToMany(mappedBy = "phoneMobile", cascade = CascadeType.PERSIST)
	private List<UspModel> usps; // new ArrayList<UspModel>();

	@OneToMany(mappedBy = "phoneMobile", cascade = CascadeType.PERSIST)
	private List<EmpresaModel> empresas; // new ArrayList<EmpresaModel>();

	public PhoneMobileModel() {
		super();
	}

	public PhoneMobileModel(Long id, CustomerModel customer, String numero, List<WhatsAppModel> whatsapps,
			List<TelegramModel> telegrams, List<UspModel> usps, List<EmpresaModel> empresas) {
		super();
		this.id = id;
		this.customer = customer;
		this.numero = numero;
		this.whatsapps = whatsapps;
		this.telegrams = telegrams;
		this.usps = usps;
		this.empresas = empresas;
	}

}
