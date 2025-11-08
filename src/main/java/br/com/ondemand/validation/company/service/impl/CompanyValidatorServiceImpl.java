package br.com.ondemand.validation.company.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ondemand.dto.CompanyDto;
import br.com.ondemand.dto.ViolationDto;
import br.com.ondemand.validation.company.CompanyHeaderValidator;
import br.com.ondemand.validation.company.CompanyValidator;
import br.com.ondemand.validation.company.service.CompanyValidatorService;

@Service
public class CompanyValidatorServiceImpl implements CompanyValidatorService {
	private final List<CompanyValidator> companyValidators;
	private final List<CompanyHeaderValidator> companyHeaderValidators;

	public CompanyValidatorServiceImpl(List<CompanyValidator> companyValidators,
			List<CompanyHeaderValidator> companyHeaderValidators) {
		this.companyValidators = companyValidators;
		this.companyHeaderValidators = companyHeaderValidators;
	}

	@Override
	public List<ViolationDto> validateAll(List<CompanyDto> companyList) {
		return companyList.stream().flatMap(company -> validate(company).stream()).collect(Collectors.toList());
	}

	@Override
	public List<ViolationDto> validate(CompanyDto company) {
		return companyValidators.stream().map(validator -> validator.validate(company)).filter(Optional::isPresent)
				.map(Optional::get).collect(Collectors.toList());
	}

	@Override
	public List<ViolationDto> validateHeader(CompanyDto company) {
		return companyHeaderValidators.stream().map(validator -> validator.validateHeader(company))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
	}

	@Override
	public ViolationDto newViolation(boolean violationStatus, String violationMessage, String violationValue) {
		ViolationDto violation = new ViolationDto();
		violation.setViolationStatus(violationStatus);
		violation.setViolationMessage(violationMessage);
		violation.setViolationValue(violationValue);
		return violation;
	}

	@Override
	public ViolationDto newViolation(boolean violationStatus, String violationMessage) {
		ViolationDto violation = new ViolationDto();
		violation.setViolationStatus(violationStatus);
		violation.setViolationMessage(violationMessage);
		return violation;
	}

}
