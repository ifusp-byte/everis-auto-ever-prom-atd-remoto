package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.ValidaDesafioDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.ValidaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.gateway.SiipcGateway;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.gateway.AuditoriaPncGateway;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.DesafioService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class DesafioServiceImpl implements DesafioService {

	@Autowired
	DataUtils dataUtils;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	SiipcGateway siipcGateway;

	@Autowired
	DocumentoUtils documentoUtils;

	@Autowired
	AuditoriaPncGateway sipncGateway;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	private static final String NOME_SERVICO = "81";
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	private static final String DOCUMENT_TYPE_CPF = "CPF";
	private static final String DOCUMENT_TYPE_CNPJ = "CNPJ";
	private static final String IDENTIFICACAO_POSITIVA = "identificação_positiva";
	private static final String CANAL_PNC = "pnc";
	private static final String CANAL_INTERAXA = "06928340000163-1";
	private static final String STATUS_SUCESSO = "SUCESSO";

	static Logger logger = Logger.getLogger(DesafioServiceImpl.class.getName());

	@Override
	public Object desafioValidar(String token, String cpf) {

		HashMap<String, String> validaDesafioMap = new HashMap<String, String>();
		validaDesafioMap.put("documento", documentoUtils.formataDocumento(cpf));

		ValidaDesafioDTO validaDesafioDTO = siipcGateway.desafioValidar(token, validaDesafioMap);
		ValidaDesafioOutputDTO validaDesafioOutputDTO = new ValidaDesafioOutputDTO();

		String tsAtualizacao = validaDesafioDTO.getTsAtualizacao();
		Long tempoDesafioMinutos = tsAtualizacao != null && StringUtils.isNotBlank(tsAtualizacao)
				? diferencaMinutos(tsAtualizacao)
				: null;

		validaDesafioOutputDTO.setTempoUltimoDesafio(String.valueOf(tempoDesafioMinutos) + " minutos atrás");
		validaDesafioOutputDTO.setDataUltimoDesafio(validaDesafioDTO.getTsAtualizacao());
		validaDesafioOutputDTO.setStatusUltimoDesafio(validaDesafioDTO.getStatus());
		validaDesafioOutputDTO.setCanalUltimoDesafio(validaDesafioDTO.getCanal());

		boolean validaTempoDesafio = false;
		boolean validaIntervaloTempoDesafio = false;

		if (tempoDesafioMinutos != null) {
			validaTempoDesafio = validaTempoDesafio(tempoDesafioMinutos);
			validaIntervaloTempoDesafio = validaIntervaloTempoDesafio(tempoDesafioMinutos);
		}

		//if (!CANAL_PNC.equalsIgnoreCase(validaDesafioDTO.getCanal())
		  if (CANAL_INTERAXA.equalsIgnoreCase(validaDesafioDTO.getCanal())
				&& STATUS_SUCESSO.equalsIgnoreCase(validaDesafioDTO.getStatus())) {

			if (validaTempoDesafio) {
				validaDesafioOutputDTO.setDesafioExpirado(false);
				validaDesafioOutputDTO.setMensagem("A identificação positiva foi realizada pelo bot do WhatsApp em "
						+ dataUtils.formataDataSiipcFront(validaDesafioDTO.getTsAtualizacao())
						+ ". Clique no botão Prosseguir para continuar o atendimento.");
			}

			if (validaIntervaloTempoDesafio) {
				validaDesafioOutputDTO.setDesafioExpirado(true);
				validaDesafioOutputDTO.setMensagem(
						"Devido expiração do tempo de 30 minutos é preciso realizar uma nova Identificação Positiva.");
			}

			return validaDesafioOutputDTO;

		}

		validaDesafioOutputDTO.setDesafioExpirado(true);
		validaDesafioOutputDTO.setMensagem(StringUtils.EMPTY);

		return validaDesafioOutputDTO;
	}

	@Override
	public CriaDesafioOutputDTO desafioCriar(String token, String cpf, CriaDesafioInputDTO criaDesafioInputDTO) {

		HashMap<String, String> criaDesafioMap = new HashMap<String, String>();

		criaDesafioMap.put("cpf", documentoUtils.formataDocumento(cpf));
		criaDesafioMap.put("nis", StringUtils.EMPTY);
		criaDesafioMap.put("outro-identificador", StringUtils.EMPTY);
		criaDesafioMap.put("nome-servico", NOME_SERVICO);

		Long cpfSocio = null;
		if (!criaDesafioInputDTO.getCpfSocio().isBlank()) {
			cpfSocio = Long.parseLong(
					Objects.requireNonNull(documentoUtils.formataDocumento(criaDesafioInputDTO.getCpfSocio())));
		}

		Long protocolo = Long.parseLong(criaDesafioInputDTO.getProtocolo().trim());
		return siipcGateway.desafioCriar(token, criaDesafioMap, cpfSocio, protocolo);
	}

	@Override
	public RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio,
			RespondeDesafioInputDTO respostaDesafio) {

		Long cpfCliente = null;
		Long cnpjCliente = null; 
			
		Optional<AtendimentoCliente> atendimentoClienteOpt = atendimentoClienteRepository
				.findByProtocolo(Long.parseLong(respostaDesafio.getProtocolo()));

		if (atendimentoClienteOpt.isPresent()) {
			AtendimentoCliente atendimentoCliente = atendimentoClienteOpt.get();			
			cpfCliente = atendimentoCliente.getCpfCliente();
			cnpjCliente = atendimentoCliente.getCnpjCliente();
		}

		String tipoDocumento = null;
		String tipoPessoa = null;
		String cpfSocio = null;
		Long cpfCnpjPnc = null;

		if (StringUtils.isBlank(String.valueOf(cnpjCliente))
				|| cnpjCliente == null) {
			tipoDocumento = DOCUMENT_TYPE_CPF;
			tipoPessoa = PERSON_TYPE_PF;
			cpfSocio = StringUtils.EMPTY;
			cpfCnpjPnc = cpfCliente;

		} else {
			tipoDocumento = DOCUMENT_TYPE_CNPJ;
			tipoPessoa = PERSON_TYPE_PJ;
			cpfSocio = respostaDesafio.getCpfSocio();
			cpfCnpjPnc = cnpjCliente;
		}

		RespondeDesafioOutputDTO respondeDesafio = siipcGateway.desafioResponder(token, idDesafio, respostaDesafio);
		auditoriaDesafioResponder(respondeDesafio, respostaDesafio, cpfSocio, token, tipoDocumento, cpfCnpjPnc);

		return respondeDesafio;
	}

	public void auditoriaDesafioResponder(RespondeDesafioOutputDTO respondeDesafio,
			RespondeDesafioInputDTO respostaDesafio, String cpfSocio, String token, String tipoDocumento,
			Long cpfCnpjPnc) {

		AuditoriaPncDesafioInputDTO auditoriaPncDesafioInputDTO = new AuditoriaPncDesafioInputDTO();
		auditoriaPncDesafioInputDTO = AuditoriaPncDesafioInputDTO.builder()
				.statusIdentificacaoPositiva(String.valueOf(respondeDesafio.isStatusCreated())).versaoSistema("1.0.0")
				.dataHoraTransacao(metodosUtils.formataData(new Date())).numeroProtocolo(respostaDesafio.getProtocolo())
				.cpfSocio(cpfSocio).build();

		String descricaoEnvioTransacao = null;
		String descricaoTransacao = null;

		descricaoTransacao = metodosUtils.writeValueAsString(IDENTIFICACAO_POSITIVA);
		descricaoEnvioTransacao = Base64.getEncoder()
				.encodeToString(metodosUtils.writeValueAsString(auditoriaPncDesafioInputDTO).getBytes());

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();

		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoEnvioTransacao(descricaoEnvioTransacao)
				.descricaoTransacao(descricaoTransacao).ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe("mfe_avl_atendimentoremoto").ambienteAplicacao("NACIONAL").numeroUnidadeLotacaoUsuario(50L)
				.tipoDocumento(tipoDocumento).numeroIdentificacaoCliente(cpfCnpjPnc).build();

		sipncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);

	}

	public Long diferencaMinutos(String tsAtualizacao) {
		return dataUtils.calculaDiferencaDataMinutos(tsAtualizacao);
	}

	public Boolean validaTempoDesafio(Long tempoDesafioMinutos) {
		return tempoDesafioMinutos <= 30L;
	}

	public Boolean validaIntervaloTempoDesafio(Long tempoDesafioMinutos) {
		return tempoDesafioMinutos > 30L && tempoDesafioMinutos <= 120L;
	}
}
