package br.gov.caixa.siavl.atendimentoremoto.report.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants.CPF_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.util.DataUtils.formataDataLocalTexto;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.RoteiroNotaCampoDinamicoDTO;
import br.gov.caixa.siavl.atendimentoremoto.report.enums.RoteiroNotaCamposObrigatoriosPFEnum;
import br.gov.caixa.siavl.atendimentoremoto.report.enums.RoteiroNotaCamposObrigatoriosPJEnum;
import br.gov.caixa.siavl.atendimentoremoto.report.service.RoteiroNotaReportService;
import br.gov.caixa.siavl.atendimentoremoto.repository.RoteiroFechamentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.CampoModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;

@Service
@SuppressWarnings("all")
public class RoteiroNotaReportServiceImpl implements RoteiroNotaReportService {

	@Autowired
	RoteiroFechamentoNotaRepository roteiroFechamentoNotaRepository;

	@Autowired
	CampoModeloNotaRepositoryImpl campoModeloNotaRepositoryImpl;

	@Autowired
	DocumentoUtils documentoUtils;

	@Autowired
	MetodosUtils metodosUtils;

	public String retornaRoteiroFormatado(Long numeroModeloNota, Map<String, Object> parameters,
			JsonNode relatorioNotaDinamico) {
		return replaceCamposRelatorio(localizaRoteiroByModeloNota(numeroModeloNota), parameters, numeroModeloNota,
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
			camposParaSubstituicao.add(matcher.group(0).toString());
		}

		return camposParaSubstituicao;

	}

	public String replaceCamposRelatorio(String roteiroFechamento, Map<String, Object> parameters,
			Long numeroModeloNota, JsonNode relatorioNotaDinamico) {

		List<RoteiroNotaCampoDinamicoDTO> listaCamposDinamicos = campoModeloNotaRepositoryImpl.camposDinamicosByModelo(numeroModeloNota);
		List<String> camposRelatorio = localizaCamposRoteiroParaSubstituicao(roteiroFechamento);

		List<Map<String, Object>> listaSubstituicaoObrigatorio = new ArrayList<>();
		List<Map<String, Object>> listaSubstituicaoDinamico = new ArrayList<>();
		
		String cpfCnpj = String.valueOf(parameters.get(CPF_CNPJ)).replaceAll("<(.*?)>", StringUtils.EMPTY).replaceAll("CPF: ", StringUtils.EMPTY).trim();

		if (!camposRelatorio.isEmpty()) {

			for (String campoRelatorio : camposRelatorio) {

				Map<String, Object> substituicaoObrigatorio = new HashMap<>();
				Map<String, Object> substituicaoDinamico = new HashMap<>();

				if (documentoUtils.retornaCpf(cpfCnpj)) {
					listaSubstituicaoObrigatorio = replaceObrigatoriosPF(listaSubstituicaoObrigatorio,
							substituicaoObrigatorio, campoRelatorio, parameters);
				} else {
					listaSubstituicaoObrigatorio = replaceObrigatoriosPJ(listaSubstituicaoObrigatorio,
							substituicaoObrigatorio, campoRelatorio, parameters);
				}

				listaSubstituicaoDinamico = replaceDinamicos(listaSubstituicaoDinamico, substituicaoDinamico,
						campoRelatorio, parameters, relatorioNotaDinamico, listaCamposDinamicos);

			}
		}

		if (!listaSubstituicaoObrigatorio.isEmpty()) {
			for (Map<String, Object> campoObrigatorio : listaSubstituicaoObrigatorio) {
				roteiroFechamento = roteiroFechamento.replaceAll(String.valueOf(campoObrigatorio.get("campoRelatorio")),
						Matcher.quoteReplacement(String.valueOf(campoObrigatorio.get("valor")).trim()));
			}
		}

		if (!listaSubstituicaoDinamico.isEmpty()) {
			for (Map<String, Object> campoDinamico : listaSubstituicaoDinamico) {
				roteiroFechamento = roteiroFechamento.replaceAll(String.valueOf(campoDinamico.get("campoRelatorio")),
						Matcher.quoteReplacement(String.valueOf(campoDinamico.get("valor")).trim()));
			}
		}

		return roteiroFechamento;

	}

	public List<Map<String, Object>> replaceObrigatoriosPF(List<Map<String, Object>> listaSubstituicaoObrigatorio,
			Map<String, Object> substituicaoObrigatorio, String campoRelatorio, Map<String, Object> parameters) {

		String campoSubstituicaoObrigatorio = StringUtils.EMPTY;

		for (RoteiroNotaCamposObrigatoriosPFEnum campo : RoteiroNotaCamposObrigatoriosPFEnum.values()) {

			if (campo.getCampoObrigatorio().equalsIgnoreCase(campoRelatorio)) {

				campoSubstituicaoObrigatorio = campo.getCampoObrigatorioReplace();
				substituicaoObrigatorio.put("campoRelatorio", campoRelatorio);
				substituicaoObrigatorio.put("campoSubstituicao", campoSubstituicaoObrigatorio);
				substituicaoObrigatorio.put("valor", parameters.get(campoSubstituicaoObrigatorio));
				listaSubstituicaoObrigatorio.add(substituicaoObrigatorio);

			}
		}
		
		return listaSubstituicaoObrigatorio;

	}

	public List<Map<String, Object>> replaceObrigatoriosPJ(List<Map<String, Object>> listaSubstituicaoObrigatorio,
			Map<String, Object> substituicaoObrigatorio, String campoRelatorio, Map<String, Object> parameters) {

		String campoSubstituicaoObrigatorio = StringUtils.EMPTY;

		for (RoteiroNotaCamposObrigatoriosPJEnum campo : RoteiroNotaCamposObrigatoriosPJEnum.values()) {

			if (campo.getCampoObrigatorio().equalsIgnoreCase(campoRelatorio)) {

				campoSubstituicaoObrigatorio = campo.getCampoObrigatorioReplace();
				substituicaoObrigatorio.put("campoRelatorio", campoRelatorio);
				substituicaoObrigatorio.put("campoSubstituicao", campoSubstituicaoObrigatorio);
				substituicaoObrigatorio.put("valor", parameters.get(campoSubstituicaoObrigatorio));
				listaSubstituicaoObrigatorio.add(substituicaoObrigatorio);

			}
		}

		return listaSubstituicaoObrigatorio;

	}

	public List<Map<String, Object>> replaceDinamicos(List<Map<String, Object>> listaSubstituicaoDinamico,
			Map<String, Object> substituicaoDinamico, String campoRelatorio, Map<String, Object> parameters,
			JsonNode relatorioNotaDinamico, List<RoteiroNotaCampoDinamicoDTO> listaCamposDinamicos) {

		String campoSubstituicaoDinamico = StringUtils.EMPTY;
		Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d", Pattern.CASE_INSENSITIVE);

		for (RoteiroNotaCampoDinamicoDTO campoDinamico : listaCamposDinamicos) {

			if (campoRelatorio.contains(campoDinamico.getIdCampoDinamico())) {
				
				campoSubstituicaoDinamico = campoDinamico.getNomeCampoDinamico();
				
				String valor = relatorioNotaDinamico.path(campoSubstituicaoDinamico).asText();
				valor = pattern.matcher(valor).matches() ? formataDataLocalTexto(valor) : valor;
				valor = valor.equals(StringUtils.EMPTY) ? "NÃO SE APLICA" : valor;
				
				substituicaoDinamico.put("campoRelatorio", campoRelatorio);
				substituicaoDinamico.put("campoSubstituicao", campoSubstituicaoDinamico);
				substituicaoDinamico.put("valor", campoSubstituicaoDinamico + ": " + valor);
				listaSubstituicaoDinamico.add(substituicaoDinamico);

			}
		}

		return listaSubstituicaoDinamico;
	}
}