package br.gov.caixa.siavl.atendimentoremoto.report.service;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.ReportInputDTO;

@SuppressWarnings("all")
public interface ReportService {

	Object relatorio(ReportInputDTO reportInputDTO) throws Exception;

}
