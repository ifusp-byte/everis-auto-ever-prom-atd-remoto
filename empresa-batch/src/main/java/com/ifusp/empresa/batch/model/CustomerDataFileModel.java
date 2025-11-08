package com.ifusp.empresa.batch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "TB_CUSTOMER_DATA_FILE")
@SequenceGenerator(name = "seqCustomerDataFile", sequenceName = "SQ_TB_CUSTOMER_DATA_FILE", allocationSize = 1)
public class CustomerDataFileModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCustomerDataFile")
	@Column(name = "ID")
	private Long id;

	@Column(name = "NUMERO_USP")
	private String numeroUsp;

	@Column(name = "EMAIL_USP")
	private String emailUsp;

	@Column(name = "ID_EDISCIPLINAS_USP")
	private String idEdisciplinasUsp;

	@Column(name = "SENHA_USP")
	private String senhaUsp;

	@Column(name = "NUMERO_CPF")
	private String numeroCpf;

	@Column(name = "NUMERO_PASSAPORTE")
	private String numeroPassaporte;

	@Column(name = "SENHA_JUPITER_WEB")
	private String senhaJupiterWeb;

	@Column(name = "NUMERO_TELEGRAM_CNPJ")
	private String numeroTelegramCnpj;

	@Column(name = "NUMERO_TELEGRAM_CPF")
	private String numeroTelegramCpf;

	@Column(name = "NUMERO_TELEGRAM_NOME")
	private String numeroTelegramNome;

	@Column(name = "NUMERO_WHATSAPP_CNPJ")
	private String numeroWhatsAppCnpj;

	@Column(name = "NUMERO_WHATSAPP_CPF")
	private String numeroWhatsAppCpf;

	@Column(name = "NUMERO_WHATSAPP_NOME")
	private String numeroWhatsAppNome;

	public CustomerDataFileModel(Long id, String numeroUsp, String emailUsp, String idEdisciplinasUsp, String senhaUsp,
			String numeroCpf, String numeroPassaporte, String senhaJupiterWeb, String numeroTelegramCnpj,
			String numeroTelegramCpf, String numeroTelegramNome, String numeroWhatsAppCnpj, String numeroWhatsAppCpf,
			String numeroWhatsAppNome) {
		super();
		this.id = id;
		this.numeroUsp = numeroUsp;
		this.emailUsp = emailUsp;
		this.idEdisciplinasUsp = idEdisciplinasUsp;
		this.senhaUsp = senhaUsp;
		this.numeroCpf = numeroCpf;
		this.numeroPassaporte = numeroPassaporte;
		this.senhaJupiterWeb = senhaJupiterWeb;
		this.numeroTelegramCnpj = numeroTelegramCnpj;
		this.numeroTelegramCpf = numeroTelegramCpf;
		this.numeroTelegramNome = numeroTelegramNome;
		this.numeroWhatsAppCnpj = numeroWhatsAppCnpj;
		this.numeroWhatsAppCpf = numeroWhatsAppCpf;
		this.numeroWhatsAppNome = numeroWhatsAppNome;
	}

	public CustomerDataFileModel(String numeroUsp, String emailUsp, String idEdisciplinasUsp, String senhaUsp,
			String numeroCpf, String numeroPassaporte, String senhaJupiterWeb, String numeroTelegramCnpj,
			String numeroTelegramCpf, String numeroTelegramNome, String numeroWhatsAppCnpj, String numeroWhatsAppCpf,
			String numeroWhatsAppNome) {
		super();
		this.numeroUsp = numeroUsp;
		this.emailUsp = emailUsp;
		this.idEdisciplinasUsp = idEdisciplinasUsp;
		this.senhaUsp = senhaUsp;
		this.numeroCpf = numeroCpf;
		this.numeroPassaporte = numeroPassaporte;
		this.senhaJupiterWeb = senhaJupiterWeb;
		this.numeroTelegramCnpj = numeroTelegramCnpj;
		this.numeroTelegramCpf = numeroTelegramCpf;
		this.numeroTelegramNome = numeroTelegramNome;
		this.numeroWhatsAppCnpj = numeroWhatsAppCnpj;
		this.numeroWhatsAppCpf = numeroWhatsAppCpf;
		this.numeroWhatsAppNome = numeroWhatsAppNome;
	}

}
