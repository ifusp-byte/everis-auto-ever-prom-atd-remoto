package br.gov.caixa.siavl.atendimentoremoto.service.impl;


import java.util.HashMap;
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
import br.gov.caixa.siavl.atendimentoremoto.service.DesafioService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings({"squid:S2293", "squid:S1854", "squid:S6813"})
public class DesafioServiceImpl implements DesafioService {
	
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	IdentificacaoPositivaGateway identificacaoPositivaGateway;
	
	@Autowired
	AuditoriaPncGateway auditoriaPncGateway;

	private static final String NOME_SERVICO = "81";
	
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	
	static Logger logger = Logger.getLogger(DesafioServiceImpl.class.getName());
	
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public CriaDesafioOutputDTO desafioCriar(String token, String cpf, CriaDesafioInputDTO criaDesafioInputDTO) throws Exception {

		HashMap<String, String> criaDesafioMap = new HashMap<String, String>();

		criaDesafioMap.put("cpf", cpf.trim());
		criaDesafioMap.put("nis", StringUtils.EMPTY);
		criaDesafioMap.put("outro-identificador", StringUtils.EMPTY);
		criaDesafioMap.put("nome-servico", NOME_SERVICO);
		
		Long cpfSocio = Long.parseLong(Objects.requireNonNull(criaDesafioInputDTO.getCpfSocio()).replace(".", "").replace("-", "").trim());
		Long protocolo = Long.parseLong(criaDesafioInputDTO.getProtocolo().trim());

		return identificacaoPositivaGateway.desafioCriar(token, criaDesafioMap, cpfSocio, protocolo);
	}

	@Override
	public RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio, RespondeDesafioInputDTO respostaDesafio) throws Exception {
		
		String tipoPessoa = null; 
		if (respostaDesafio.getCpfSocio().isBlank()) {		
			tipoPessoa = PERSON_TYPE_PF;		
		} else {	
			tipoPessoa = PERSON_TYPE_PJ;	
		}
		
		RespondeDesafioOutputDTO respondeDesafio = identificacaoPositivaGateway.desafioResponder(token, idDesafio, respostaDesafio);
		
		AuditoriaPncDesafioInputDTO auditoriaPncDesafioInputDTO = new AuditoriaPncDesafioInputDTO(); 	
		auditoriaPncDesafioInputDTO = AuditoriaPncDesafioInputDTO.builder()
				.idDesafio(idDesafio)		
				.matriculaAtendente(tokenUtils.getMatriculaFromToken(token))
				.statusValidacao(String.valueOf(respondeDesafio.isStatusCreated()))
				.tipoPessoa(tipoPessoa)
				.transacaoSistema("144")
				.build();
				
		String descricaoTransacao = null;

		try {
			descricaoTransacao = mapper.writeValueAsString(auditoriaPncDesafioInputDTO);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();

		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder()
				.descricaoTransacao(descricaoTransacao)
				.ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe("mfe_avl_atendimentoremoto")
				.ambienteAplicacao("NACIONAL")
				.numeroUnidadeLotacaoUsuario(50L)
				.build();

		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
		return respondeDesafio;
	}

}
