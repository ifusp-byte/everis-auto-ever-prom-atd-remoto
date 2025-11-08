package ondemand.validation.product.validators.headers;

import ondemand.constants.ProductConstants;
import ondemand.dto.ProductDto;
import ondemand.dto.ViolationDto;
import ondemand.validation.product.ProductHeaderValidator;
import ondemand.validation.product.service.ProductValidatorService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ProductHeaderUnitPriceValidation implements ProductHeaderValidator {
    private final ProductValidatorService productValidatorService;
    private final MessageSource messageSource;

    public ProductHeaderUnitPriceValidation(MessageSource messageSource, @Lazy ProductValidatorService productValidatorService) {
        this.messageSource = messageSource;
        this.productValidatorService = productValidatorService;
    }

    @Override
    public Optional<ViolationDto> validateHeader(ProductDto product) {
        String unitPrice = product.getPrecoUnitario();
        if (!unitPrice.equals(Util.getMessageSource(ProductConstants.VALIDATION_PRODUCT_HEADER_PRECO_UNITARIO, messageSource))) {
            boolean statusReturn = true;
            return Optional.of(productValidatorService.newViolation(statusReturn, Util.getMessageSource(ProductConstants.VALIDATION_PRODUCT_HEADER_PRECO_UNITARIO, messageSource), unitPrice));
        }
        return Optional.empty();
    }

}