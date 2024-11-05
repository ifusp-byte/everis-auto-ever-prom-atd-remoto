package br.gov.caixa.siavl.atendimentoremoto.report.parameters;

import static br.gov.caixa.siavl.atendimentoremoto.report.constants.ReportConstants.LOGO_CAIXA_3;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.ASSINATURA;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.CONTA_ATENDIMENTO;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.CPF_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.CPF_SOCIO;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.NOME_CLIENTE;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.NOME_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.NOME_PDF;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.NOME_SOCIO;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.NUMERO_MODELO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.NUMERO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.RAZAO_SOCIAL;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.RELATORIO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.ROTEIRO_FECHAMENTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.ReportInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.report.enums.ReportEnum;
import br.gov.caixa.siavl.atendimentoremoto.report.service.RoteiroNotaReportService;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
@SuppressWarnings("all")
public class RoteiroStep4Parameters {

	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	RoteiroNotaReportService roteiroNotaReportService;

	@Autowired
	DocumentoUtils documentoUtils;

	public Map<String, Object> buildParameters(ReportInputDTO reportInputDTO) throws Exception {

		JsonNode jsonNode = metodosUtils.readTree(metodosUtils.writeValueAsString(reportInputDTO.getParametrosRelatorio()));

		String nomeNota = Objects.requireNonNull(jsonNode.path(NOME_NOTA)).asText();
		String assinaturaNota = Objects.requireNonNull(jsonNode.path(ASSINATURA)).asText();
		String numeroNota = Objects.requireNonNull(jsonNode.path(NUMERO_NOTA)).asText();
		String nomeCliente = Objects.requireNonNull(jsonNode.path(NOME_CLIENTE)).asText();
		String cpfCnpj = Objects.requireNonNull(jsonNode.path(CPF_CNPJ)).asText();
		String cpfSocio = Objects.requireNonNull(jsonNode.path(CPF_SOCIO)).asText();
		String nomeSocio = Objects.requireNonNull(jsonNode.path(NOME_SOCIO)).asText();
		String contaAtendimento = Objects.requireNonNull(jsonNode.path(CONTA_ATENDIMENTO)).asText();
		String numeroModeloNota = Objects.requireNonNull(jsonNode.path(NUMERO_MODELO_NOTA)).asText();
		String cnpj = Objects.requireNonNull(jsonNode.path(CNPJ)).asText();
		String razaoSocial = Objects.requireNonNull(jsonNode.path(RAZAO_SOCIAL)).asText();
		String valorMeta = Objects.requireNonNull(jsonNode.path(RAZAO_SOCIAL)).asText();
		JsonNode relatorioNotaDinamico = Objects.requireNonNull(jsonNode.path(RELATORIO_NOTA));

		Map<String, Object> parameters = new HashMap<>();
		parameters.put(NOME_NOTA, nomeNota);
		parameters.put(ASSINATURA, "ASSINATURA: " +"<b>"+StringUtils.upperCase(assinaturaNota)+"</b>");
		parameters.put(NUMERO_NOTA, "NOTA DE NEGOCIAÇÃO Nº: " + "<b>" + numeroNota + "</b>");

		if (documentoUtils.retornaCpf(cpfCnpj)) {

			parameters.put(NOME_CLIENTE, "<b>NOME: " + StringUtils.upperCase(nomeCliente) + "</b>");
			parameters.put(CPF_CNPJ, "CPF: " + "<b>" + cpfCnpj + "</b>");

		} else {

			parameters.put(NOME_CLIENTE, "<b>RAZÃO SOCIAL: " + StringUtils.upperCase(nomeCliente) + "</b>");
			parameters.put(CPF_CNPJ, "CNPJ: " + "<b>" + cpfCnpj + "</b>");

		}

		parameters.put(CNPJ, "<b>CNPJ:</b> " + cnpj);
		parameters.put(RAZAO_SOCIAL, "<b>RAZÃO SOCIAL:</b> " + razaoSocial);
		parameters.put(CPF_SOCIO, "<b>CPF SÓCIO(A):</b> " + cpfSocio);
		parameters.put(NOME_SOCIO, "<b>NOME DO(A) SÓCIO(A):</b> " + StringUtils.upperCase(nomeSocio));
		parameters.put(CONTA_ATENDIMENTO, "CONTA ATENDIMENTO: " + "<b>" + contaAtendimento + "</b>");
		parameters.put(ROTEIRO_FECHAMENTO, roteiroNotaReportService.retornaRoteiroFormatado(Long.valueOf(numeroModeloNota), parameters, relatorioNotaDinamico));
		parameters.put(NOME_PDF, "Nota-Negociacao_" + numeroNota);
		parameters.put(LOGO_CAIXA_3, ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(new ClassPathResource(ReportEnum.LOGO_CAIXA.getRelatorio()).getInputStream()))));

		return parameters;

	}

}
