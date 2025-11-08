package com.ifusp.empresa.batch.token;

import org.springframework.stereotype.Component;

@Component
public class CustomerDataFileToken {

	public String[] customerDataFileToken() {

		String[] customerDataFileTokens = {

				"emailUsp", "idEdisciplinasUsp", "numeroCpf", "numeroPassaporte", "numeroTelegramCnpj",
				"numeroTelegramCpf", "numeroTelegramNome", "numeroUsp", "numeroWhatsAppCnpj", "numeroWhatsAppCpf",
				"numeroWhatsAppNome", "senhaJupiterWeb", "senhaUsp"

		};

		return customerDataFileTokens;
	}

}
