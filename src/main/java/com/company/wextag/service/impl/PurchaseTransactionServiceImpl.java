
package com.company.wextag.service.impl;

import static com.company.wextag.constant.PurchaseTransactionConstants.CURRENCY_EXCHANGE_DATE_NOT_FOUND;
import static com.company.wextag.constant.PurchaseTransactionConstants.TRANSACTIONS_FOUND;
import static com.company.wextag.constant.PurchaseTransactionConstants.TRANSACTION_CONVERTED;
import static com.company.wextag.constant.PurchaseTransactionConstants.TRANSACTION_CREATED;
import static com.company.wextag.constant.PurchaseTransactionConstants.TRANSACTION_FOUND;
import static com.company.wextag.constant.PurchaseTransactionConstants.TRANSACTION_NOT_CONVERTED;
import static com.company.wextag.constant.PurchaseTransactionConstants.TRANSACTION_NOT_CREATED;
import static com.company.wextag.constant.PurchaseTransactionConstants.TRANSACTION_NOT_FOUND;
import static com.company.wextag.util.MessageUtils.getMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.company.wextag.client.CurrencyExchangeClient;
import com.company.wextag.client.dto.CurrencyExchangeOutputDTO;
import com.company.wextag.dto.ConvertPurchaseTransactionInputDTO;
import com.company.wextag.dto.ConvertPurchaseTransactionOutputDTO;
import com.company.wextag.dto.ConvertedPurchaseTransactionOutputDTO;
import com.company.wextag.dto.GenericResponseDTO;
import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.dto.PurchaseTransactionOutputDTO;
import com.company.wextag.dto.ViolationDTO;
import com.company.wextag.entity.PurchaseTransaction;
import com.company.wextag.repository.PurchaseTransactionRepository;
import com.company.wextag.service.PurchaseTransactionService;
import com.company.wextag.validation.PurchaseTransactionValidation;

@Service
public class PurchaseTransactionServiceImpl implements PurchaseTransactionService {

	private final PurchaseTransactionRepository repository;
	private final CurrencyExchangeClient currencyExchangeClient;
	private final PurchaseTransactionValidation validation;

	public PurchaseTransactionServiceImpl(PurchaseTransactionRepository repository,
			CurrencyExchangeClient currencyExchangeClient, PurchaseTransactionValidation validation) {
		this.repository = repository;
		this.currencyExchangeClient = currencyExchangeClient;
		this.validation = validation;
	}

	@Override
	public GenericResponseDTO saveTransaction(PurchaseTransactionInputDTO dto) {

		List<ViolationDTO> violations = validation.validate(dto);
		GenericResponseDTO genericResponse = new GenericResponseDTO();

		if (!violations.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			genericResponse.setMessage(getMessage(TRANSACTION_NOT_CREATED));
			genericResponse.setData(violations);
			return genericResponse;
		}

		PurchaseTransaction transaction = new PurchaseTransaction();
		transaction.setDescription(dto.getDescription());
		transaction.setTransactionDate(LocalDate.parse(dto.getTransactionDate()));
		transaction.setPurchaseAmount(dto.getPurchaseAmount());
		transaction = repository.save(transaction);

		PurchaseTransactionOutputDTO purchaseTransaction = modelToDto(transaction);

		genericResponse.setStatusCode(HttpStatus.CREATED.value());
		genericResponse.setMessage(getMessage(TRANSACTION_CREATED) + purchaseTransaction.getId());
		genericResponse.setData(purchaseTransaction);

		return genericResponse;
	}

	@Override
	public GenericResponseDTO getAllTransactions() {

		GenericResponseDTO genericResponse = new GenericResponseDTO();
		List<PurchaseTransactionOutputDTO> purchaseTransactionList = repository.findAll().stream().map(this::modelToDto)
				.toList();

		if (purchaseTransactionList.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			genericResponse.setMessage(getMessage(TRANSACTION_NOT_FOUND));
			return genericResponse;
		}

		genericResponse.setStatusCode(HttpStatus.OK.value());
		genericResponse.setMessage(purchaseTransactionList.size() + " " + getMessage(TRANSACTIONS_FOUND));
		genericResponse.setData(purchaseTransactionList);

		return genericResponse;

	}

	@Override
	public GenericResponseDTO getTransactionById(Long id) {

		GenericResponseDTO genericResponse = new GenericResponseDTO();
		PurchaseTransactionOutputDTO purchaseTransaction = repository.findById(id).map(this::modelToDto).orElse(null);

		if (purchaseTransaction == null) {
			genericResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			genericResponse.setMessage(getMessage(TRANSACTION_NOT_FOUND) + id);
			return genericResponse;
		}

		genericResponse.setStatusCode(HttpStatus.OK.value());
		genericResponse.setMessage(getMessage(TRANSACTION_FOUND) + id);
		genericResponse.setData(purchaseTransaction);

		return genericResponse;
	}

	@Override
	public GenericResponseDTO getConvertedTransaction(ConvertPurchaseTransactionInputDTO dto) {

		GenericResponseDTO genericResponse = null;
		GenericResponseDTO existingPurchaseTransaction = getTransactionById(dto.getId());

		if (existingPurchaseTransaction.getData() == null) {
			return existingPurchaseTransaction;
		}

		PurchaseTransactionOutputDTO transaction = (PurchaseTransactionOutputDTO) existingPurchaseTransaction.getData();

		ConvertedPurchaseTransactionOutputDTO convertedPurchaseTransaction = new ConvertedPurchaseTransactionOutputDTO();

		convertedPurchaseTransaction.setOriginalPurchaseAmount(transaction.getPurchaseAmount());
		convertedPurchaseTransaction.setId(transaction.getId());
		convertedPurchaseTransaction.setDescription(transaction.getDescription());
		convertedPurchaseTransaction.setTransactionDate(transaction.getTransactionDate());

		genericResponse = currencyExchangeClient.getExchangeRate(dto.getCurrency());

		if (!HttpStatus.OK.equals(HttpStatus.valueOf(genericResponse.getStatusCode()))) {
			genericResponse.setStatusCode(genericResponse.getStatusCode());
			genericResponse.setMessage(genericResponse.getMessage());
			genericResponse.setData(convertedPurchaseTransaction);
			return genericResponse;
		}

		List<CurrencyExchangeOutputDTO> currencyExchangeList = (List<CurrencyExchangeOutputDTO>) genericResponse
				.getData();

		CurrencyExchangeOutputDTO currencyExchange = closestCurrencyExchange(currencyExchangeList, transaction);

		ConvertPurchaseTransactionOutputDTO convertPurchaseTransaction = new ConvertPurchaseTransactionOutputDTO();

		if (currencyExchange != null) {

			convertPurchaseTransaction.setConvertedPurchaseTransaction(convertedPurchaseTransaction);
			convertPurchaseTransaction.setExchangeRate(currencyExchange.getExchangeRate());
			convertPurchaseTransaction.setRecordDate(LocalDate.parse(currencyExchange.getRecordDate()));

			if (Period.between(LocalDate.parse(currencyExchange.getRecordDate()), transaction.getTransactionDate())
					.getMonths() > 6) {
				genericResponse.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
				genericResponse.setMessage(getMessage(TRANSACTION_NOT_CONVERTED));
				genericResponse.setData(convertPurchaseTransaction);
				return genericResponse;
			}

			convertedPurchaseTransaction.setConvertedPurchaseAmount(transaction.getPurchaseAmount()
					.multiply(new BigDecimal(currencyExchange.getExchangeRate())).setScale(2, RoundingMode.HALF_UP));

			convertPurchaseTransaction.setConvertedPurchaseTransaction(convertedPurchaseTransaction);

			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage(getMessage(TRANSACTION_CONVERTED));
			genericResponse.setData(convertPurchaseTransaction);

			return genericResponse;

		}

		genericResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		genericResponse.setMessage(getMessage(CURRENCY_EXCHANGE_DATE_NOT_FOUND));
		return genericResponse;
	}

	public static CurrencyExchangeOutputDTO closestCurrencyExchange(
			List<CurrencyExchangeOutputDTO> currencyExchangeList, PurchaseTransactionOutputDTO transaction) {

		CurrencyExchangeOutputDTO closestCurrencyExchange = null;
		long smallestDifferenceInDays = Long.MAX_VALUE;

		for (CurrencyExchangeOutputDTO currencyExchange : currencyExchangeList) {

			long differenceInDays = ChronoUnit.DAYS.between(LocalDate.parse(currencyExchange.getRecordDate()),
					transaction.getTransactionDate());

			if (differenceInDays < 0) {
				continue;
			}

			if (differenceInDays < smallestDifferenceInDays) {
				smallestDifferenceInDays = differenceInDays;
				closestCurrencyExchange = currencyExchange;
			}
		}

		return closestCurrencyExchange;
	}

	public PurchaseTransactionOutputDTO modelToDto(PurchaseTransaction purchaseTransaction) {
		return PurchaseTransactionOutputDTO.builder().id(purchaseTransaction.getId())
				.description(purchaseTransaction.getDescription())
				.transactionDate(purchaseTransaction.getTransactionDate())
				.purchaseAmount(purchaseTransaction.getPurchaseAmount()).build();
	}
}
