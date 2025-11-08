package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.NOTA_SUCESSO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.criaNotaSucessoOutputBuild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.service.CriaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.GeraProtocoloService;
import br.gov.caixa.siavl.atendimentoremoto.service.RegistroNotaService;
import br.gov.caixa.siavl.atendimentoremoto.validation.service.CriaNotaViolacoesService;

@Service
@SuppressWarnings("all")
public class CriaNotaServiceImpl implements CriaNotaService {

	@Autowired
	CriaNotaViolacoesService criaNotaViolacoesService;

	@Autowired
	GeraProtocoloService geraProtocoloService;

	@Autowired
	RegistroNotaService registroNotaService;

	public Object criaNota(CriaNotaInputDTO criaNotaInputDto, String token) throws Exception {

		criaNotaInputDto.setToken(token);

		Object response = null;

		response = criaNotaViolacoesService.violacoesValidar(criaNotaInputDto);

		if (response != null) {
			return response;
		}

		criaNotaInputDto = geraProtocoloService.geraProtocolo(criaNotaInputDto);
		criaNotaInputDto = registroNotaService.registraNota(criaNotaInputDto);
		criaNotaInputDto = registroNotaService.enviaCliente(criaNotaInputDto);

		response = criaNotaSucessoOutputBuild(HttpStatus.OK.value(), getMessage(NOTA_SUCESSO, criaNotaInputDto.getNumeroNota()), criaNotaInputDto);

		return response;

	}

}
