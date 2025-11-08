package br.com.ondemand.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ondemand.dto.ProductDto;
import br.com.ondemand.service.ProductImportService;

@Configuration
public class ProductWriter {
	private List<ProductDto> allItems;
	private final ProductImportService productImportService;

	public ProductWriter(ProductImportService productImportService) {
		this.productImportService = productImportService;
	}

	@Bean
	public ItemWriter<ProductDto> productItemWriter() {
		return items -> {
			allItems = new ArrayList<>();
			allItems.addAll(items);
			productImportService.allProductItems(allItems);
		};
	}
}
