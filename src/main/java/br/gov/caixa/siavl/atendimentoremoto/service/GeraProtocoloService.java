package br.gov.caixa.siavl.atendimentoremoto.service;

import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloOutputDTO;

public interface GeraProtocoloService {

	GeraProtocoloOutputDTO geraProtocolo(String token, GeraProtocoloInputDTO geraProtocoloInputDTO);

}
