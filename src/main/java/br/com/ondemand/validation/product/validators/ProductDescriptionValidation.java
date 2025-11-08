package br.com.ondemand.validation.product.validators;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.ondemand.constants.ProductConstants;
import br.com.ondemand.dto.ProductDto;
import br.com.ondemand.dto.ViolationDto;
import br.com.ondemand.validation.product.ProductValidator;
import bsh.util.Util;
import main.java.br.com.ondemand.validation.product.service.ProductValidatorService;

@Component
public class ProductDescriptionValidation implements ProductValidator {
	private final ProductValidatorService productValidatorService;
	private final MessageSource messageSource;

	public ProductDescriptionValidation(@Lazy ProductValidatorService productValidatorService,
			MessageSource messageSource) {
		this.productValidatorService = productValidatorService;
		this.messageSource = messageSource;
	}

	@Override
	public Optional<ViolationDto> validate(ProductDto product) {
		String description = product.getDescricao();

		if (description.isEmpty()) {
			boolean statusReturn = true;
			String violationMessage = Util.getMessageSource(ProductConstants.VALIDATION_PRODUCT_DESCRICAO,
					messageSource);
			return Optional.of(productValidatorService.newViolation(statusReturn, violationMessage, description));
		}
		return Optional.empty();
	}
}
