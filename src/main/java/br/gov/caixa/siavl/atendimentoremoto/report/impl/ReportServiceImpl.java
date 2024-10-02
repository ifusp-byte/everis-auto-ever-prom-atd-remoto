package br.gov.caixa.siavl.atendimentoremoto.report.impl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import br.gov.caixa.siavl.atendimentoremoto.report.constants.ReportConstants;
import br.gov.caixa.siavl.atendimentoremoto.report.dto.ReportInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.report.enums.ReportEnum;
import br.gov.caixa.siavl.atendimentoremoto.report.parameters.RoteiroStep4Parameters;
import br.gov.caixa.siavl.atendimentoremoto.report.service.ReportService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
@SuppressWarnings("all")
public class ReportServiceImpl implements ReportService {

	@Autowired
	DataUtils dataUtils;

	@Autowired
	RoteiroStep4Parameters roteiroStep4Parameters;

	public Object relatorio(ReportInputDTO reportInputDTO) throws Exception {

		Map<String, Object> parameters = new HashMap<>();
		String reportResource = null;
		String fileName = null;

		if (ReportEnum.ROTEIRO_STEP4.getNome().equalsIgnoreCase(reportInputDTO.getNomeRelatorio())) {
			parameters = roteiroStep4Parameters.buildParameters(reportInputDTO);
			reportResource = ReportEnum.ROTEIRO_STEP4.getRelatorio();
			fileName = parameters.get(ReportConstants.NOME_PDF) + ReportConstants.EXTENSION_PDF;
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(JasperFillManager.fillReport(
				(JasperReport) JRLoader.loadObject(
						ResourceUtils.getFile(new ClassPathResource(reportResource).getFile().getAbsolutePath())),
				parameters, new JREmptyDataSource()), byteArrayOutputStream);

		ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
		return ResponseEntity.ok()
				.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
						ReportConstants.ATTACHMENT_FILENAME + fileName + ReportConstants.BAR)
				.contentLength(resource.contentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

	}

}
