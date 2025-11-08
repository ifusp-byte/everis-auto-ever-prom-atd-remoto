package ondemand.service;

import ondemand.dto.ProductDto;
import java.util.List;

public interface ProductImportService {
    Boolean productJob(String userEmail, String userPersistenceUnit, String urlFileAmazon, Long idFileAmazon);

    void allProductItems(List<ProductDto> productList);
}
