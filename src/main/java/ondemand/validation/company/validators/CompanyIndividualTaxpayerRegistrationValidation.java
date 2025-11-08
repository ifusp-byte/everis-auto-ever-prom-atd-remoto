package ondemand.validation.company.validators;

import ondemand.constants.CompanyConstants;
import ondemand.dto.CompanyDto;
import ondemand.dto.ViolationDto;
import ondemand.validation.company.CompanyValidator;
import ondemand.validation.company.service.CompanyValidatorService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class CompanyIndividualTaxpayerRegistrationValidation implements CompanyValidator {
    private final CompanyValidatorService companyValidatorService;
    private final MessageSource messageSource;

    public CompanyIndividualTaxpayerRegistrationValidation(@Lazy CompanyValidatorService companyValidatorService, MessageSource messageSource) {
        this.companyValidatorService = companyValidatorService;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<ViolationDto> validate(CompanyDto company) {
        String individualTaxpayerRegistration = company.getCpf();
        if (!Pattern.compile(REGEX_CPF).matcher(individualTaxpayerRegistration).matches()) {
            boolean statusReturn = true;
            String violationMessage = Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_CPF, messageSource);
            return Optional.of(companyValidatorService.newViolation(statusReturn, violationMessage, individualTaxpayerRegistration));
        }
        return Optional.empty();
    }

}
