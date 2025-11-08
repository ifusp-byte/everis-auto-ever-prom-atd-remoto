package ondemand.validation.product.service;

import ondemand.dto.ProductDto;
import ondemand.dto.ViolationDto;
import java.util.List;

public interface ProductValidatorService {
    List<ViolationDto> validateAll(List<ProductDto> productList);

    List<ViolationDto> validate(ProductDto product);

    List<ViolationDto> validateHeader(ProductDto product);

    ViolationDto newViolation(boolean violationStatus, String violationMessage, String violationValue);
}
