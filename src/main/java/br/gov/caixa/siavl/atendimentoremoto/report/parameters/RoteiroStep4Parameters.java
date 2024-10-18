package br.gov.caixa.siavl.atendimentoremoto.report.parameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.ReportInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.report.service.RoteiroNotaReportService;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;

@Component
@SuppressWarnings("all")
public class RoteiroStep4Parameters {

	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	RoteiroNotaReportService roteiroNotaReportService;

	private static final String NOME_NOTA = "nomeNota";
	private static final String ASSINATURA = "assinaturaNota";
	private static final String NUMERO_NOTA = "numeroNota";
	private static final String NOME_CLIENTE = "nomeCliente";
	private static final String CPF_CNPJ = "cpfCnpj";
	private static final String CPF_SOCIO = "cpfSocio";
	private static final String NOME_SOCIO = "nomeSocio";
	private static final String CONTA_ATENDIMENTO = "contaAtendimento";
	private static final String ROTEIRO_FECHAMENTO = "roteiroFechamento";
	private static final String NOME_PDF = "nomePdf";
	private static final String NUMERO_MODELO_NOTA = "numeroModeloNota";

	public Map<String, Object> buildParameters(ReportInputDTO reportInputDTO) {

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
		String roteiroFechamento = roteiroNotaReportService.retornaRoteiroFormatado(Long.valueOf(numeroModeloNota));

		Map<String, Object> parameters = new HashMap<>();
		parameters.put(NOME_NOTA, nomeNota);
		parameters.put(ASSINATURA, "ASSINATURA: " + assinaturaNota);
		parameters.put(NUMERO_NOTA, "NOTA DE NEGOCIAÇÃO Nº: " + numeroNota);
		parameters.put(NOME_CLIENTE, "NOME: " + nomeCliente);
		parameters.put(CPF_CNPJ, "CPF/CNPJ: " + cpfCnpj);
		parameters.put(CPF_SOCIO, "CPF SÓCIO: " + cpfSocio);
		parameters.put(NOME_SOCIO, "NOME DO(A) SÓCIO(A): " + nomeSocio);
		parameters.put(CONTA_ATENDIMENTO, "CONTA ATENDIMENTO: " + contaAtendimento);
		parameters.put(ROTEIRO_FECHAMENTO, roteiroFechamento);
		parameters.put(NOME_PDF, "Nota-Negociacao_" + numeroNota);

		return parameters;

	}

}
