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
@Table(name = "TB_WHATSAPP")
@SequenceGenerator(name = "seqWhatsApp", sequenceName = "SQ_TB_WHATSAPP", allocationSize = 1)
public class WhatsAppModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCustomer")
	@Column(name = "WHATSAPP_ID")
	private Long id;

	@Column(name = "NUMERO_WHATSAPP_CNPJ")
	private String numeroWhatsAppCnpj;

	@Column(name = "NUMERO_WHATSAPP_CPF")
	private String numeroWhatsAppCpf;

	@Column(name = "NUMERO_WHATSAPP_NOME")
	private String numeroWhatsAppNome;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "PHONE_MOBILE_ID")
	private PhoneMobileModel phoneMobile;

	public WhatsAppModel() {
		super();
	}

	public WhatsAppModel(Long id, String numeroWhatsAppCnpj, String numeroWhatsAppCpf, String numeroWhatsAppNome,
			PhoneMobileModel phoneMobile) {
		super();
		this.id = id;
		this.numeroWhatsAppCnpj = numeroWhatsAppCnpj;
		this.numeroWhatsAppCpf = numeroWhatsAppCpf;
		this.numeroWhatsAppNome = numeroWhatsAppNome;
		this.phoneMobile = phoneMobile;
	}

}
