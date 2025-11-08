package com.ifusp.empresa.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.ifusp.empresa.batch.dto.CustomerDataFileDTO;

@Component
public class CustomerDataFileMapper implements FieldSetMapper<CustomerDataFileDTO> {
	
	@Override
	public CustomerDataFileDTO mapFieldSet(final FieldSet fieldSet) throws BindException {
		
		CustomerDataFileDTO customerDataFileModel = new CustomerDataFileDTO();
		
		customerDataFileModel.setEmailUsp(fieldSet.readString("emailUsp").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setIdEdisciplinasUsp(fieldSet.readString("idEdisciplinasUsp").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroCpf(fieldSet.readString("numeroCpf").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroPassaporte(fieldSet.readString("numeroPassaporte").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroTelegramCnpj(fieldSet.readString("numeroTelegramCnpj").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroTelegramCpf(fieldSet.readString("numeroTelegramCpf").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroTelegramNome(fieldSet.readString("numeroTelegramNome").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroUsp(fieldSet.readString("numeroUsp").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroWhatsAppCnpj(fieldSet.readString("numeroWhatsAppCnpj").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroWhatsAppCpf(fieldSet.readString("numeroWhatsAppCpf").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setNumeroWhatsAppNome(fieldSet.readString("numeroWhatsAppNome").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setSenhaJupiterWeb(fieldSet.readString("senhaJupiterWeb").toUpperCase().toUpperCase().trim());
		customerDataFileModel.setSenhaUsp(fieldSet.readString("senhaUsp").toUpperCase().toUpperCase().trim());	
		
		return customerDataFileModel;
	}

}
