
package com.company.wextag.service;

import com.company.wextag.dto.ConvertPurchaseTransactionInputDTO;
import com.company.wextag.dto.GenericResponseDTO;
import com.company.wextag.dto.PurchaseTransactionInputDTO;

public interface PurchaseTransactionService {

	GenericResponseDTO saveTransaction(PurchaseTransactionInputDTO dto);

	GenericResponseDTO getAllTransactions();

	GenericResponseDTO getTransactionById(Long id);

	GenericResponseDTO getConvertedTransaction(ConvertPurchaseTransactionInputDTO dto);

}
