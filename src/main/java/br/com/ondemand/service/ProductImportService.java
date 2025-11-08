package br.com.ondemand.service;

import java.util.List;

import br.com.ondemand.dto.ProductDto;

public interface ProductImportService {
    Boolean productJob(String userEmail, String userPersistenceUnit, String urlFileAmazon, Long idFileAmazon);

    void allProductItems(List<ProductDto> productList);
}
