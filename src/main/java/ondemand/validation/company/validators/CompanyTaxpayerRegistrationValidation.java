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
public class CompanyTaxpayerRegistrationValidation implements CompanyValidator {
    private final CompanyRepository companyRepository;
    private final MessageSource messageSource;
    private final CompanyValidatorService companyValidatorService;

    public CompanyTaxpayerRegistrationValidation(CompanyRepository companyRepository, MessageSource messageSource, @Lazy CompanyValidatorService companyValidatorService) {
        this.companyRepository = companyRepository;
        this.messageSource = messageSource;
        this.companyValidatorService = companyValidatorService;
    }

    @Override
    public Optional<ViolationDto> validate(CompanyDto company) {
        String taxpayerRegistration = company.getCnpj();

        if (!companyRepository.findByCnpj(Long.valueOf(company.getUserUnitPersistence()), company.getCnpj()).isEmpty()) {
            boolean statusReturn = true;
            String violationMessage = Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_CNPJ_EXISTENTE, messageSource);
            return Optional.of(companyValidatorService.newViolation(statusReturn, violationMessage, taxpayerRegistration));
        }

        if (!Pattern.compile(REGEX_TAXPAYER_REGISTRATION).matcher(taxpayerRegistration).matches()) {
            boolean statusReturn = true;
            String violationMessage = Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_CNPJ, messageSource);
            return Optional.of(companyValidatorService.newViolation(statusReturn, violationMessage, taxpayerRegistration));
        }
        return Optional.empty();
    }

}
