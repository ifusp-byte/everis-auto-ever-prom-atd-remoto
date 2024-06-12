package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.model.TipoDocumentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.TipoDocumentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.AnexoDocumentoService;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.gateway.SiecmGateway;

@Service
public class AnexoDocumentoServiceImpl implements AnexoDocumentoService {

	@Autowired
	SiecmGateway siecmGateway;

	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository;

	@Override
	public SiecmOutputDto enviaDocumento(String token, String cpfCnpj) throws Exception {
		SiecmOutputDto siecmOutputDto = null;
		siecmOutputDto = siecmGateway.dossieCriar(token, cpfCnpj);
		
		
		
		//siecmOutputDto = siecmGateway.dossieListar(token, cpfCnpj);
		
		
		return siecmOutputDto;
	}

	@Override
	public Object tipoDocumento(String cpfCnpj) throws Exception {
		List<TipoDocumentoCliente> tpoDocumentoClienteLista = new ArrayList<>();
		if (cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim().length() == 11) {
			tpoDocumentoClienteLista = tipoDocumentoRepository.tipoDocumentoPF();
		} else {
			tpoDocumentoClienteLista = tipoDocumentoRepository.tipoDocumentoPJ();
		}
		return tpoDocumentoClienteLista;
	}

}
