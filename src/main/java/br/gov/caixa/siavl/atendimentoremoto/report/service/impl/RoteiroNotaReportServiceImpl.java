package br.gov.caixa.siavl.atendimentoremoto.report.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.report.enums.RoteiroNotaCamposObrigatoriosPFEnum;
import br.gov.caixa.siavl.atendimentoremoto.report.service.RoteiroNotaReportService;
import br.gov.caixa.siavl.atendimentoremoto.repository.RoteiroFechamentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;

@Service
@SuppressWarnings("all")
public class RoteiroNotaReportServiceImpl implements RoteiroNotaReportService {

	@Autowired
	RoteiroFechamentoNotaRepository roteiroFechamentoNotaRepository;

	@Autowired
	MetodosUtils metodosUtils;

	public String retornaRoteiroFormatado(Long numeroModeloNota, Map<String, Object> parameters) {
		return replaceObrigatorios(localizaRoteiroByModeloNota(numeroModeloNota), parameters);
	}

	public String localizaRoteiroByModeloNota(Long numeroModeloNota) {

		String roteiroByModeloNota = StringUtils.EMPTY;
		Optional<List<Clob>> roteiroListaConsulta = roteiroFechamentoNotaRepository.roteiro(numeroModeloNota);
		List<Clob> roteiroRetorno = roteiroListaConsulta.isPresent() ? roteiroListaConsulta.get() : new ArrayList<>();
		Clob roteiro = roteiroRetorno.isEmpty() ? roteiro = null : roteiroRetorno.get(0);

		if (roteiro != null) {
			try {
				roteiroByModeloNota = String
						.valueOf(roteiro.getSubString(1, Integer.parseInt(String.valueOf(roteiro.length()))));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		}

		return roteiroByModeloNota;
	}

	public List<String> localizaCamposRoteiroParaSubstituicao(String roteiroByModeloNota) {

		roteiroByModeloNota = roteiroByModeloNota.replaceAll("<!--(.*?)-->", StringUtils.EMPTY);

		List<String> camposParaSubstituicao = new ArrayList<>();

		Pattern pattern = Pattern.compile("!(.*?)#(.*?)#", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(roteiroByModeloNota);

		while (matcher.find()) {
			System.err.println(matcher.group(0).toString());
			camposParaSubstituicao.add(matcher.group(0).toString());
		}

		return camposParaSubstituicao;

	}

	public String replaceObrigatorios(String roteiroFechamento, Map<String, Object> parameters) {

		List<String> camposRelatorio = localizaCamposRoteiroParaSubstituicao(roteiroFechamento);
		List<Map<String, Object>> camposRelatorioReplace = new ArrayList<>();

		if (!camposRelatorio.isEmpty()) {
			for (String campoRelatorio : camposRelatorio) {
				Map<String, Object> campoRelatorioSubstituicao = new HashMap<>();
				String campoSubstituicao = StringUtils.EMPTY;

				for (RoteiroNotaCamposObrigatoriosPFEnum campo : RoteiroNotaCamposObrigatoriosPFEnum.values()) {
					if (campo.getCampoObrigatorio().equalsIgnoreCase(campoRelatorio)) {
						campoSubstituicao = campo.getCampoObrigatorioReplace();
					}
				}

				campoRelatorioSubstituicao.put("campoRelatorio", campoRelatorio);
				campoRelatorioSubstituicao.put("campoSubstituicao", campoSubstituicao);
				campoRelatorioSubstituicao.put("valor", parameters.get(campoSubstituicao));
				camposRelatorioReplace.add(campoRelatorioSubstituicao);
			}
		}

		if (!camposRelatorioReplace.isEmpty()) {
			for (Map<String, Object> campoRelatorio : camposRelatorioReplace) {
				roteiroFechamento = roteiroFechamento.replaceAll(String.valueOf(campoRelatorio.get("campoRelatorio")),
						String.valueOf(campoRelatorio.get("valor")));
			}
		}

		return roteiroFechamento;

	}

	/*
	 * public String replaceObrigatoriosPF(String roteiroFechamento, Map<String,
	 * Object> parameters) {
	 * 
	 * 
	 * List<String> camposRelatorio =
	 * 
	 * 
	 * 
	 * return null;
	 * 
	 * }
	 * 
	 * public String replaceObrigatoriosPJ(String roteiroFechamento, Map<String,
	 * Object> parameters) {
	 * 
	 * return null;
	 * 
	 * }
	 */
}