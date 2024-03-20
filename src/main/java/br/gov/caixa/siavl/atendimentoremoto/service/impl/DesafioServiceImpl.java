package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.gateway.AuditoriaPncGateway;
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
	
	private static final String PERSON_TYPE = "PF";
	
	private static final String DEFAULT_USER_IP = "123";


	static Logger logger = Logger.getLogger(DesafioServiceImpl.class.getName());
	
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public CriaDesafioOutputDTO desafioCriar(String token, String cpf) throws Exception {

		HashMap<String, String> criaDesafioMap = new HashMap<String, String>();

		criaDesafioMap.put("cpf", cpf.trim());
		criaDesafioMap.put("nis", StringUtils.EMPTY);
		criaDesafioMap.put("outro-identificador", StringUtils.EMPTY);
		criaDesafioMap.put("nome-servico", NOME_SERVICO);

		return identificacaoPositivaGateway.desafioCriar(token, criaDesafioMap);
	}

	@Override
	public RespondeDesafioOutputDTO desafioResponder(String token, String idDesafio, String respostaDesafio) throws Exception {
		
		Long matriculaAtendente = Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", ""));

		RespondeDesafioInputDTO respondeDesafioInputDTO = new RespondeDesafioInputDTO();
		respondeDesafioInputDTO = RespondeDesafioInputDTO.builder().request(respostaDesafio).build();
		
		RespondeDesafioOutputDTO respondeDesafio = identificacaoPositivaGateway.desafioResponder(token, idDesafio, respondeDesafioInputDTO);
		
		AuditoriaPncDesafioInputDTO auditoriaPncDesafioInputDTO = new AuditoriaPncDesafioInputDTO(); 	
		auditoriaPncDesafioInputDTO = AuditoriaPncDesafioInputDTO.builder()
				.idDesafio(idDesafio)		
				.matriculaAtendente(tokenUtils.getMatriculaFromToken(token))
				.statusValidacao(String.valueOf(respondeDesafio.isStatusCreated()))
				.tipoPessoa(PERSON_TYPE)
				.transacaoSistema("144")
				.build();
				
		String descricaoTransacao = null;

		try {
			descricaoTransacao = mapper.writeValueAsString(auditoriaPncDesafioInputDTO);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();

		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder()
				.descricaoTransacao(descricaoTransacao)
				.ipTerminalUsuario(DEFAULT_USER_IP)
				.nomeMfe("mfe_avl_atendimentoremoto")
				.numeroUnidadeLotacaoUsuario(50)
				.build();

		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
		return respondeDesafio;
	}

}
