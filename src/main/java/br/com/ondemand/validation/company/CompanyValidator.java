package br.com.ondemand.validation.company;

import java.util.Optional;

import br.com.ondemand.dto.CompanyDto;
import br.com.ondemand.dto.ViolationDto;

public interface CompanyValidator {

	Optional<ViolationDto> validate(CompanyDto company);

}
