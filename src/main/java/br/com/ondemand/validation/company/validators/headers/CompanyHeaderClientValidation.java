package main.java.br.com.ondemand.validation.company.validators.headers;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import main.java.br.com.ondemand.constants.CompanyConstants;
import main.java.br.com.ondemand.dto.CompanyDto;
import main.java.br.com.ondemand.dto.ViolationDto;
import main.java.br.com.ondemand.validation.company.CompanyHeaderValidator;
import main.java.br.com.ondemand.validation.company.service.CompanyValidatorService;

@Component
public class CompanyHeaderClientValidation implements CompanyHeaderValidator {
    private final CompanyValidatorService companyValidatorService;
    private final MessageSource messageSource;

    public CompanyHeaderClientValidation(@Lazy CompanyValidatorService companyValidatorService, MessageSource messageSource) {
        this.companyValidatorService = companyValidatorService;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<ViolationDto> validateHeader(CompanyDto company) {
        String client = company.getIsCliente();
        if (!client.equals(Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_CLIENTE, messageSource))) {
            boolean statusReturn = true;
            return Optional.of(companyValidatorService.newViolation(statusReturn, Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_CLIENTE, messageSource), client));
        }
        return Optional.empty();
    }

}
