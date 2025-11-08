package br.gov.caixa.siavl.autorizacaoenviomsg.controller;

import static br.gov.caixa.siavl.autorizacaoenviomsg.controller.AutorizacaoEnvioMsgControllerEndpoints.AUTORIZACAO;
import static br.gov.caixa.siavl.autorizacaoenviomsg.controller.AutorizacaoEnvioMsgControllerEndpoints.BASE_URL;
import static br.gov.caixa.siavl.autorizacaoenviomsg.controller.AutorizacaoEnvioMsgControllerEndpoints.CREDITOS;
import static br.gov.caixa.siavl.autorizacaoenviomsg.controller.AutorizacaoEnvioMsgControllerEndpoints.SALDO;
import static br.gov.caixa.siavl.autorizacaoenviomsg.util.ConstantsUtils.ALL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.caixa.siavl.autorizacaoenviomsg.service.AutorizacaoService;
import br.gov.caixa.siavl.autorizacaoenviomsg.service.CreditosService;
import br.gov.caixa.siavl.autorizacaoenviomsg.service.SaldoService;

@Validated
@RestController
@RequestMapping(BASE_URL)
@CrossOrigin(origins = ALL)
@SuppressWarnings("all")
public class AutorizacaoEnvioMsgController {

	@Autowired
	AutorizacaoService autorizacaoService;

	@Autowired
	CreditosService creditosService;

	@Autowired
	SaldoService saldoService;

	@GetMapping(AUTORIZACAO)
	public Object autorizacao() throws Exception {
		// return autorizacaoService;
		return null;
	}

	@GetMapping(CREDITOS)
	public Object creditos() throws Exception {
		// return creditosService;
		return null;
	}

	@PostMapping(SALDO)
	public Object saldo() throws Exception {
		// return saldoService;
		return null;
	}

}
