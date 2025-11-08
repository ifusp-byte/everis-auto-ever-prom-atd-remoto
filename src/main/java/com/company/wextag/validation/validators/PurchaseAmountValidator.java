package com.company.wextag.validation.validators;

import static com.company.wextag.constant.PurchaseTransactionConstants.PURCHASE_AMOUNT_GREATER_THAN_ZERO;
import static com.company.wextag.constant.PurchaseTransactionConstants.PURCHASE_AMOUNT_REQUIRED;
import static com.company.wextag.util.MessageUtils.getMessage;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.dto.ViolationDTO;
import com.company.wextag.validation.PurchaseTransactionValidation;

@Component
public class PurchaseAmountValidator implements PurchaseTransactionValidator {

	PurchaseTransactionValidation validation;

	public PurchaseAmountValidator(@Lazy PurchaseTransactionValidation validation) {
		this.validation = validation;
	}

	@Override
	public Optional<ViolationDTO> validate(PurchaseTransactionInputDTO dto) {

		BigDecimal purchaseAmount = dto.getPurchaseAmount();

		if (purchaseAmount == null) {
			return Optional.of(validation.newViolation(getMessage(PURCHASE_AMOUNT_REQUIRED), String.valueOf(purchaseAmount)));
		}

		if (purchaseAmount.compareTo(new BigDecimal(String.valueOf(0.01))) < 0) {
			return Optional.of(validation.newViolation(getMessage(PURCHASE_AMOUNT_GREATER_THAN_ZERO), String.valueOf(purchaseAmount)));
		}

		return Optional.empty();
	}
}
