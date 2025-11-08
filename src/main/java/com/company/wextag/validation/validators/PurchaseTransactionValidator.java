package com.company.wextag.validation.validators;

import java.util.Optional;

import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.dto.ViolationDTO;

public interface PurchaseTransactionValidator {

	Optional<ViolationDTO> validate(PurchaseTransactionInputDTO purchaseTransactionInputDTO);

}
