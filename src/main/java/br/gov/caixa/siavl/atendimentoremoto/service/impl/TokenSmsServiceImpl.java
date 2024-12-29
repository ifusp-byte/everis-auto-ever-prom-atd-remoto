package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REGEX_REPLACE_LETRAS;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.TokenSmsInputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.model.ComentarioAtendimento;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNegocioRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ComentarioAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.TokenSmsService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class TokenSmsServiceImpl implements TokenSmsService {

	@Autowired
	DataUtils dataUtils;
	
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	@Autowired
	AtendimentoNegocioRepository atendimentoNegocioRepository;
	
	@Autowired	
	ComentarioAtendimentoRepository comentarioAtendimentoRepository;

	@Override
	public Object identificacaoTokenSms(String token, TokenSmsInputDto tokenSmsInputDto) {
		
		Long matriculaAtendente = Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll(REGEX_REPLACE_LETRAS, StringUtils.EMPTY));

		Optional<AtendimentoCliente> atendimentoClienteOpt = atendimentoClienteRepository
				.findByProtocolo(Long.parseLong(tokenSmsInputDto.getNumeroProtocolo()));

		AtendimentoCliente atendimentoCliente = null;

		if (atendimentoClienteOpt.isPresent()) {
			atendimentoCliente = atendimentoClienteOpt.get();
		}
		
		ComentarioAtendimento comentarioAtendimento = new ComentarioAtendimento();
		comentarioAtendimento.setMatriculaAtendente(matriculaAtendente);
		comentarioAtendimento.setDataComentario(dataUtils.formataDataBanco());
		comentarioAtendimento.setNumeroProtocolo(Long.parseLong(tokenSmsInputDto.getNumeroProtocolo()));
			
		if (Boolean.TRUE.equals(
				Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(tokenSmsInputDto.getIdentificacaoToken()))))) {
			if (Boolean.FALSE.equals(Boolean.parseBoolean(String.valueOf(tokenSmsInputDto.getTokenValido())))) {
				
				atendimentoCliente.setValidacaoTokenAtendimento(2L);
				atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
				atendimentoClienteRepository.save(atendimentoCliente);
				
				comentarioAtendimento.setDescricaoComentario("Identificação Token Sms. Token Sms validado para o número de telefone: " + tokenSmsInputDto.getTokenTelefone());
				comentarioAtendimentoRepository.save(comentarioAtendimento);
						
				return false;
			}

			atendimentoCliente.setValidacaoTokenAtendimento(1L);
			atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);
			
			comentarioAtendimento.setDescricaoComentario("Identificação Token Sms. Token Sms não validado para o número de telefone: " + tokenSmsInputDto.getTokenTelefone());
			comentarioAtendimentoRepository.save(comentarioAtendimento);
		}

		return true;

	}

}
