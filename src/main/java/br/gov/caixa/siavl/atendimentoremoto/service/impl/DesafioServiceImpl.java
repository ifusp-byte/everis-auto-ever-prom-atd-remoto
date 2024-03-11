package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.CriaDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto.RespondeDesafioOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.gateway.IdentificacaoPositivaGateway;
import br.gov.caixa.siavl.atendimentoremoto.service.DesafioService;

@Service
@SuppressWarnings({"squid:S2293", "squid:S1854", "squid:S6813"})
public class DesafioServiceImpl implements DesafioService {

	@Autowired
	IdentificacaoPositivaGateway identificacaoPositivaGateway;

	private static final String NOME_SERVICO = "81";

	static Logger logger = Logger.getLogger(DesafioServiceImpl.class.getName());

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

		RespondeDesafioInputDTO respondeDesafioInputDTO = new RespondeDesafioInputDTO();
		respondeDesafioInputDTO = RespondeDesafioInputDTO.builder().request(respostaDesafio).build();
		return identificacaoPositivaGateway.desafioResponder(token, idDesafio, respondeDesafioInputDTO);
	}

}
