package com.ifusp.empresa.batch.range;

import org.springframework.batch.item.file.transform.Range;
import org.springframework.stereotype.Component;

import com.ifusp.empresa.batch.dto.CustomerDataFileDTO;

@Component
public class CustomerDataFileRange {
	
	public Range[] customerDataFileRange() {
		
		Range[] customerDataFileRanges = {
				
				new Range(CustomerDataFileDTO.START_EMAIL_USP, CustomerDataFileDTO.END_EMAIL_USP),
				new Range(CustomerDataFileDTO.START_ID_EDISCIPLINAS_USP, CustomerDataFileDTO.END_ID_EDISCIPLINAS_USP),
				new Range(CustomerDataFileDTO.START_NUMERO_CPF, CustomerDataFileDTO.END_NUMERO_CPF),
				new Range(CustomerDataFileDTO.START_NUMERO_PASSAPORTE, CustomerDataFileDTO.END_NUMERO_PASSAPORTE),
				new Range(CustomerDataFileDTO.START_NUMERO_TELEGRAM_CNPJ, CustomerDataFileDTO.END_NUMERO_TELEGRAM_CNPJ),
				new Range(CustomerDataFileDTO.START_NUMERO_TELEGRAM_CPF, CustomerDataFileDTO.END_NUMERO_TELEGRAM_CPF),
				new Range(CustomerDataFileDTO.START_NUMERO_TELEGRAM_NOME, CustomerDataFileDTO.END_NUMERO_TELEGRAM_NOME),
				new Range(CustomerDataFileDTO.START_NUMERO_USP, CustomerDataFileDTO.END_NUMERO_USP),
				new Range(CustomerDataFileDTO.START_NUMERO_WHATSAPP_CNPJ, CustomerDataFileDTO.END_NUMERO_WHATSAPP_CNPJ),
				new Range(CustomerDataFileDTO.START_NUMERO_WHATSAPP_CPF, CustomerDataFileDTO.END_NUMERO_WHATSAPP_CPF),
				new Range(CustomerDataFileDTO.START_NUMERO_WHATSAPP_NOME, CustomerDataFileDTO.END_NUMERO_WHATSAPP_NOME),
				new Range(CustomerDataFileDTO.START_SENHA_JUPITER_WEB, CustomerDataFileDTO.END_SENHA_JUPITER_WEB),
				new Range(CustomerDataFileDTO.START_SENHA_USP, CustomerDataFileDTO.END_SENHA_USP)
							
		};
		
		return customerDataFileRanges;
	}

}
