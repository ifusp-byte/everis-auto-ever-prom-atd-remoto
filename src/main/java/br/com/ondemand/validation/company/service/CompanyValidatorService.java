package br.com.ondemand.validation.company.service;

import java.util.List;

import br.com.ondemand.dto.CompanyDto;
import br.com.ondemand.dto.ViolationDto;

public interface CompanyValidatorService {
	List<ViolationDto> validateAll(List<CompanyDto> companyList);

	List<ViolationDto> validate(CompanyDto company);

	List<ViolationDto> validateHeader(CompanyDto company);

	ViolationDto newViolation(boolean violationStatus, String violationMessage, String violationValue);

	ViolationDto newViolation(boolean violationStatus, String violationMessage);

}
