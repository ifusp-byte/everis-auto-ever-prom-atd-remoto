package br.gov.caixa.siavl.atendimentoremoto.controller.registronota;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerRegistraNotaTestesTest extends AtendimentoRemotoControllerRegistraNotaTest {

	@BeforeEach
	public void beforeEach() {
		setUpTest();
		setupIntegracao();
	}

	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	@Test
	void registraNotaPFTest() throws StreamReadException, DatabindException, IOException {
		registraNotaPF();
	}

	@Test
	void registraNotaPJTest() throws StreamReadException, DatabindException, IOException {
		registraNotaPJ();
	}

	@Test
	void enviaNotaPJ1Test() throws StreamReadException, DatabindException, IOException {
		enviaNotaPJ1();
	}

	@Test
	void enviaNotaPJ2Test() throws StreamReadException, DatabindException, IOException {
		enviaNotaPJ2();
	}

	@Test
	void enviaNotaPJ3Test() throws StreamReadException, DatabindException, IOException {
		mockNotasByProtocolo();
		enviaNotaPJ3();
	}

	/*
	@Test
	void atualizaXml1Test() throws Exception {
		atualizaXml1Test();
	}

	@Test
	void atualizaXml2Test() throws Exception {
		atualizaXml2Test();
	}

	@Test
	void atualizaXml3Test() throws Exception {
		atualizaXml3Test();
	}
	*/

}
