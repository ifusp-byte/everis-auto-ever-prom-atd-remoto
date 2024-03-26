package br.gov.caixa.siavl.atendimentoremoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaIdentificacaoPositivaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaIdentificacaoPositivaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.service.ConsultaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.ContrataNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.DesafioService;
import br.gov.caixa.siavl.atendimentoremoto.service.GeraProtocoloService;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;
import br.gov.caixa.siavl.atendimentoremoto.sicli.gateway.SicliGateway;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(AtendimentoRemotoController.BASE_URL)
@SuppressWarnings({ "squid:S6813" })
public class AtendimentoRemotoController {

	public static final String BASE_URL = "/v1/atendimento-remoto";

	@Autowired
	ConsultaNotaService consultaNotaService;

	@Autowired
	ContrataNotaService contrataNotaService;

	@Autowired
	ModeloNotaService modeloNotaService;

	@Autowired
	GeraProtocoloService geraProtocoloService;

	@Autowired
	DesafioService desafioService;

	@Autowired
	AuditoriaIdentificacaoPositivaService auditoriaIdentificacaoPositivaService;

	@Autowired
	SicliGateway sicliGateway;

	@PostMapping("/protocolo")
	public ResponseEntity<GeraProtocoloOutputDTO> geraProtocolo(
			@RequestHeader(value = "token", required = true) String token,
			@RequestBody GeraProtocoloInputDTO geraProtocoloInputDTO) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(geraProtocoloService.geraProtocolo(token, geraProtocoloInputDTO));
	}

	@PostMapping("/desafio-criar/{cpf}")
	public ResponseEntity<CriaDesafioOutputDTO> desafioCriar(
			@RequestHeader(value = "token", required = true) String token, @PathVariable String cpf) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(desafioService.desafioCriar(token, cpf));
	}

	@PostMapping("/desafio-responder/{idDesafio}")
	public ResponseEntity<RespondeDesafioOutputDTO> desafioResponder(
			@RequestHeader(value = "token", required = true) String token, @PathVariable String idDesafio,
			@RequestBody String respostaDesafio) throws Exception {

		return ResponseEntity.status(HttpStatus.OK)
				.body(desafioService.desafioResponder(token, idDesafio, respostaDesafio));

	}

	@PostMapping("/auditoria-identificacao-positiva")
	public ResponseEntity<Object> auditar(@RequestHeader(value = "token", required = true) String token,
			@RequestBody AuditoriaIdentificacaoPositivaInputDTO auditoriaIdentificacaoPositivaInputDTO) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(auditoriaIdentificacaoPositivaService.auditar(token, auditoriaIdentificacaoPositivaInputDTO));

	}

	@GetMapping("/modelo-nota")
	public ResponseEntity<Object> consultaModeloFavorita() {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNota());
	}

	@GetMapping("/modelo-nota-favorita")
	public ResponseEntity<Object> consultaModeloNotaFavorita(
			@RequestHeader(value = "token", required = true) String token) {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNotaFavorita(token));
	}

	@PostMapping("/modelo-nota-favorita/{numeroModeloNota}")
	public ResponseEntity<Object> adicionaModeloNotaFavorita(
			@RequestHeader(value = "token", required = true) String token, @PathVariable Long numeroModeloNota) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(modeloNotaService.adicionaModeloNotaFavorita(token, numeroModeloNota));
	}

	@GetMapping("/modelo-nota-mais-utilizada")
	public ResponseEntity<Object> consultaModeloMaisUtilizada() {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNotaMaisUtilizada());
	}

	@PostMapping("/modelo-nota-dinamico/{numeroModeloNota}")
	public ResponseEntity<Object> modeloNotaDinamico(@RequestHeader(value = "token", required = true) String token,
			@PathVariable Long numeroModeloNota, @RequestBody ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO)
			throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(modeloNotaService.modeloNotaDinamico(token, numeroModeloNota, modeloNotaDinamicoInputDTO));
	}

	@GetMapping("/conta-atendimento/{cpfCnpj}")
	public ResponseEntity<Object> modeloNotaDinamico(@RequestHeader(value = "token", required = true) String token,
			@PathVariable String cpfCnpj) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(sicliGateway.contaAtendimento(token, cpfCnpj));
	}

}
