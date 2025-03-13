package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.downloadDocumento;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.responseSucesso;

import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
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
	public SimtrOutputDto documentos(String cpfCnpj) throws Exception {
		SimtrOutputDto simtrOutputDto = null;
		simtrOutputDto = simtrGateway.documentosCpfCnpjConsultar(cpfCnpj);
		return simtrOutputDto;
	}

	@Override
	public Object documentoConsulta(String idDocumento) throws Exception {
		SimtrOutputDto simtrOutputDto = null;
		simtrOutputDto = simtrGateway.documentoByIdConsultar(idDocumento);

		if (StringUtils.isNotEmpty(simtrOutputDto.getBinario()) && StringUtils.isNotEmpty(simtrOutputDto.getTipologia())
				&& StringUtils.isNotEmpty(simtrOutputDto.getExtensao())
				&& StringUtils.isNotEmpty(simtrOutputDto.getMimeType())) {

			return downloadDocumento(simtrOutputDto);

		}

		return responseSucesso(simtrOutputDto);

	}

}
