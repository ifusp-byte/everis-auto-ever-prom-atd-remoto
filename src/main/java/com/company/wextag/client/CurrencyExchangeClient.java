package com.company.wextag.client;

import static com.company.wextag.constant.PurchaseTransactionConstants.CURRENCY_EXCHANGE_AVAILABLE;
import static com.company.wextag.constant.PurchaseTransactionConstants.CURRENCY_EXCHANGE_NOT_AVAILABLE;
import static com.company.wextag.constant.PurchaseTransactionConstants.CURRENCY_EXCHANGE_NOT_FOUND;
import static com.company.wextag.util.MessageUtils.getMessage;
import static com.company.wextag.util.MethodUtils.readTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.company.wextag.client.dto.CurrencyExchangeOutputDTO;
import com.company.wextag.dto.GenericResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class CurrencyExchangeClient {

	//@Value("${env.treasury.gov.url}")
	private String treasuryGovUrl;

	private final RestTemplate restTemplate;

	public CurrencyExchangeClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public GenericResponseDTO getExchangeRate(String currency) {

		GenericResponseDTO genericResponse = new GenericResponseDTO();
		List<CurrencyExchangeOutputDTO> currencyExchangeList = new ArrayList<>();

		try {

			String url = treasuryGovUrl + "?sort=-record_date&filter=currency:eq:" + currency;
			String response = restTemplate.getForObject(url, String.class);

			ArrayNode dataResponse = (ArrayNode) readTree(response).get("data");

			if (dataResponse.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
				genericResponse.setMessage(getMessage(CURRENCY_EXCHANGE_NOT_FOUND) + currency);
				return genericResponse;
			}

			for (JsonNode data : dataResponse) {
				CurrencyExchangeOutputDTO currencyExchange = new CurrencyExchangeOutputDTO();
				currencyExchange.setRecordDate(Objects.requireNonNull(data.path("record_date")).asText());
				currencyExchange.setExchangeRate(Objects.requireNonNull(data.path("exchange_rate")).asText());
				currencyExchangeList.add(currencyExchange);
			}

			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setMessage(getMessage(CURRENCY_EXCHANGE_AVAILABLE) + currency);
			genericResponse.setData(currencyExchangeList);

		} catch (RestClientResponseException e) {

			genericResponse.setStatusCode(e.getRawStatusCode());
			genericResponse.setMessage(getMessage(CURRENCY_EXCHANGE_NOT_AVAILABLE) + currency);
			return genericResponse;
		}

		return genericResponse;

	}
}
