package br.com.ondemand.validation.company.validators.headers;

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
public class CompanyHeaderEmployeeValidation implements CompanyHeaderValidator {
	private final CompanyValidatorService companyValidatorService;
	private final MessageSource messageSource;

	public CompanyHeaderEmployeeValidation(@Lazy CompanyValidatorService companyValidatorService,
			MessageSource messageSource) {
		this.companyValidatorService = companyValidatorService;
		this.messageSource = messageSource;
	}

	@Override
	public Optional<ViolationDto> validateHeader(CompanyDto company) {
		String employee = company.getIsFuncionario();
		if (!employee
				.equals(Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_FUNCIONARIO, messageSource))) {
			boolean statusReturn = true;
			return Optional.of(companyValidatorService.newViolation(statusReturn,
					Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_FUNCIONARIO, messageSource),
					employee));
		}
		return Optional.empty();
	}

}
