package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto.ValidaDesafioOutptDTO;
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
	private static final String STATUS_SUCESSO = "SUCESSO";

	static Logger logger = Logger.getLogger(DesafioServiceImpl.class.getName());

	@Override
	public Object desafioValidar(String token, String cpf) {

		HashMap<String, String> validaDesafioMap = new HashMap<String, String>();
		validaDesafioMap.put("documento", documentoUtils.formataDocumento(cpf));
		// criaDesafioMap.put("nome-servico", NOME_SERVICO);

		ValidaDesafioOutptDTO validaDesafioOutptDTO = siipcGateway.desafioValidar(token, validaDesafioMap);

		if (!CANAL_PNC.equalsIgnoreCase(validaDesafioOutptDTO.getCanal())
				&& STATUS_SUCESSO.equalsIgnoreCase(validaDesafioOutptDTO.getStatus())
				&& dataUtils.menorTrintaMinutos(validaDesafioOutptDTO.getTsAtualizacao())) {

			validaDesafioOutptDTO.setDesafioExpirado(false);
			validaDesafioOutptDTO.setMensagem("A identificação positiva foi realizada pelo bot do WhatsApp em "
					+ dataUtils.formataDataSiipc(validaDesafioOutptDTO.getTsAtualizacao() + "."));

			return validaDesafioOutptDTO;

		}

		validaDesafioOutptDTO.setDesafioExpirado(true);
		validaDesafioOutptDTO.setMensagem("Refaça a IP.");

		return validaDesafioOutptDTO;
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

		AtendimentoCliente atendimentoCliente = atendimentoClienteRepository
				.getReferenceById(Long.parseLong(respostaDesafio.getProtocolo()));

		String tipoDocumento = null;
		String tipoPessoa = null;
		String cpfSocio = null;
		Long cpfCnpjPnc = null;

		if (StringUtils.isBlank(String.valueOf(atendimentoCliente.getCnpjCliente()))
				|| atendimentoCliente.getCnpjCliente() == null) {
			tipoDocumento = DOCUMENT_TYPE_CPF;
			tipoPessoa = PERSON_TYPE_PF;
			cpfSocio = StringUtils.EMPTY;
			cpfCnpjPnc = atendimentoCliente.getCpfCliente();

		} else {
			tipoDocumento = DOCUMENT_TYPE_CNPJ;
			tipoPessoa = PERSON_TYPE_PJ;
			cpfSocio = respostaDesafio.getCpfSocio();
			cpfCnpjPnc = atendimentoCliente.getCnpjCliente();
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

}
