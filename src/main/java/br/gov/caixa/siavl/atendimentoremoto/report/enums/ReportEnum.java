package br.gov.caixa.siavl.atendimentoremoto.report.enums;

import static br.gov.caixa.siavl.atendimentoremoto.report.constants.ReportConstants.EXTENSION_JASPER;
import static br.gov.caixa.siavl.atendimentoremoto.report.constants.ReportConstants.REOPORTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.enums.GeraProtocoloTipoAtendimentoEnum;
import wiremock.com.google.common.primitives.Ints;

@SuppressWarnings("all")
public enum ReportEnum {

	ROTEIRO_STEP4(0, "roteiro-step4", REOPORTS + "roteiro-step4" + EXTENSION_JASPER);

	private final int codigo;
	private final String nome;
	private final String relatorio;

	ReportEnum(int codigo, String nome, String relatorio) {
		this.codigo = codigo;
		this.nome = nome;
		this.relatorio = relatorio; 
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public static int[] codigos() {
		List<Integer> codigos = new ArrayList<>();
		Arrays.stream(GeraProtocoloTipoAtendimentoEnum.values()).forEach(c -> codigos.add(Integer.parseInt(String.valueOf(c.getCodigo()))));
		return Ints.toArray(codigos);
	}

}
