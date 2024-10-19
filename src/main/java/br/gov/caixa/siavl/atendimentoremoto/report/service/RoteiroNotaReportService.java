package br.gov.caixa.siavl.atendimentoremoto.report.service;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

@SuppressWarnings("all")
public interface RoteiroNotaReportService {
	
	String retornaRoteiroFormatado(Long numeroModeloNota, Map<String, Object> parameters, JsonNode relatorioNotaDinamico);

}
