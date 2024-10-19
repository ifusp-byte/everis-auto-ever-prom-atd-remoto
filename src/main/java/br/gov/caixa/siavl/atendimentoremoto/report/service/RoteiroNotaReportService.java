package br.gov.caixa.siavl.atendimentoremoto.report.service;

import java.util.Map;

@SuppressWarnings("all")
public interface RoteiroNotaReportService {
	
	String retornaRoteiroFormatado(Long numeroModeloNota, Map<String, Object> parameters);

}
