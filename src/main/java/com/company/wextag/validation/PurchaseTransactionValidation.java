package com.company.wextag.validation;

import java.util.List;

import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.dto.ViolationDTO;

public interface PurchaseTransactionValidation {

	List<ViolationDTO> validate(PurchaseTransactionInputDTO purchaseTransactionInputDTO);

	ViolationDTO newViolation(String property, String value);

}
