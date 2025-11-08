package br.gov.caixa.siavl.atendimentoremoto.controller.crianota;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SuppressWarnings("all")
class AtendimentoRemotoControllerCriaNotaTestesTest extends AtendimentoRemotoControllerCriaNotaTest {

	@BeforeEach
	public void beforeEach() {
		setUpTest();
	}

	@Test
	@Tag("crianota")
	void criaNotaPF1Test() throws StreamReadException, DatabindException, IOException {
		criaNotaPF1();
	}

	@Test
	@Tag("crianota")
	void criaNotaPF2Test() throws StreamReadException, DatabindException, IOException {
		criaNotaPF2();
	}

	@Test
	@Tag("crianota")
	void criaNotaPF3Test() throws StreamReadException, DatabindException, IOException {
		criaNotaPF3();
	}

	@Test
	@Tag("crianota")
	void criaNotaPF4Test() throws StreamReadException, DatabindException, IOException {
		criaNotaPF4();
	}

	@Test
	@Tag("crianota")
	void criaNotaPF5Test() throws StreamReadException, DatabindException, IOException {
		criaNotaPF5();
	}

	@Test
	@Tag("crianota")
	void criaNotaPF6Test() throws StreamReadException, DatabindException, IOException {
		criaNotaPF6();
	}

	@Test
	@Tag("crianota")
	void criaNotaPF7Test() throws StreamReadException, DatabindException, IOException {
		criaNotaPF7();
	}

	@Test
	@Tag("crianota")
	void criaNotaPF8Test() throws Exception {
		criaNotaPF8();
	}

}
