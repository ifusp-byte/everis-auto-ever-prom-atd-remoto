package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.TokenSmsInputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNegocioRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.TokenSmsService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;

@Service
@SuppressWarnings("all")
public class TokenSmsServiceImpl implements TokenSmsService {

	@Autowired
	DataUtils dataUtils;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	@Autowired
	AtendimentoNegocioRepository atendimentoNegocioRepository;

	@Override
	public Object identificacaoTokenSms(TokenSmsInputDto tokenSmsInputDto) {

		Optional<AtendimentoCliente> atendimentoClienteOpt = atendimentoClienteRepository
				.findByProtocolo(Long.parseLong(tokenSmsInputDto.getNumeroProtocolo()));

		AtendimentoCliente atendimentoCliente = null;

		if (atendimentoClienteOpt.isPresent()) {
			atendimentoCliente = atendimentoClienteOpt.get();
		}

		if (Boolean.TRUE.equals(
				Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(tokenSmsInputDto.getAssinaturaToken()))))) {
			if (Boolean.FALSE.equals(Boolean.parseBoolean(String.valueOf(tokenSmsInputDto.getTokenValido())))) {
				atendimentoCliente.setValidacaoTokenAtendimento(2L);
				atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
				atendimentoClienteRepository.save(atendimentoCliente);
				return false;
			}

			atendimentoCliente.setValidacaoTokenAtendimento(1L);
			atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);

		}

		return true;

	}

}
