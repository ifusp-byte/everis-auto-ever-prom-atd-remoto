package br.gov.caixa.siavl.autorizacaoenviomsg.util;

@SuppressWarnings("all")
public final class ConstantsUtils {

	private ConstantsUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static final String ALL = "*";
	public static final String WARNINGS = "all";
	public static final String BEARER = "Bearer";
	public static final String AUTHORIZATION = "Authorization";
	public static final String UNRECOGNIZED_TOKEN = "Unrecognized token";
	public static final String UTILITY_CLASS = "Utility class";

}