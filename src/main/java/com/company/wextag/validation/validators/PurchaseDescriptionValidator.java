package com.company.wextag.validation.validators;

import static com.company.wextag.constant.PurchaseTransactionConstants.DESCRIPTION_EMPTY;
import static com.company.wextag.constant.PurchaseTransactionConstants.DESCRIPTION_SIZE;
import static com.company.wextag.util.MessageUtils.getMessage;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.dto.ViolationDTO;
import com.company.wextag.validation.PurchaseTransactionValidation;

@Component
public class PurchaseDescriptionValidator implements PurchaseTransactionValidator {

	PurchaseTransactionValidation validation;

	public PurchaseDescriptionValidator(@Lazy PurchaseTransactionValidation validation) {
		this.validation = validation;
	}

	@Override
	public Optional<ViolationDTO> validate(PurchaseTransactionInputDTO dto) {

		String description = dto.getDescription();

		if (StringUtils.isEmpty(description)) {
			return Optional.of(validation.newViolation(getMessage(DESCRIPTION_EMPTY), description));
		}

		if (description.length() > 50) {
			return Optional.of(validation.newViolation(getMessage(DESCRIPTION_SIZE), description));
		}

		return Optional.empty();
	}
}
