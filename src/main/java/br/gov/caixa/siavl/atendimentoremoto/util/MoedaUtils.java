package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
public class MoedaUtils {

	public static String sanitizaValor(String moeda) {
		return moeda.replace("R$", "").replaceAll("\u00A0", "").trim();
	}

}
