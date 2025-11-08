package com.company.wextag.constant;

public class PurchaseTransactionConstants {

	private PurchaseTransactionConstants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String INTERNAL_APPLICATION_ERROR = "internal.application.error";
	public static final String CURRENCY_EXCHANGE_AVAILABLE = "currency.exchange.client.available";
	public static final String CURRENCY_EXCHANGE_NOT_AVAILABLE = "currency.exchange.client.not.available";
	public static final String CURRENCY_EXCHANGE_NOT_FOUND = "currency.exchange.client.not.found";
	public static final String CURRENCY_EXCHANGE_DATE_NOT_FOUND = "currency.exchange.client.not.found.exchange.date";
	public static final String TRANSACTION_FOUND = "purchase.transaction.found";
	public static final String TRANSACTIONS_FOUND = "purchase.transactions.found";
	public static final String TRANSACTION_NOT_FOUND = "purchase.transaction.not.found";
	public static final String TRANSACTION_CREATED = "purchase.transaction.created";
	public static final String TRANSACTION_NOT_CREATED = "purchase.transaction.not.created";
	public static final String TRANSACTION_CONVERTED = "purchase.transaction.converted";
	public static final String TRANSACTION_NOT_CONVERTED = "purchase.transaction.not.converted";
	public static final String DESCRIPTION_EMPTY = "purchase.transaction.description.empty";
	public static final String DESCRIPTION_SIZE = "purchase.transaction.description.size";
	public static final String PURCHASE_AMOUNT_REQUIRED = "purchase.transaction.amount.required";
	public static final String PURCHASE_AMOUNT_GREATER_THAN_ZERO = "purchase.transaction.amount.greater.zero";
	public static final String PURCHASE_DATE_REQUIRED = "purchase.transaction.date.required";
	public static final String PURCHASE_DATE_FORMAT = "purchase.transaction.date.match.format";

}
