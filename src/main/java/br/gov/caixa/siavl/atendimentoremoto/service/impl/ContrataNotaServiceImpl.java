package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gov.caixa.siavl.atendimentoremoto.constants.ContrataNotaServiceConstants;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.ContrataNotaService;

@Service
public class ContrataNotaServiceImpl implements ContrataNotaService {

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;

	public Boolean contrataNota(Long idNegociacao) {

		Boolean statusContratacao = null;

		if (ContrataNotaServiceConstants.STATUS_CONTRATACAO_AUTOMATICA_N
				.equals(validaNotaContratacaoAutomatica(idNegociacao))) {
			
			notaNegociacaoRepository.updateSituacaoNota(idNegociacao);
			statusContratacao = true;

		}

		return statusContratacao;

	}

	public String validaNotaContratacaoAutomatica(Long idNegociacao) {
		
		return String.valueOf(notaNegociacaoRepository.findByContratacaoAutomatica(idNegociacao));

	}

}
