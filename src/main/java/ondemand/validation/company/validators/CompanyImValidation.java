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
public class CompanyImValidation implements CompanyValidator {
    private final CompanyValidatorService companyValidatorService;
    private final MessageSource messageSource;

    public CompanyImValidation(@Lazy CompanyValidatorService companyValidatorService, MessageSource messageSource) {
        this.companyValidatorService = companyValidatorService;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<ViolationDto> validate(CompanyDto company) {
        String im = company.getInscricaoMunicipal();
        if (!Pattern.compile(REGEX_IM).matcher(im).matches()) {
            boolean statusReturn = true;
            String violationMessage = Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_IM, messageSource);
            return Optional.of(companyValidatorService.newViolation(statusReturn, violationMessage, im));
        }
        return Optional.empty();
    }

}
