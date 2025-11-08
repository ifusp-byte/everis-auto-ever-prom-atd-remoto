package ondemand.validation.company.validators.headers;

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
public class CompanyHeaderTaxpayerTypeValidation implements CompanyHeaderValidator {
    private final CompanyValidatorService companyValidatorService;
    private final MessageSource messageSource;

    public CompanyHeaderTaxpayerTypeValidation(@Lazy CompanyValidatorService companyValidatorService, MessageSource messageSource) {
        this.companyValidatorService = companyValidatorService;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<ViolationDto> validateHeader(CompanyDto company) {
        String taxpayerType = company.getTipoContribuinte();
        if (!taxpayerType.equals(Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_TIPO_CONTRIBUINTE, messageSource))) {
            boolean statusReturn = true;
            return Optional.of(companyValidatorService.newViolation(statusReturn, Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_TIPO_CONTRIBUINTE, messageSource), taxpayerType));
        }
        return Optional.empty();
    }

}

