package br.com.ondemand.validation.company.validators;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import main.java.br.com.ondemand.constants.CompanyConstants;
import main.java.br.com.ondemand.dto.CompanyDto;
import main.java.br.com.ondemand.dto.ViolationDto;
import main.java.br.com.ondemand.validation.company.CompanyValidator;
import main.java.br.com.ondemand.validation.company.service.CompanyValidatorService;

@Component
public class CompanyCommercialPhoneValidation implements CompanyValidator {
	private final CompanyValidatorService companyValidatorService;
	private final MessageSource messageSource;

	public CompanyCommercialPhoneValidation(@Lazy CompanyValidatorService companyValidatorService,
			MessageSource messageSource) {
		this.companyValidatorService = companyValidatorService;
		this.messageSource = messageSource;
	}

	@Override
	public Optional<ViolationDto> validate(CompanyDto company) {
		String commercialPhone = company.getTelefoneComercial();
		if (!Pattern.compile(REGEX_COMPANY_COMMERCIAL_PHONE).matcher(commercialPhone).matches()) {
			boolean statusReturn = true;
			String violationMessage = Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_TELEFONE_COMERCIAL,
					messageSource);
			return Optional.of(companyValidatorService.newViolation(statusReturn, violationMessage, commercialPhone));
		}
		return Optional.empty();
	}

}
