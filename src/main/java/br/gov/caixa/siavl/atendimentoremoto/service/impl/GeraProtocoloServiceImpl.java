package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.GeraProtocoloRespository;
import br.gov.caixa.siavl.atendimentoremoto.service.GeraProtocoloService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings({"squid:S6813"})
public class GeraProtocoloServiceImpl implements GeraProtocoloService {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	GeraProtocoloRespository geraProtocoloRespository;

	@Override
	public GeraProtocoloOutputDTO geraProtocolo(String token, GeraProtocoloInputDTO geraProtocoloInputDTO) {

		AtendimentoCliente atendimentoCliente = new AtendimentoCliente();

		atendimentoCliente.setMatriculaAtendente(
				Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", "")));
		atendimentoCliente.setCanalAtendimento(geraProtocoloInputDTO.getTipoAtendimento().charAt(0));

		if (geraProtocoloInputDTO.getCpfCnpj().length() == 11) {

			atendimentoCliente.setCpfCliente(Long.parseLong(geraProtocoloInputDTO.getCpfCnpj().trim()));
		} else {
			atendimentoCliente.setCnpjCliente(Long.parseLong(geraProtocoloInputDTO.getCpfCnpj().trim()));
		}

		atendimentoCliente.setDataInicialAtendimento(new Date());
		atendimentoCliente = geraProtocoloRespository.save(atendimentoCliente);

		GeraProtocoloOutputDTO geraProtocoloOutputDTO = new GeraProtocoloOutputDTO();
		geraProtocoloOutputDTO.setStatus(true);
		geraProtocoloOutputDTO.setNumeroProtocolo(String.valueOf(atendimentoCliente.getNumeroProtocolo()));

		return geraProtocoloOutputDTO;
	}

}
