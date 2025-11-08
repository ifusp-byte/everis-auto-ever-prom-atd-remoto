package com.company.wextag.validation.validators;

import static com.company.wextag.constant.PurchaseTransactionConstants.PURCHASE_DATE_FORMAT;
import static com.company.wextag.constant.PurchaseTransactionConstants.PURCHASE_DATE_REQUIRED;
import static com.company.wextag.util.MessageUtils.getMessage;
import static com.company.wextag.util.MethodUtils.isValidDate;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.dto.ViolationDTO;
import com.company.wextag.validation.PurchaseTransactionValidation;

@Component
public class PurchaseTransactionDateValidator implements PurchaseTransactionValidator {

	PurchaseTransactionValidation validation;

	public PurchaseTransactionDateValidator(@Lazy PurchaseTransactionValidation validation) {
		this.validation = validation;
	}

	@Override
	public Optional<ViolationDTO> validate(PurchaseTransactionInputDTO dto) {

		String transactionDate = dto.getTransactionDate();

		if (StringUtils.isEmpty(transactionDate)) {
			return Optional.of(validation.newViolation(getMessage(PURCHASE_DATE_REQUIRED), transactionDate));
		}

		if (!isValidDate(transactionDate)) {
			return Optional.of(validation.newViolation(getMessage(PURCHASE_DATE_FORMAT), transactionDate));
		}

		return Optional.empty();
	}
}
