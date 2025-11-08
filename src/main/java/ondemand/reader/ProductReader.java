package ondemand.reader;

import ondemand.dto.ProductDto;
import ondemand.mapper.ProductMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class ProductReader {
    Resource productResource = null;
    private final ProductMapper productMapper;

    public ProductReader(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Bean
    @StepScope
    public PoiItemReader<ProductDto> productItemReader(@Value("#{jobExecutionContext['productFileResource']}") String absolutePathResource) {

        productResource = new FileSystemResource(absolutePathResource);

        PoiItemReader<ProductDto> itemReader = new PoiItemReader<>();

        itemReader.setLinesToSkip(1);
        itemReader.setStrict(false);
        itemReader.setResource(productResource);
        itemReader.setRowMapper(productMapper);
        itemReader.open(new ExecutionContext());
        itemReader.close();
        return itemReader;

    }
}
