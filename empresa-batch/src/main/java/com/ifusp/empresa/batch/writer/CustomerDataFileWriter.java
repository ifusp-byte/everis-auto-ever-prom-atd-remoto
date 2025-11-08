package com.ifusp.empresa.batch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ifusp.empresa.batch.dto.CustomerDataFileDTO;
import com.ifusp.empresa.batch.model.CustomerDataFileModel;
import com.ifusp.empresa.batch.repository.CustomerDataFileRepository;

@Configuration
public class CustomerDataFileWriter {

	@Autowired
	private CustomerDataFileRepository repository;

	@Bean
	public ItemWriter<CustomerDataFileDTO> customerFileDataItemWriter() {

		return items -> items.forEach(item -> repository
				.save(new CustomerDataFileModel(item.getNumeroUsp(), item.getEmailUsp(), item.getIdEdisciplinasUsp(),
						item.getSenhaUsp(), item.getNumeroCpf(), item.getNumeroPassaporte(), item.getSenhaJupiterWeb(),
						item.getNumeroTelegramCnpj(), item.getNumeroTelegramCpf(), item.getNumeroTelegramNome(),
						item.getNumeroWhatsAppCnpj(), item.getNumeroWhatsAppCpf(), item.getNumeroWhatsAppNome())));

	}

}
