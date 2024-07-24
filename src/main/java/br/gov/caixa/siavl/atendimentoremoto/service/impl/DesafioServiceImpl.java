package br.gov.caixa.siavl.atendimentoremoto.service.impl;


import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.gateway.AuditoriaPncGateway;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.CriaDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.gateway.IdentificacaoPositivaGateway;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.DesafioService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class DesafioServiceImpl implements DesafioService {
	
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	IdentificacaoPositivaGateway identificacaoPositivaGateway;
	
	@Autowired
	AuditoriaPncGateway auditoriaPncGateway;
	
	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	private static final String NOME_SERVICO = "81";
	
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	private static final String DOCUMENT_TYPE_CPF = "CPF";
	private static final String DOCUMENT_TYPE_CNPJ = "CNPJ";
	private static final String IDENTIFICACAO_POSITIVA = "identificação_positiva";
	
	static Logger logger = Logger.getLogger(DesafioServiceImpl.class.getName());
	
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public CriaDesafioOutputDTO desafioCriar(String token, String cpf, CriaDesafioInputDTO criaDesafioInputDTO) throws Exception {

		HashMap<String, String> criaDesafioMap = new HashMap<String, String>();

		criaDesafioMap.put("cpf", cpf.replace(".", "").replace("-", "").trim());
		criaDesafioMap.put("nis", StringUtils.EMPTY);
		criaDesafioMap.put("outro-identificador", StringUtils.EMPTY);
		criaDesafioMap.put("nome-servico", NOME_SERVICO);
		
		Long cpfSocio = null; 
		if (!criaDesafioInputDTO.getCpfSocio().isBlank()) {
		cpfSocio = Long.parseLong(Objects.requireNonNull(criaDesafioInputDTO.getCpfSocio()).replace(".", "").replace("-", "").trim());
		}

		Long protocolo = Long.parseLong(criaDesafioInputDTO.getProtocolo().trim());
		return identificacaoPositivaGateway.desafioCriar(token, criaDesafioMap, cpfSocio, protocolo);
	}

	@Override
	public RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio, RespondeDesafioInputDTO respostaDesafio) throws Exception {
		
		AtendimentoCliente atendimentoCliente = atendimentoClienteRepository.getReferenceById(Long.parseLong(respostaDesafio.getProtocolo()));
		
		String tipoDocumento = null;
		String tipoPessoa = null; 
		String cpfSocio = null; 
		Long cpfCnpjPnc = null; 
		
		if (StringUtils.isBlank(String.valueOf(atendimentoCliente.getCnpjCliente())) || atendimentoCliente.getCnpjCliente() == null) {	
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
		
		RespondeDesafioOutputDTO respondeDesafio = identificacaoPositivaGateway.desafioResponder(token, idDesafio, respostaDesafio);
		
		AuditoriaPncDesafioInputDTO auditoriaPncDesafioInputDTO = new AuditoriaPncDesafioInputDTO(); 	
		auditoriaPncDesafioInputDTO = AuditoriaPncDesafioInputDTO.builder()		
				.statusIdentificacaoPositiva(String.valueOf(respondeDesafio.isStatusCreated()))
				.versaoSistema("1.0.0")
				.dataHoraTransacao(formataData(new Date()))
				.numeroProtocolo(respostaDesafio.getProtocolo())
				.cpfSocio(cpfSocio)
				.build();
				
		String descricaoEnvioTransacao = null;
		String descricaoTransacao = null;

		try {
			descricaoTransacao = mapper.writeValueAsString(IDENTIFICACAO_POSITIVA);
			descricaoEnvioTransacao = Base64.getEncoder()
					.encodeToString(mapper.writeValueAsString(auditoriaPncDesafioInputDTO).getBytes());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();

		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder()
				.descricaoEnvioTransacao(descricaoEnvioTransacao)
				.descricaoTransacao(descricaoTransacao)
				.ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe("mfe_avl_atendimentoremoto")
				.ambienteAplicacao("NACIONAL")
				.numeroUnidadeLotacaoUsuario(50L)
				.tipoDocumento(tipoDocumento)
				.numeroIdentificacaoCliente(cpfCnpjPnc)
				.build();

		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
		return respondeDesafio;
	}
	
	
	private String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

}
