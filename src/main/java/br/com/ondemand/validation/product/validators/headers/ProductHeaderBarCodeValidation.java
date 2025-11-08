package main.java.br.com.ondemand.validation.product.validators.headers;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.ondemand.constants.ProductConstants;
import br.com.ondemand.dto.ProductDto;
import br.com.ondemand.dto.ViolationDto;
import br.com.ondemand.validation.product.ProductHeaderValidator;
import bsh.util.Util;
import main.java.br.com.ondemand.validation.product.service.ProductValidatorService;

@Component
public class ProductHeaderBarCodeValidation implements ProductHeaderValidator {
	private final ProductValidatorService productValidatorService;
	private final MessageSource messageSource;

	public ProductHeaderBarCodeValidation(MessageSource messageSource,
			@Lazy ProductValidatorService productValidatorService) {
		this.messageSource = messageSource;
		this.productValidatorService = productValidatorService;
	}

	@Override
	public Optional<ViolationDto> validateHeader(ProductDto product) {
		String barCode = product.getCodBarras();
		if (!barCode.equals(
				Util.getMessageSource(ProductConstants.VALIDATION_PRODUCT_HEADER_CODIGO_BARRAS, messageSource))) {
			boolean statusReturn = true;
			return Optional.of(productValidatorService.newViolation(statusReturn,
					Util.getMessageSource(ProductConstants.VALIDATION_PRODUCT_HEADER_CODIGO_BARRAS, messageSource),
					barCode));
		}
		return Optional.empty();
	}

}