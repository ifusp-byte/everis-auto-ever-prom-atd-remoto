package br.gov.caixa.siavl.atendimentoremoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.caixa.siavl.atendimentoremoto.service.ConsultaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.ContrataNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(AtendimentoRemotoController.BASE_URL)
public class AtendimentoRemotoController {

	public static final String BASE_URL = "/v1/atendimento-remoto";

	@Autowired
	ConsultaNotaService consultaNotaService;

	@Autowired
	ContrataNotaService contrataNotaService;

	@Autowired
	ModeloNotaService modeloNotaService;

	/*
	@GetMapping
	public ResponseEntity<Object> consultaNota(@RequestHeader(value = "token", required = true) String token) {
		return ResponseEntity.status(HttpStatus.CREATED).body(consultaNotaService.consultaNota(token));
	}

	@PutMapping("/contrata-nota/{idNegociacao}")
	public ResponseEntity<Object> contrataNota(@PathVariable Long idNegociacao) {
		return ResponseEntity.status(HttpStatus.CREATED).body(contrataNotaService.contrataNota(idNegociacao));
	}
	*/
	
	@GetMapping("/modelo-nota")
	public ResponseEntity<Object> consultaModeloFavorita() {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNota());
	}
	
	@GetMapping("/modelo-nota-favorita")
	public ResponseEntity<Object> consultaModeloNotaFavorita(@RequestHeader(value = "token", required = true) String token) {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNotaFavorita(token));
	}
	
	@PostMapping("/modelo-nota-favorita/{numeroModeloNota}")
	public ResponseEntity<Object> adicionaModeloNotaFavorita(@RequestHeader(value = "token", required = true) String token, @PathVariable Long numeroModeloNota) {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.adicionaModeloNotaFavorita(token, numeroModeloNota));
	}
	
	@GetMapping("/modelo-nota-mais-utilizada")
	public ResponseEntity<Object> consultaModeloMaisUtilizada() {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNotaMaisUtilizada());
	}


}
