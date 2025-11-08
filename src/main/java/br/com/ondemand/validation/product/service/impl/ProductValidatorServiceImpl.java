package br.com.ondemand.validation.product.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ondemand.dto.ProductDto;
import br.com.ondemand.dto.ViolationDto;
import br.com.ondemand.validation.product.ProductHeaderValidator;
import br.com.ondemand.validation.product.ProductValidator;
import br.com.ondemand.validation.product.service.ProductValidatorService;

@Service
public class ProductValidatorServiceImpl implements ProductValidatorService {

	private final List<ProductValidator> productValidators;
	private final List<ProductHeaderValidator> productHeaderValidators;

	public ProductValidatorServiceImpl(List<ProductValidator> productValidators,
			List<ProductHeaderValidator> productHeaderValidators) {
		this.productValidators = productValidators;
		this.productHeaderValidators = productHeaderValidators;
	}

	@Override
	public List<ViolationDto> validateAll(List<ProductDto> productList) {
		return productList.stream().flatMap(product -> validate(product).stream()).collect(Collectors.toList());
	}

	@Override
	public List<ViolationDto> validate(ProductDto product) {
		return productValidators.stream().map(validator -> validator.validate(product)).filter(Optional::isPresent)
				.map(Optional::get).collect(Collectors.toList());
	}

	@Override
	public List<ViolationDto> validateHeader(ProductDto product) {
		return productHeaderValidators.stream().map(validator -> validator.validateHeader(product))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
	}

	@Override
	public ViolationDto newViolation(boolean violationStatus, String violationMessage, String violationValue) {
		ViolationDto violation = new ViolationDto();
		violation.setViolationStatus(violationStatus);
		violation.setViolationMessage(violationMessage);
		violation.setViolationValue(violationValue);
		return violation;
	}

}
