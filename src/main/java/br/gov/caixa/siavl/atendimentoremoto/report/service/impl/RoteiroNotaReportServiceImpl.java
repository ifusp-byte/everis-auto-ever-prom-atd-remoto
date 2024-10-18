package br.gov.caixa.siavl.atendimentoremoto.report.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.report.service.RoteiroNotaReportService;
import br.gov.caixa.siavl.atendimentoremoto.repository.RoteiroFechamentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;

@Service
@SuppressWarnings("all")
public class RoteiroNotaReportServiceImpl implements RoteiroNotaReportService{

	@Autowired
	RoteiroFechamentoNotaRepository roteiroFechamentoNotaRepository;

	@Autowired
	MetodosUtils metodosUtils;
	
	public String retornaRoteiroFormatado(Long numeroModeloNota) {
		return localizaRoteiroByModeloNota(numeroModeloNota);
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

	public List<String> localizaCamposRoteiroParaSubstituicao(Long numeroModeloNota) {

		String roteiroByModeloNota = localizaRoteiroByModeloNota(numeroModeloNota);
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
	
	public String replaceObrigatorios() {
		
		
		return null; 
		
	}
}