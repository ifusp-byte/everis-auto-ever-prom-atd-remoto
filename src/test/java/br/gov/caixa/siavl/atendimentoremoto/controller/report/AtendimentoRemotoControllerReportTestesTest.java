package br.gov.caixa.siavl.atendimentoremoto.controller.report;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.RELATORIO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.RELATORIO_PF;
import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.RELATORIO_PJ;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerReportTestesTest extends AtendimentoRemotoControllerReportTest {

	@BeforeEach
	public void setUp() {
		setUpTest();
	}

	@Test
	void relatorioPFTest() throws StreamReadException, DatabindException, IOException {
		relatorioPFPJ(RELATORIO_PF);
	}

	@Test
	void relatorioPJTest() throws StreamReadException, DatabindException, IOException {
		relatorioPFPJ(RELATORIO_PJ);
	}

	@Test
	void relatorioTest() throws StreamReadException, DatabindException, IOException {
		relatorio(RELATORIO);
	}
}
