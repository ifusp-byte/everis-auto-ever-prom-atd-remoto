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
@Table(name = "TB_TELEGRAM")
@SequenceGenerator(name = "seqTelegram", sequenceName = "SQ_TB_TELEGRAM", allocationSize = 1)
public class TelegramModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCustomer")
	@Column(name = "TELEGRAM_ID")
	private Long id;

	@Column(name = "NUMERO_TELEGRAM_CNPJ")
	private String numeroTelegramCnpj;

	@Column(name = "NUMERO_TELEGRAM_CPF")
	private String numeroTelegramCpf;

	@Column(name = "NUMERO_TELEGRAM_NOME")
	private String numeroTelegramNome;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "PHONE_MOBILE_ID")
	private PhoneMobileModel phoneMobile;

	public TelegramModel() {
		super();
	}

	public TelegramModel(Long id, String numeroTelegramCnpj, String numeroTelegramCpf, String numeroTelegramNome,
			PhoneMobileModel phoneMobile) {
		super();
		this.id = id;
		this.numeroTelegramCnpj = numeroTelegramCnpj;
		this.numeroTelegramCpf = numeroTelegramCpf;
		this.numeroTelegramNome = numeroTelegramNome;
		this.phoneMobile = phoneMobile;
	}

}
