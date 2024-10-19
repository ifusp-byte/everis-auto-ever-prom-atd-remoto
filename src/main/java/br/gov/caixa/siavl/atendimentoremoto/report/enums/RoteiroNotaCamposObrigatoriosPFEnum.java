package br.gov.caixa.siavl.atendimentoremoto.report.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import br.gov.caixa.siavl.atendimentoremoto.report.constants.RoteiroNotaReportConstants;

public enum RoteiroNotaCamposObrigatoriosPFEnum {
	NOME("!NOME#NOME#", RoteiroNotaReportConstants.NOME_CLIENTE), 
	CPF("!CPF#CPF#", RoteiroNotaReportConstants.CPF_CNPJ), 
	CNPJ("!CNPJ#CNPJ#", RoteiroNotaReportConstants.CNPJ), 
	RAZAO_SOCIAL("!RAZAOSOCIAL#RAZAOSOCIAL#", RoteiroNotaReportConstants.RAZAO_SOCIAL),
	CONTA_ATENDIMENTO("!CONTAATENDIMENTO#CONTAATENDIMENTO#", RoteiroNotaReportConstants.CONTA_ATENDIMENTO);

	private final String campoObrigatorio;
	private final String campoObrigatorioReplace;

	RoteiroNotaCamposObrigatoriosPFEnum(String campoObrigatorio, String campoObrigatorioReplace) {
		this.campoObrigatorio = campoObrigatorio;
		this.campoObrigatorioReplace = campoObrigatorioReplace; 
	}

	public String getCampoObrigatorio() {
		return campoObrigatorio;
	}

	public String getCampoObrigatorioReplace() {
		return campoObrigatorioReplace;
	}
	public static String[] campos() {
		List<String> camposObrigatorios = new ArrayList<>();
		Arrays.stream(RoteiroNotaCamposObrigatoriosPFEnum.values())
				.forEach(c -> camposObrigatorios.add(String.valueOf(c.getCampoObrigatorio())));
		return StringUtils.toStringArray(camposObrigatorios);
	}

}
