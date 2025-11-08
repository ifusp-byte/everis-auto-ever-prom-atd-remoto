package ondemand.writer;

import ondemand.dto.ProductDto;
import ondemand.service.ProductImportService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

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
