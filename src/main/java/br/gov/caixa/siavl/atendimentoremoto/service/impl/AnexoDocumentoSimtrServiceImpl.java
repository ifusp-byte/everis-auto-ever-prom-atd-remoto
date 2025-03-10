package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto.SimtrOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.gateway.SimtrGateway;
import br.gov.caixa.siavl.atendimentoremoto.service.AnexoDocumentoSimtrService;

@Service
@SuppressWarnings("all")
public class AnexoDocumentoSimtrServiceImpl implements AnexoDocumentoSimtrService {

	@Autowired
	SimtrGateway simtrGateway;

	private static final Logger LOG = Logger.getLogger(AnexoDocumentoSimtrServiceImpl.class.getName());

	@Override
	public SimtrOutputDto documentos(String token, String cpfCnpj) throws Exception {
		SimtrOutputDto simtrOutputDto = null;
		simtrOutputDto = simtrGateway.documentosCpfCnpjConsultar(token, cpfCnpj);
		return simtrOutputDto;
	}

}
