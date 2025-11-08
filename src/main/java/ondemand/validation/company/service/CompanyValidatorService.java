package ondemand.validation.company.service;

import ondemand.dto.CompanyDto;
import ondemand.dto.ViolationDto;
import java.util.List;

public interface CompanyValidatorService {
    List<ViolationDto> validateAll(List<CompanyDto> companyList);

    List<ViolationDto> validate(CompanyDto company);

    List<ViolationDto> validateHeader(CompanyDto company);

    ViolationDto newViolation(boolean violationStatus, String violationMessage, String violationValue);

    ViolationDto newViolation(boolean violationStatus, String violationMessage);

}
