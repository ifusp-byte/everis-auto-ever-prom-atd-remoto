package br.gov.caixa.siavl.atendimentoremoto.controller.report;

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
	void relatorioTestPJTest() throws StreamReadException, DatabindException, IOException {
		relatorio(RELATORIO_PJ);
	}
	
	@Test
	void relatorioTestPFTest() throws StreamReadException, DatabindException, IOException {
		relatorio(RELATORIO_PF);
	}

}
