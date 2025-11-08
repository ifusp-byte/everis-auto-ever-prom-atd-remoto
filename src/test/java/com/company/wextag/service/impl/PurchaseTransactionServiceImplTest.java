
package com.company.wextag.service.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import com.company.wextag.client.CurrencyExchangeClient;
import com.company.wextag.client.dto.CurrencyExchangeOutputDTO;
import com.company.wextag.dto.ConvertPurchaseTransactionInputDTO;
import com.company.wextag.dto.GenericResponseDTO;
import com.company.wextag.dto.PurchaseTransactionInputDTO;
import com.company.wextag.dto.PurchaseTransactionOutputDTO;
import com.company.wextag.entity.PurchaseTransaction;
import com.company.wextag.repository.PurchaseTransactionRepository;
import com.company.wextag.validation.PurchaseTransactionValidation;
import com.github.tomakehurst.wiremock.WireMockServer;

@SuppressWarnings("all")
class PurchaseTransactionServiceImplTest {

	@InjectMocks
	private PurchaseTransactionServiceImpl purchaseTransactionService;

	@Mock
	private PurchaseTransactionRepository purchaseTransactionRepository;

	@Mock
	private CurrencyExchangeClient currencyExchangeClient;

	@Mock
	private PurchaseTransactionValidation validation;

	@Mock
	private RestTemplate restTemplate;

	private WireMockServer wireMockServer;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		wireMockServer = new WireMockServer(8080);
		wireMockServer.start();

		String jsonResponse = String.format("[{\"exchangeRate\": 5.0, \"recordDate\": \"%s\"}]",
				LocalDate.now().minusDays(10));

		wireMockServer.stubFor(get(urlEqualTo("/api/exchange-rate")).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(jsonResponse)));

		CurrencyExchangeOutputDTO exchangeOutput = new CurrencyExchangeOutputDTO();
		exchangeOutput.setExchangeRate("5.0");
		exchangeOutput.setRecordDate(LocalDate.now().minusDays(10).toString());

		GenericResponseDTO mockedResponse = new GenericResponseDTO();
		mockedResponse.setStatusCode(HttpStatus.OK.value());
		mockedResponse.setData(Collections.singletonList(exchangeOutput));

		when(currencyExchangeClient.getExchangeRate(anyString())).thenReturn(mockedResponse);
	}

	@AfterEach
	void tearDown() {
		if (wireMockServer.isRunning()) {
			wireMockServer.stop();
		}
	}

	@Test
	void testSaveTransaction() {
		PurchaseTransaction transaction = new PurchaseTransaction();
		transaction.setId(1L);
		transaction.setPurchaseAmount(new BigDecimal(100.0));
		transaction.setDescription("abcd");
		transaction.setTransactionDate(LocalDate.parse("2025-01-20"));
		when(purchaseTransactionRepository.save(any(PurchaseTransaction.class))).thenReturn(transaction);

		PurchaseTransactionInputDTO dto = new PurchaseTransactionInputDTO();
		dto.setDescription("abcd");
		dto.setTransactionDate("2025-01-20");
		dto.setPurchaseAmount(new BigDecimal(100.0));
		dto.setTransactionDate("2025-01-20");
		when(validation.validate(dto)).thenReturn(new ArrayList<>());

		GenericResponseDTO result = purchaseTransactionService.saveTransaction(dto);
		PurchaseTransactionOutputDTO purchaseTransaction = (PurchaseTransactionOutputDTO) result.getData();

		assertEquals(1L, purchaseTransaction.getId());
		assertEquals(new BigDecimal(100.0), purchaseTransaction.getPurchaseAmount());
	}

	@Test
	void testGetTransactionById() {
		PurchaseTransaction transaction = new PurchaseTransaction();
		transaction.setId(1L);
		transaction.setPurchaseAmount(new BigDecimal(200.0));
		when(purchaseTransactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

		GenericResponseDTO result = purchaseTransactionService.getTransactionById(1L);
		PurchaseTransactionOutputDTO purchaseTransaction = (PurchaseTransactionOutputDTO) result.getData();

		assertEquals(1L, purchaseTransaction.getId());
		assertEquals(new BigDecimal(200.0), purchaseTransaction.getPurchaseAmount());
		verify(purchaseTransactionRepository, times(1)).findById(1L);
	}

	@Test
	void testGetAllTransactions() {
		PurchaseTransaction transaction1 = new PurchaseTransaction();
		transaction1.setId(1L);
		transaction1.setPurchaseAmount(new BigDecimal(100.0));

		PurchaseTransaction transaction2 = new PurchaseTransaction();
		transaction2.setId(2L);
		transaction2.setPurchaseAmount(new BigDecimal(200.0));

		List<PurchaseTransaction> transactions = Arrays.asList(transaction1, transaction2);
		when(purchaseTransactionRepository.findAll()).thenReturn(transactions);

		GenericResponseDTO result = purchaseTransactionService.getAllTransactions();
		List<PurchaseTransactionOutputDTO> resultList = (List<PurchaseTransactionOutputDTO>) result.getData();

		assertEquals(2, resultList.size());
		verify(purchaseTransactionRepository, times(1)).findAll();
	}

	@Test
	void testGetConvertedTransaction() {

		ConvertPurchaseTransactionInputDTO inputDTO = new ConvertPurchaseTransactionInputDTO();
		inputDTO.setId(1L);
		inputDTO.setCurrency("USD");

		PurchaseTransaction transaction = new PurchaseTransaction();
		transaction.setId(1L);
		transaction.setPurchaseAmount(BigDecimal.valueOf(100.0));
		transaction.setTransactionDate(LocalDate.now().minusDays(10));

		when(purchaseTransactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

		GenericResponseDTO response = purchaseTransactionService.getConvertedTransaction(inputDTO);

		assertNotNull(response, "Response should not be null");
		assertEquals(HttpStatus.OK.value(), response.getStatusCode(), "Status code should be 200");
		assertNotNull(response.getData(), "Response data should not be null");

		verify(purchaseTransactionRepository, times(1)).findById(1L);
		verify(currencyExchangeClient, times(1)).getExchangeRate("USD");
	}

	@Test
	void testModelToDto() {
		PurchaseTransaction transaction = new PurchaseTransaction();
		transaction.setId(1L);
		transaction.setPurchaseAmount(new BigDecimal(300.0));

		var dto = purchaseTransactionService.modelToDto(transaction);

		assertNotNull(dto);
		assertEquals(1L, dto.getId());
		assertEquals(new BigDecimal(300.0), dto.getPurchaseAmount());
	}

}
