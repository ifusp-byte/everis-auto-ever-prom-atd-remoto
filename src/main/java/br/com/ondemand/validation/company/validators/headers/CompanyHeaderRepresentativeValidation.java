package br.com.ondemand.validation.company.validators.headers;

import ondemand.constants.CompanyConstants;
import ondemand.dto.CompanyDto;
import ondemand.dto.ViolationDto;
import ondemand.validation.company.CompanyHeaderValidator;
import ondemand.validation.company.service.CompanyValidatorService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CompanyHeaderRepresentativeValidation implements CompanyHeaderValidator {
    private final CompanyValidatorService companyValidatorService;
    private final MessageSource messageSource;

    public CompanyHeaderRepresentativeValidation(@Lazy CompanyValidatorService companyValidatorService, MessageSource messageSource) {
        this.companyValidatorService = companyValidatorService;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<ViolationDto> validateHeader(CompanyDto company) {
        String representative = company.getIsRepresentante();
        if (!representative.equals(Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_REPRESENTANTE, messageSource))) {
            boolean statusReturn = true;
            return Optional.of(companyValidatorService.newViolation(statusReturn, Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_REPRESENTANTE, messageSource), representative));
        }
        return Optional.empty();
    }

}
