package br.com.ondemand.validation.product;

import java.util.Optional;

import br.com.ondemand.dto.ProductDto;
import br.com.ondemand.dto.ViolationDto;

public interface ProductValidator {

	Optional<ViolationDto> validate(ProductDto product);

}
