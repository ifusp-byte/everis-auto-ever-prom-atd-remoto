
package com.company.wextag.controller;

import static com.company.wextag.util.MethodUtils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.wextag.dto.ConvertPurchaseTransactionInputDTO;
import com.company.wextag.dto.GenericResponseDTO;
import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.service.PurchaseTransactionService;

@RestController
@RequestMapping(PurchaseTransactionController.BASE_URL)
public class PurchaseTransactionController {

	public static final String BASE_URL = "/v1/api/transactions";
	private final PurchaseTransactionService service;

	public PurchaseTransactionController(PurchaseTransactionService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Object> createTransaction(@RequestBody PurchaseTransactionInputDTO dto) {
		GenericResponseDTO genericResponse = service.saveTransaction(dto);
		return response(HttpStatus.valueOf(genericResponse.getStatusCode()), genericResponse);
	}

	@GetMapping
	public ResponseEntity<Object> getAllTransactions() {
		GenericResponseDTO genericResponse = service.getAllTransactions();
		return response(HttpStatus.valueOf(genericResponse.getStatusCode()), genericResponse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getTransactionById(@PathVariable Long id) {
		GenericResponseDTO genericResponse = service.getTransactionById(id);
		return response(HttpStatus.valueOf(genericResponse.getStatusCode()), genericResponse);
	}

	@GetMapping("/convert")
	public ResponseEntity<Object> getConvertedTransaction(@RequestBody ConvertPurchaseTransactionInputDTO dto) {
		GenericResponseDTO genericResponse = service.getConvertedTransaction(dto);
		return response(HttpStatus.valueOf(genericResponse.getStatusCode()), genericResponse);
	}
}
