package br.com.ondemand.validation.company.validators;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.ondemand.constants.CompanyConstants;
import br.com.ondemand.dto.CompanyDto;
import br.com.ondemand.dto.ViolationDto;
import br.com.ondemand.validation.company.CompanyValidator;
import br.com.ondemand.validation.company.service.CompanyValidatorService;
import bsh.util.Util;

@Component
public class CompanyIeValidation implements CompanyValidator {
	private final CompanyValidatorService companyValidatorService;
	private final MessageSource messageSource;

	public CompanyIeValidation(@Lazy CompanyValidatorService companyValidatorService, MessageSource messageSource) {
		this.companyValidatorService = companyValidatorService;
		this.messageSource = messageSource;
	}

	@Override
	public Optional<ViolationDto> validate(CompanyDto company) {
		String ie = company.getInscricaoEstadual();
		if (!Pattern.compile(REGEX_IE).matcher(ie).matches()) {
			boolean statusReturn = true;
			String violationMessage = Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_IE, messageSource);
			return Optional.of(companyValidatorService.newViolation(statusReturn, violationMessage, ie));
		}
		return Optional.empty();
	}

}
