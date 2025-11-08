package br.com.ondemand.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ondemand.dto.CompanyDto;
import br.com.ondemand.service.CompanyImportService;

@Configuration
public class CompanyWriter {
	private List<CompanyDto> allItems;
	private final CompanyImportService companyImportService;

	public CompanyWriter(CompanyImportService companyImportService) {
		this.companyImportService = companyImportService;
	}

	@Bean
	public ItemWriter<CompanyDto> companyItemWriter() {
		return items -> {
			allItems = new ArrayList<>();
			allItems.addAll(items);
			companyImportService.allCompanyItems(allItems);
		};
	}
}
