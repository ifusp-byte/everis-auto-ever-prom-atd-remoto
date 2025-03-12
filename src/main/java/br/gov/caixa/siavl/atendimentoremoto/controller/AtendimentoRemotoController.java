package br.gov.caixa.siavl.atendimentoremoto.controller;

import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.AUDITORIA_IDENTIFICACAO_POSITIVA;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.BASE_URL;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.CONTA_ATENDIMENTO;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DESAFIO_CRIAR;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DESAFIO_RESPONDER;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DESAFIO_VALIDAR;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DOCUMENTOS_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DOCUMENTOS_SIMTR_CONSULTAR;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DOCUMENTOS_SIMTR_LISTAR;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DOCUMENTO_CONSULTAR;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DOCUMENTO_EXCLUIR;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DOCUMENTO_INCLUIR;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DOCUMENTO_TIPO;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.DOCUMENTO_TIPO_CAMPOS;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MARCA_DOI;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MODELO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MODELO_NOTA_DINAMICO;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MODELO_NOTA_FAVORITA;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MODELO_NOTA_FAVORITA_BY_MODELO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MODELO_NOTA_MAIS_UTILIZADA;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.NOTA_ENVIAR_CLIENTE;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.NOTA_SALVAR_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.PROTOCOLO;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.RELATORIOS;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.TIPO_NOTA;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.TOKEN_SMS;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.AUTHORIZATION;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.BEARER_1;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaIdentificacaoPositivaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaIdentificacaoPositivaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaDocumentoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.TokenSmsInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.gateway.SicliGateway;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.report.dto.ReportInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.report.service.ReportService;
import br.gov.caixa.siavl.atendimentoremoto.service.AnexoDocumentoSiecmService;
import br.gov.caixa.siavl.atendimentoremoto.service.AnexoDocumentoSimtrService;
import br.gov.caixa.siavl.atendimentoremoto.service.DesafioService;
import br.gov.caixa.siavl.atendimentoremoto.service.GeraProtocoloService;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.RegistroNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.TokenSmsService;

@Validated
@RestController
@SuppressWarnings("all")
@RequestMapping(BASE_URL)
@CrossOrigin(origins = "*")
public class AtendimentoRemotoController {

	@Autowired
	SicliGateway sicliGateway;

	@Autowired
	ReportService reportService;

	@Autowired
	DesafioService desafioService;

	@Autowired
	TokenSmsService tokenSmsService;

	@Autowired
	ModeloNotaService modeloNotaService;

	@Autowired
	RegistroNotaService registroNotaService;

	@Autowired
	GeraProtocoloService geraProtocoloService;

	@Autowired
	AnexoDocumentoSiecmService anexoDocumentoSiecmService;

	@Autowired
	AnexoDocumentoSimtrService anexoDocumentoSimtrService;

	@Autowired
	AuditoriaIdentificacaoPositivaService auditoriaIdentificacaoPositivaService;

	@PostMapping(PROTOCOLO)
	public ResponseEntity<Object> geraProtocolo(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @RequestBody GeraProtocoloInputDTO geraProtocoloInputDTO) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(geraProtocoloService.geraProtocolo(getToken(token), geraProtocoloInputDTO));
	}

	@GetMapping(DESAFIO_VALIDAR)
	public ResponseEntity<Object> desafioValidar(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable String cpf) {
		return ResponseEntity.status(HttpStatus.OK).body(desafioService.desafioValidar(getToken(token), cpf));
	}

	@PostMapping(DESAFIO_CRIAR)
	public ResponseEntity<CriaDesafioOutputDTO> desafioCriar(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token, @Valid @PathVariable String cpf,
			@Valid @RequestBody CriaDesafioInputDTO criaDesafioInputDTO) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(desafioService.desafioCriar(getToken(token), cpf, criaDesafioInputDTO));
	}

	@PostMapping(DESAFIO_RESPONDER)
	public ResponseEntity<RespondeDesafioOutputDTO> desafioResponder(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable String idDesafio, @Valid @RequestBody RespondeDesafioInputDTO respostaDesafio) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(desafioService.desafioResponder(getToken(token), idDesafio, respostaDesafio));
	}

	@PostMapping(AUDITORIA_IDENTIFICACAO_POSITIVA)
	public ResponseEntity<Object> auditar(@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @RequestBody AuditoriaIdentificacaoPositivaInputDTO auditoriaIdentificacaoPositivaInputDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(
				auditoriaIdentificacaoPositivaService.auditar(getToken(token), auditoriaIdentificacaoPositivaInputDTO));
	}

	@GetMapping(MODELO_NOTA)
	public ResponseEntity<Object> consultaModeloNota(@Valid @PathVariable String cpfCnpj) {
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloNotaService.consultaModeloNota(cpfCnpj));
	}

	@GetMapping(MODELO_NOTA_FAVORITA)
	public ResponseEntity<Object> consultaModeloNotaFavorita(@Valid @PathVariable String cpfCnpj,
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(modeloNotaService.consultaModeloNotaFavorita(getToken(token), cpfCnpj));
	}

	@PostMapping(MODELO_NOTA_FAVORITA_BY_MODELO_NOTA)
	public ResponseEntity<Object> adicionaModeloNotaFavorita(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable Long numeroModeloNota) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(modeloNotaService.adicionaModeloNotaFavorita(getToken(token), numeroModeloNota));
	}

	@GetMapping(MODELO_NOTA_MAIS_UTILIZADA)
	public ResponseEntity<Object> consultaModeloMaisUtilizada(@Valid @PathVariable String cpfCnpj) {
		return ResponseEntity.status(HttpStatus.OK).body(modeloNotaService.consultaModeloNotaMaisUtilizada(cpfCnpj));
	}

	@PostMapping(MODELO_NOTA_DINAMICO)
	public ResponseEntity<Object> modeloNotaDinamico(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable Long numeroModeloNota,
			@Valid @RequestBody ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
				modeloNotaService.modeloNotaDinamico(getToken(token), numeroModeloNota, modeloNotaDinamicoInputDTO));
	}

	@GetMapping(CONTA_ATENDIMENTO)
	public ResponseEntity<Object> contaAtendimento(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable String cpfCnpj) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(sicliGateway.contaAtendimento(getToken(token), cpfCnpj, true));
	}

	@PostMapping(NOTA_SALVAR_NOTA)
	public ResponseEntity<Object> registraNota(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @RequestBody RegistraNotaInputDto registraNotaInputDto, @Valid @PathVariable Long numeroModeloNota)
			throws Exception {
		return ResponseEntity.status(HttpStatus.OK)
				.body(registroNotaService.registraNota(getToken(token), registraNotaInputDto, numeroModeloNota));
	}

	@PutMapping(NOTA_ENVIAR_CLIENTE)
	public ResponseEntity<Object> contrataNota(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable Long numeroNota, @Valid @RequestBody EnviaClienteInputDto enviaClienteInputDto) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(registroNotaService.enviaCliente(getToken(token), numeroNota, enviaClienteInputDto));
	}

	@PostMapping(DOCUMENTO_INCLUIR)
	public ResponseEntity<Object> enviaDocumento(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable String cpfCnpj, @RequestBody EnviaDocumentoInputDto enviaDocumentoInputDto)
			throws Exception {
		return ResponseEntity.status(HttpStatus.OK)
				.body(anexoDocumentoSiecmService.enviaDocumento(getToken(token), cpfCnpj, enviaDocumentoInputDto));
	}

	@DeleteMapping(DOCUMENTO_EXCLUIR)
	public ResponseEntity<Object> excluiDocumento(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable Long numeroNota, @PathVariable String codGedAnexo) throws Exception {
		return ResponseEntity.status(HttpStatus.OK)
				.body(anexoDocumentoSiecmService.excluiDocumento(getToken(token), numeroNota, codGedAnexo));
	}

	@GetMapping(DOCUMENTO_CONSULTAR)
	public ResponseEntity<Object> consultaDocumento(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable String codGedAnexo) throws Exception {
		return ResponseEntity.status(HttpStatus.OK)
				.body(anexoDocumentoSiecmService.consultaDocumento(getToken(token), codGedAnexo));
	}

	@GetMapping(DOCUMENTO_TIPO)
	public ResponseEntity<Object> tipoDocumento(@Valid @PathVariable String cpfCnpj) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(anexoDocumentoSiecmService.tipoDocumento(cpfCnpj));
	}

	@GetMapping(DOCUMENTO_TIPO_CAMPOS)
	public ResponseEntity<Object> tipoDocumentoCampos(@Valid @PathVariable String codGED) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(anexoDocumentoSiecmService.tipoDocumentoCampos(codGED));
	}

	@GetMapping(DOCUMENTOS_NOTA)
	public ResponseEntity<Object> documentoNota(@Valid @PathVariable Long numeroNota) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(anexoDocumentoSiecmService.documentoNota(numeroNota));
	}

	@GetMapping(MARCA_DOI)
	public ResponseEntity<Object> verificarMarcaDoi(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable String cpfCnpj) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(sicliGateway.verificaMarcaDoi(getToken(token), cpfCnpj));
	}

	@PostMapping(RELATORIOS)
	public Object relatorio(@RequestBody ReportInputDTO reportInputDTO) throws Exception {
		return reportService.relatorio(reportInputDTO);
	}

	@GetMapping(TIPO_NOTA)
	public ResponseEntity<Object> consultaTipoNota() {
		return ResponseEntity.status(HttpStatus.OK).body(modeloNotaService.consultaTipoNota());
	}

	@PostMapping(TOKEN_SMS)
	public Object identificacaoTokenSms(@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@RequestBody TokenSmsInputDto tokenSmsInputDto) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(tokenSmsService.identificacaoTokenSms(getToken(token), tokenSmsInputDto));
	}

	@GetMapping(DOCUMENTOS_SIMTR_LISTAR)
	public ResponseEntity<Object> listaDocumentosSimtr(
			@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable String cpfCnpj) throws Exception {
		return ResponseEntity.status(HttpStatus.OK)
				.body(anexoDocumentoSimtrService.documentos(getToken(token), cpfCnpj));
	}

	@GetMapping(DOCUMENTOS_SIMTR_CONSULTAR)
	public Object documentoConsultarSimtr(@Valid @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @PathVariable String idDocumento) throws Exception {
		return anexoDocumentoSimtrService.documentoConsulta(getToken(token), idDocumento);
	}

	public String getToken(String token) {
		return token.trim().replace(BEARER_1, StringUtils.EMPTY);
	}

}
