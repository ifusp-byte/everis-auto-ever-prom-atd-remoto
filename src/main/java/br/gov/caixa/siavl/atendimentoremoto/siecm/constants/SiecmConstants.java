package br.gov.caixa.siavl.atendimentoremoto.siecm.constants;

public class SiecmConstants {

	private SiecmConstants() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String STRING = "\\u201c"+"STRING"+"\\u201d";
	
	public static final String[] ESTADOS = { "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" };

	public static final String[] SIM_NAO = { "SIM", "NÃO" };

	public static final String[] IMPOSTO_RENDA = { "Declaração Completa", "Declaração Simplificada" };
	
}
