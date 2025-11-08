package ondemand.validation.product.validators;

import ondemand.constants.ProductConstants;
import ondemand.dto.ProductDto;
import ondemand.dto.ViolationDto;
import ondemand.validation.product.ProductValidator;
import ondemand.validation.product.service.ProductValidatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ProductUnitPriceValidation implements ProductValidator {
    private final ProductValidatorService productValidatorService;
    private final MessageSource messageSource;

    public ProductUnitPriceValidation(@Lazy ProductValidatorService productValidatorService, MessageSource messageSource) {
        this.productValidatorService = productValidatorService;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<ViolationDto> validate(ProductDto product) {
        String unitPrice = priceFormatValidate(product.getPrecoUnitario());
        if (!Pattern.compile(REGEX_ALLOW_ONLY_NUMBERS).matcher(unitPrice).matches()) {
            boolean statusReturn = true;
            String violationMessage = Util.getMessageSource(ProductConstants.VALIDATION_PRODUCT_PRECO, messageSource);
            return Optional.of(productValidatorService.newViolation(statusReturn, violationMessage, unitPrice));
        }
        product.setPrecoUnitario(priceFormat(product.getPrecoUnitario()));
        return Optional.empty();
    }

    private String priceFormatValidate(String unitPrice) {
        if (StringUtils.isNotBlank(unitPrice)) {
            unitPrice = unitPrice.replace(PONTO, StringUtils.EMPTY);
            unitPrice = unitPrice.replace(COMMA, StringUtils.EMPTY);
        }
        return unitPrice;
    }

    private String priceFormat(String unitPrice) {
        if (StringUtils.isNotBlank(unitPrice)) {
            unitPrice = unitPrice.replace(PONTO, StringUtils.EMPTY);
            unitPrice = unitPrice.replace(COMMA, PONTO);
        }
        return unitPrice;
    }
}
