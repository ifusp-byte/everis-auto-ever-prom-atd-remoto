package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloOutputDTO;

@SuppressWarnings("all")
public interface GeraProtocoloService {

	Object geraProtocolo(String token, GeraProtocoloInputDTO geraProtocoloInputDTO) throws Exception;

}
