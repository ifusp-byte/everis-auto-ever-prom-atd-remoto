package ondemand.validation.product.validators;

import ondemand.constants.ProductConstants;
import ondemand.dto.ProductDto;
import ondemand.dto.ViolationDto;
import ondemand.validation.product.ProductValidator;
import ondemand.validation.product.service.ProductValidatorService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ProductDescriptionValidation implements ProductValidator {
    private final ProductValidatorService productValidatorService;
    private final MessageSource messageSource;

    public ProductDescriptionValidation(@Lazy ProductValidatorService productValidatorService, MessageSource messageSource) {
        this.productValidatorService = productValidatorService;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<ViolationDto> validate(ProductDto product) {
        String description = product.getDescricao();

        if (description.isEmpty()) {
            boolean statusReturn = true;
            String violationMessage = Util.getMessageSource(ProductConstants.VALIDATION_PRODUCT_DESCRICAO, messageSource);
            return Optional.of(productValidatorService.newViolation(statusReturn, violationMessage, description));
        }
        return Optional.empty();
    }
}
