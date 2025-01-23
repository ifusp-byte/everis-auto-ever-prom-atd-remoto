package br.gov.caixa.siavl.atendimentoremoto.controller.desafio;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.service.impl.DesafioServiceImpl;

@SuppressWarnings("all")
class AtendimentoRemotoControllerDesafioTestes1Test extends AtendimentoRemotoControllerDesafioTest {
	
	@SpyBean
	DesafioServiceImpl desafioServiceImpl;

	@BeforeEach
	public void setUp() {
		setUpTest();
		setupIntegracao(HttpStatus.CREATED.value(), "siipcDesafioValidarSucesso1.json");
	}

	@AfterEach
	public void tearDownTest() throws Exception {
		tearDownIntegracao();
	}

	@Test
	@Tag("desafio")
	void desafioValidar1Test() throws StreamReadException, DatabindException, IOException {
		doAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				invocation.callRealMethod();
				invocation.getArgument(0);
				Long tempoDesafioMinutos = 25L;
				return true;
			}
		}).when(desafioServiceImpl).validaTempoDesafio(anyLong());
		desafioValidar();
	}
	
	@Test
	@Tag("desafio")
	void desafioValidar2Test() throws StreamReadException, DatabindException, IOException {
		doAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				invocation.callRealMethod();
				invocation.getArgument(0);
				Long tempoDesafioMinutos = 30L;
				return true;
			}
		}).when(desafioServiceImpl).validaTempoDesafio(anyLong());
		desafioValidar();
	}
	
	@Test
	@Tag("desafio")
	void desafioValidar3Test() throws StreamReadException, DatabindException, IOException {
		doAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				invocation.callRealMethod();
				invocation.getArgument(0);
				Long tempoDesafioMinutos = 20L;
				return false;
			}
		}).when(desafioServiceImpl).validaIntervaloTempoDesafio(anyLong());
		desafioValidar();
	}
	
	@Test
	@Tag("desafio")
	void desafioValidar4Test() throws StreamReadException, DatabindException, IOException {
		doAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				invocation.callRealMethod();
				invocation.getArgument(0);
				Long tempoDesafioMinutos = 120L;
				return true;
			}
		}).when(desafioServiceImpl).validaIntervaloTempoDesafio(anyLong());
		desafioValidar();
	}
	
	@Test
	@Tag("desafio")
	void desafioValidar5Test() throws StreamReadException, DatabindException, IOException {
		doAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				invocation.callRealMethod();
				invocation.getArgument(0);
				Long tempoDesafioMinutos = 250L;
				return true;
			}
		}).when(desafioServiceImpl).validaIntervaloTempoDesafio(anyLong());
		desafioValidar();
	}

}
