package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.caixa.siavl.atendimentoremoto.model.TipoDocumentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.TipoDocumentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.AnexoDocumentoService;
import br.gov.caixa.siavl.atendimentoremoto.siecm.gateway.SiecmGateway;

public class AnexoDocumentoServiceImpl implements AnexoDocumentoService {

	@Autowired
	SiecmGateway siecmGateway;

	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository;

	@Override
	public Boolean enviaDocumento(String token, String cpfCnpj) throws Exception {

		siecmGateway.dossieCriar(token, cpfCnpj);

		return null;
	}

	@Override
	public Object tipoDocumento() throws Exception {
		List<TipoDocumentoCliente> tpoDocumentoClienteLista = new ArrayList<>();
		tpoDocumentoClienteLista = tipoDocumentoRepository.tipoDocumento();
		return tpoDocumentoClienteLista;
	}

}
