package br.gov.caixa.siavl.atendimentoremoto.siecm.constants;

public class SiecmConstants {

	private SiecmConstants() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String STRING = "STRING";
	
	public static final String DATE = "DATE";
	
	public static final String CPF = "CPF";
	
	public static final String CNPJ = "CNPJ";
	
	public static final String CPF_CNPJ = "CPF/CNPJ";
	
	public static final String[] ESTADOS = { "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" };

	public static final String[] SIM_NAO = { "SIM", "NÃO" };

	public static final String[] IMPOSTO_RENDA = { "Declaração Completa", "Declaração Simplificada" };
	
	public static final String STATUS = "STATUS";
	
	public static final String RESPONSAVEL_CAPTURA = "RESPONSAVEL_CAPTURA";
	
	public static final String CLASSIFICACAO_SIGILO = "CLASSIFICACAO_SIGILO";
	
	public static final String INTERNO_TODOS = "#INTERNO_TODOS";
	
	public static final String DATA_EMISSAO = "DATA_EMISSAO";
	
	public static final String SIAVL = "SIAVL";
	
	public static final String EMISSOR = "EMISSOR";
}
