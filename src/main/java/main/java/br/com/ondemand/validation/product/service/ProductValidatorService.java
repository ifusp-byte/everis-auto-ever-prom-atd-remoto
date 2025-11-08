package main.java.br.com.ondemand.validation.product.service;

import java.util.List;

import br.com.ondemand.dto.ProductDto;
import br.com.ondemand.dto.ViolationDto;

public interface ProductValidatorService {
	List<ViolationDto> validateAll(List<ProductDto> productList);

	List<ViolationDto> validate(ProductDto product);

	List<ViolationDto> validateHeader(ProductDto product);

	ViolationDto newViolation(boolean violationStatus, String violationMessage, String violationValue);
}
