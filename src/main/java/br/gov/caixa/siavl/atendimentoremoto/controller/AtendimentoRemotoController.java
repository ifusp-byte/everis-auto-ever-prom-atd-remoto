package br.gov.caixa.siavl.atendimentoremoto.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaIdentificacaoPositivaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaIdentificacaoPositivaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaDocumentoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.CriaDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.service.AnexoDocumentoService;
import br.gov.caixa.siavl.atendimentoremoto.service.ConsultaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.ContrataNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.DesafioService;
import br.gov.caixa.siavl.atendimentoremoto.service.GeraProtocoloService;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.RegistroNotaService;
import br.gov.caixa.siavl.atendimentoremoto.sicli.gateway.SicliGateway;

@Validated
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
	RegistroNotaService registroNotaService;

	@Autowired
	AuditoriaIdentificacaoPositivaService auditoriaIdentificacaoPositivaService;

	@Autowired
	AnexoDocumentoService anexoDocumentoService;

	@Autowired
	SicliGateway sicliGateway;

	@PostMapping("/protocolo")
	public ResponseEntity<GeraProtocoloOutputDTO> geraProtocolo(
			@Valid @RequestHeader(value = "token", required = true) String token,
			@Valid @RequestBody GeraProtocoloInputDTO geraProtocoloInputDTO) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(geraProtocoloService.geraProtocolo(token, geraProtocoloInputDTO));
	}

	@PostMapping("/desafio-criar/{cpf}")
	public ResponseEntity<CriaDesafioOutputDTO> desafioCriar(
			@Valid @RequestHeader(value = "token", required = true) String token, @Valid @PathVariable String cpf,
			@Valid @RequestBody CriaDesafioInputDTO criaDesafioInputDTO) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(desafioService.desafioCriar(token, cpf, criaDesafioInputDTO));
	}

	@PostMapping("/desafio-responder/{idDesafio}")
	public ResponseEntity<RespondeDesafioOutputDTO> desafioResponder(
			@Valid @RequestHeader(value = "token", required = true) String token, @Valid @PathVariable String idDesafio,
			@Valid @RequestBody RespondeDesafioInputDTO respostaDesafio) throws Exception {

		return ResponseEntity.status(HttpStatus.OK)
				.body(desafioService.desafioResponder(token, idDesafio, respostaDesafio));

	}

	@PostMapping("/auditoria-identificacao-positiva")
	public ResponseEntity<Object> auditar(@Valid @RequestHeader(value = "token", required = true) String token,
			@Valid @RequestBody AuditoriaIdentificacaoPositivaInputDTO auditoriaIdentificacaoPositivaInputDTO) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(auditoriaIdentificacaoPositivaService.auditar(token, auditoriaIdentificacaoPositivaInputDTO));

	}

	@GetMapping("/modelo-nota")
	public ResponseEntity<Object> consultaModeloFavorita() {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNota());
	}

	@GetMapping("/modelo-nota-favorita")
	public ResponseEntity<Object> consultaModeloNotaFavorita(
			@Valid @RequestHeader(value = "token", required = true) String token) {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNotaFavorita(token));
	}

	@PostMapping("/modelo-nota-favorita/{numeroModeloNota}")
	public ResponseEntity<Object> adicionaModeloNotaFavorita(
			@Valid @RequestHeader(value = "token", required = true) String token,
			@Valid @PathVariable Long numeroModeloNota) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(modeloNotaService.adicionaModeloNotaFavorita(token, numeroModeloNota));
	}

	@GetMapping("/modelo-nota-mais-utilizada")
	public ResponseEntity<Object> consultaModeloMaisUtilizada() {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNotaMaisUtilizada());
	}

	@PostMapping("/modelo-nota-dinamico/{numeroModeloNota}")
	public ResponseEntity<Object> modeloNotaDinamico(
			@Valid @RequestHeader(value = "token", required = true) String token,
			@Valid @PathVariable Long numeroModeloNota,
			@Valid @RequestBody ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
				.body(modeloNotaService.modeloNotaDinamico(token, numeroModeloNota, modeloNotaDinamicoInputDTO));
	}

	@GetMapping("/conta-atendimento/{cpfCnpj}")
	public ResponseEntity<Object> contaAtendimento(@Valid @RequestHeader(value = "token", required = true) String token,
			@Valid @PathVariable String cpfCnpj) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
				.body(sicliGateway.contaAtendimento(token, cpfCnpj, true));
	}

	@PostMapping("/nota/{numeroModeloNota}")
	public ResponseEntity<Object> registraNota(@Valid @RequestHeader(value = "token", required = true) String token,
			@Valid @RequestBody RegistraNotaInputDto registraNotaInputDto, @Valid @PathVariable Long numeroModeloNota)
			throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(registroNotaService.registraNota(token, registraNotaInputDto, numeroModeloNota));
	}

	@PutMapping("/nota/{numeroNota}")
	public ResponseEntity<Object> contrataNota(@Valid @RequestHeader(value = "token", required = true) String token,
			@Valid @PathVariable Long numeroNota, @Valid @RequestBody EnviaClienteInputDto enviaClienteInputDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(registroNotaService.enviaCliente(token, numeroNota, enviaClienteInputDto));
	}

	@PostMapping("/documento/{cpfCnpj}")
	public ResponseEntity<Object> enviaDocumento(@Valid @RequestHeader(value = "token", required = true) String token,
			@Valid @PathVariable String cpfCnpj, @Valid @RequestBody EnviaDocumentoInputDto enviaDocumentoInputDto) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(anexoDocumentoService.enviaDocumento(token, cpfCnpj, enviaDocumentoInputDto));
	}

	@GetMapping("/documento/tipo/{cpfCnpj}")
	public ResponseEntity<Object> tipoDocumento(@Valid @PathVariable String cpfCnpj) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
				.body(anexoDocumentoService.tipoDocumento(cpfCnpj));
	}

	@GetMapping("/documento/tipo/campos/{codGED}")
	public ResponseEntity<Object> tipoDocumentoCampos(@Valid @PathVariable String codGED) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
				.body(anexoDocumentoService.tipoDocumentoCampos(codGED));
	}

}
