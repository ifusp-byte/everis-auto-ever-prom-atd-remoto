package ondemand.validation.product;

import ondemand.dto.ProductDto;
import ondemand.dto.ViolationDto;
import java.util.Optional;

public interface ProductValidator {

    Optional<ViolationDto> validate(ProductDto product);

}
