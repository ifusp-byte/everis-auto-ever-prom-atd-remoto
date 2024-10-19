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

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.RoteiroNotaCampoDinamicoDTO;
import br.gov.caixa.siavl.atendimentoremoto.report.enums.RoteiroNotaCamposObrigatoriosPFEnum;
import br.gov.caixa.siavl.atendimentoremoto.report.service.RoteiroNotaReportService;
import br.gov.caixa.siavl.atendimentoremoto.repository.RoteiroFechamentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.CampoModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;

@Service
@SuppressWarnings("all")
public class RoteiroNotaReportServiceImpl implements RoteiroNotaReportService {

	@Autowired
	RoteiroFechamentoNotaRepository roteiroFechamentoNotaRepository;

	@Autowired
	CampoModeloNotaRepositoryImpl campoModeloNotaRepositoryImpl;

	@Autowired
	MetodosUtils metodosUtils;

	public String retornaRoteiroFormatado(Long numeroModeloNota, Map<String, Object> parameters,
			JsonNode relatorioNotaDinamico) {
		return replaceObrigatorios(localizaRoteiroByModeloNota(numeroModeloNota), parameters, numeroModeloNota,
				relatorioNotaDinamico);
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

	public String replaceObrigatorios(String roteiroFechamento, Map<String, Object> parameters, Long numeroModeloNota,
			JsonNode relatorioNotaDinamico) {

		List<RoteiroNotaCampoDinamicoDTO> listaCamposDinamicos = campoModeloNotaRepositoryImpl.camposDinamicosByModelo(numeroModeloNota);
		List<String> camposRelatorio = localizaCamposRoteiroParaSubstituicao(roteiroFechamento);

		List<Map<String, Object>> listaSubstituicaoObrigatorio = new ArrayList<>();
		List<Map<String, Object>> listaSubstituicaoDinamico = new ArrayList<>();

		if (!camposRelatorio.isEmpty()) {

			for (String campoRelatorio : camposRelatorio) {

				Map<String, Object> substituicaoObrigatorio = new HashMap<>();
				Map<String, Object> substituicaoDinamico = new HashMap<>();

				String campoSubstituicaoObrigatorio = StringUtils.EMPTY;
				String campoSubstituicaoDinamico = StringUtils.EMPTY;

				for (RoteiroNotaCamposObrigatoriosPFEnum campo : RoteiroNotaCamposObrigatoriosPFEnum.values()) {

					if (campo.getCampoObrigatorio().equalsIgnoreCase(campoRelatorio)) {

						campoSubstituicaoObrigatorio = campo.getCampoObrigatorioReplace();
						substituicaoObrigatorio.put("campoRelatorio", campoRelatorio);
						substituicaoObrigatorio.put("campoSubstituicao", campoSubstituicaoObrigatorio);
						substituicaoObrigatorio.put("valor", parameters.get(campoSubstituicaoObrigatorio));
						listaSubstituicaoObrigatorio.add(substituicaoObrigatorio);

					} else {

						for (RoteiroNotaCampoDinamicoDTO campoDinamico : listaCamposDinamicos) {

							if (campoRelatorio.contains(campoDinamico.getIdCampoDinamico())) {

								campoSubstituicaoDinamico = campoDinamico.getNomeCampoDinamico();
								substituicaoDinamico.put("campoRelatorio", campoRelatorio);
								substituicaoDinamico.put("campoSubstituicao", campoSubstituicaoDinamico);
								substituicaoDinamico.put("valor", campoSubstituicaoDinamico +": "+ relatorioNotaDinamico.path(campoSubstituicaoDinamico).asText());
								listaSubstituicaoDinamico.add(substituicaoDinamico);

							}

						}

					}
				}

			}
		}

		if (!listaSubstituicaoObrigatorio.isEmpty()) {
			for (Map<String, Object> campoObrigatorio : listaSubstituicaoObrigatorio) {
				roteiroFechamento = roteiroFechamento.replaceAll(String.valueOf(campoObrigatorio.get("campoRelatorio")),
						String.valueOf(campoObrigatorio.get("valor")));
			}
		}
		
		if (!listaSubstituicaoDinamico.isEmpty()) {
			for (Map<String, Object> campoDinamico : listaSubstituicaoDinamico) {
				roteiroFechamento = roteiroFechamento.replaceAll(String.valueOf(campoDinamico.get("campoRelatorio")),
						String.valueOf(campoDinamico.get("valor")));
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