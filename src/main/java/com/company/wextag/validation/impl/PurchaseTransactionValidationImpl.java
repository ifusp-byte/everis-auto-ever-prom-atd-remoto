package com.company.wextag.validation.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.dto.ViolationDTO;
import com.company.wextag.validation.PurchaseTransactionValidation;
import com.company.wextag.validation.validators.PurchaseTransactionValidator;

@Service
public class PurchaseTransactionValidationImpl implements PurchaseTransactionValidation {

	private final List<PurchaseTransactionValidator> validators;

	public PurchaseTransactionValidationImpl(List<PurchaseTransactionValidator> validators) {
		this.validators = validators;
	}

	@Override
	public List<ViolationDTO> validate(PurchaseTransactionInputDTO purchaseTransactionInputDTO) {
		return validators.stream().map(validator -> validator.validate(purchaseTransactionInputDTO))
				.filter(Optional::isPresent).map(Optional::get).toList();
	}

	@Override
	public ViolationDTO newViolation(String property, String value) {
		ViolationDTO violationDTO = new ViolationDTO();
		violationDTO.setProperty(property);
		violationDTO.setValue(value);
		return violationDTO;

	}

}
