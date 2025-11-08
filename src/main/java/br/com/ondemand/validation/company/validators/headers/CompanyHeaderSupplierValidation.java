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
public class CompanyHeaderSupplierValidation implements CompanyHeaderValidator {
	private final CompanyValidatorService companyValidatorService;
	private final MessageSource messageSource;

	public CompanyHeaderSupplierValidation(@Lazy CompanyValidatorService companyValidatorService,
			MessageSource messageSource) {
		this.companyValidatorService = companyValidatorService;
		this.messageSource = messageSource;
	}

	@Override
	public Optional<ViolationDto> validateHeader(CompanyDto company) {
		String supplier = company.getIsFornecedor();
		if (!supplier
				.equals(Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_FORNECEDOR, messageSource))) {
			boolean statusReturn = true;
			return Optional.of(companyValidatorService.newViolation(statusReturn,
					Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_HEADER_FORNECEDOR, messageSource),
					supplier));
		}
		return Optional.empty();
	}

}
