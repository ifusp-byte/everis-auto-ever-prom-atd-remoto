package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.gov.caixa.siavl.atendimentoremoto.config.SecurityConfig;
import br.gov.caixa.siavl.atendimentoremoto.filter.JwtAuthenticationFilter;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.gateway.SiipcGateway;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.gateway.SipncGateway;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@SpringBootTest
@SuppressWarnings("all")
class DesafioServiceImplTest {

	@InjectMocks
	private DesafioServiceImpl desafioService;

	@Mock
	private AtendimentoClienteRepository atendimentoClienteRepository;

	@MockitoBean
	AuthenticationConfiguration AuthenticationConfiguration;

	@MockitoBean
	JwtAuthenticationFilter JwtAuthenticationFilter;

	@Mock
	private AtendimentoCliente atendimentoCliente;

	@Mock
	private DocumentoUtils documentoUtils;

	@Mock
	private SiipcGateway siipcGateway;

	@Mock
	private SipncGateway sipncGateway;

	@Mock
	private TokenUtils tokenUtils;

	@MockitoBean
	SecurityConfig SecurityConfig;

	@Mock
	private DataUtils dataUtils;

	@MockitoBean
	HttpSecurity HttpSecurity;

	@Before(value = "")
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl1Test() {
		String tsAtualizacao = "2025-01-23T15:00:00";
		Long tempoEsperado = 10L;
		when(dataUtils.calculaDiferencaDataMinutos(tsAtualizacao)).thenReturn(tempoEsperado);
		Long tempoDesafioMinutos = desafioService.diferencaMinutos(tsAtualizacao);
		assertEquals(Long.valueOf(10), tempoDesafioMinutos);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl2Test() {
		Long tempoDesafioMinutos = 20L;
		Boolean resultado = desafioService.validaTempoDesafio(tempoDesafioMinutos);
		assertTrue(resultado);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl3Test() {
		Long tempoDesafioMinutos = 40L;
		Boolean resultado = desafioService.validaTempoDesafio(tempoDesafioMinutos);
		assertFalse(resultado);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl4Test() {
		Long tempoDesafioMinutos = 60L;
		Boolean resultado = desafioService.validaIntervaloTempoDesafio(tempoDesafioMinutos);
		assertTrue(resultado);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl5Test() {
		Long tempoDesafioMinutos = 130L;
		Boolean resultado = desafioService.validaIntervaloTempoDesafio(tempoDesafioMinutos);
		assertFalse(resultado);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl6Test() {
		String tsAtualizacao = null;
		when(desafioService.diferencaMinutos(tsAtualizacao)).thenReturn(null);
		Long tempoDesafioMinutos = desafioService.diferencaMinutos(tsAtualizacao);
		assertNull(tempoDesafioMinutos);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl7Test() {
		Long tempoDesafioMinutos = 60L;
		Boolean resultado = desafioService.validaIntervaloTempoDesafio(tempoDesafioMinutos);
		assertTrue(resultado);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl8Test() {
		Long tempoDesafioMinutos = 20L;
		Boolean resultado = desafioService.validaIntervaloTempoDesafio(tempoDesafioMinutos);
		assertFalse(resultado);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl9Test() {
		Long tempoDesafioMinutos = 130L;
		Boolean resultado = desafioService.validaIntervaloTempoDesafio(tempoDesafioMinutos);
		assertFalse(resultado);
	}

	@Test
	@Tag("desafio")
	void desafioServiceImpl10Test() {
		String tsAtualizacao = "2025-01-23T12:30:00";
		Long tempoDesafioMinutos = desafioService.diferencaMinutos(tsAtualizacao);
		assertNotNull(tempoDesafioMinutos);
	}

}