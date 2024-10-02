package br.gov.caixa.siavl.atendimentoremoto.report.parameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.ReportInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;

@Component
@SuppressWarnings("all")
public class RoteiroStep4Parameters {

	@Autowired
	MetodosUtils metodosUtils;

	private static final String NOME_NOTA = "nomeNota";
	private static final String NUMERO_NOTA = "numeroNota";
	private static final String NOME_CLIENTE = "nomeCliente";
	private static final String CPF_CNPJ = "cpfCnpj";
	private static final String CONTA_ATENDIMENTO = "contaAtendimento";
	private static final String ROTEIRO_FECHAMENTO = "roteiroFechamento";
	private static final String NOME_PDF = "nomePdf";

	public Map<String, Object> buildParameters(ReportInputDTO reportInputDTO) {

		JsonNode jsonNode = metodosUtils.readTree(String.valueOf(reportInputDTO.getParametrosRelatorio()));

		String nomeNota = Objects.requireNonNull(jsonNode.path(NOME_NOTA)).asText();
		String numeroNota = Objects.requireNonNull(jsonNode.path(NUMERO_NOTA)).asText();
		String nomeCliente = Objects.requireNonNull(jsonNode.path(NOME_CLIENTE)).asText();
		String cpfCnpj = Objects.requireNonNull(jsonNode.path(CPF_CNPJ)).asText();
		String contaAtendimento = Objects.requireNonNull(jsonNode.path(CONTA_ATENDIMENTO)).asText();
		String roteiroFechamento = Objects.requireNonNull(jsonNode.path(ROTEIRO_FECHAMENTO)).asText();

		Map<String, Object> parameters = new HashMap<>();
		parameters.put(NOME_NOTA, nomeNota);
		parameters.put(NUMERO_NOTA, numeroNota);
		parameters.put(NOME_CLIENTE, nomeCliente);
		parameters.put(CPF_CNPJ, cpfCnpj);
		parameters.put(CONTA_ATENDIMENTO, contaAtendimento);
		parameters.put(ROTEIRO_FECHAMENTO, roteiroFechamento);
		parameters.put(NOME_PDF, "Nota-Negociacao_" + numeroNota);

		return parameters;

	}

}
