package com.ifusp.empresa.batch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDataFileDTO {
	
	private String emailUsp;
	private String idEdisciplinasUsp;
	private String numeroCpf;
	private String numeroPassaporte;
	private String numeroTelegramCnpj;
	private String numeroTelegramCpf;
	private String numeroTelegramNome;
	private String numeroUsp;
	private String numeroWhatsAppCnpj;
	private String numeroWhatsAppCpf;
	private String numeroWhatsAppNome;
	private String senhaJupiterWeb;
	private String senhaUsp;
	
	public static final int START_EMAIL_USP = 1;
	public static final int END_EMAIL_USP = 50;
	public static final int START_ID_EDISCIPLINAS_USP = 51;
	public static final int END_ID_EDISCIPLINAS_USP = 100;
	public static final int START_NUMERO_CPF = 101;
	public static final int END_NUMERO_CPF = 150;
	public static final int START_NUMERO_PASSAPORTE = 151;
	public static final int END_NUMERO_PASSAPORTE = 200;
	public static final int START_NUMERO_TELEGRAM_CNPJ = 201;
	public static final int END_NUMERO_TELEGRAM_CNPJ = 250;
	public static final int START_NUMERO_TELEGRAM_CPF = 251;
	public static final int END_NUMERO_TELEGRAM_CPF = 300;
	public static final int START_NUMERO_TELEGRAM_NOME = 301;
	public static final int END_NUMERO_TELEGRAM_NOME = 350;
	public static final int START_NUMERO_USP = 351;
	public static final int END_NUMERO_USP = 400;
	public static final int START_NUMERO_WHATSAPP_CNPJ = 401;
	public static final int END_NUMERO_WHATSAPP_CNPJ = 450;
	public static final int START_NUMERO_WHATSAPP_CPF = 451;
	public static final int END_NUMERO_WHATSAPP_CPF = 500;
	public static final int START_NUMERO_WHATSAPP_NOME = 501;
	public static final int END_NUMERO_WHATSAPP_NOME = 550;
	public static final int START_SENHA_JUPITER_WEB = 551;
	public static final int END_SENHA_JUPITER_WEB = 600;
	public static final int START_SENHA_USP = 601;
	public static final int END_SENHA_USP = 650; 
	
	public CustomerDataFileDTO() {
		super();
	}
}
