package main.java.br.com.ondemand.validation.company.validators.headers;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.ondemand.constants.CompanyConstants;
import br.com.ondemand.dto.CompanyDto;
import br.com.ondemand.dto.ViolationDto;
import br.com.ondemand.validation.company.CompanyHeaderValidator;
import br.com.ondemand.validation.company.service.CompanyValidatorService;
import bsh.util.Util;

@Component
public class CompanyHeaderIeValidation implements CompanyHeaderValidator {
	private final CompanyValidatorService companyValidatorService;
	private final MessageSource messageSource;

	public CompanyHeaderIeValidation(@Lazy CompanyValidatorService companyValidatorService,
			MessageSource messageSource) {
		this.companyValidatorService = companyValidatorService;
		this.messageSource = messageSource;
	}

	@Override
	public Optional<ViolationDto> validateHeader(CompanyDto company) {
		String ie = company.getInscricaoEstadual();
		if (!ie.equals(Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_IE, messageSource))) {
			boolean statusReturn = true;
			return Optional.of(companyValidatorService.newViolation(statusReturn,
					Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_IE, messageSource), ie));
		}
		return Optional.empty();
	}

}