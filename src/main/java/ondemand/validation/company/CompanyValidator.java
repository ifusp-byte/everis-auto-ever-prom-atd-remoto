package ondemand.validation.company;

import ondemand.dto.CompanyDto;
import ondemand.dto.ViolationDto;
import java.util.Optional;

public interface CompanyValidator {

    Optional<ViolationDto> validate(CompanyDto company);

}
