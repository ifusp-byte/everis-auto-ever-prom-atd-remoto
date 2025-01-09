package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REGEX_REPLACE_LETRAS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.TokenSmsInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.TokenSmsOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.AssinaturaNota;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.model.ComentarioAtendimento;
import br.gov.caixa.siavl.atendimentoremoto.repository.AssinaturaNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNegocioRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ComentarioAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.PendenciaAtendimentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RelatorioNotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.NotaNegociacaoRepositoryImpl;
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
	AssinaturaNotaRepository assinaturaNotaRepository;

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	@Autowired
	AtendimentoNegocioRepository atendimentoNegocioRepository;

	@Autowired
	NotaNegociacaoRepositoryImpl notaNegociacaoRepositoryImpl;

	@Autowired
	ComentarioAtendimentoRepository comentarioAtendimentoRepository;

	@Autowired
	RelatorioNotaNegociacaoRepository relatorioNotaNegociacaoRepository;

	@Autowired
	PendenciaAtendimentoNotaRepository pendenciaAtendimentoNotaRepository;

	@Override
	public Object identificacaoTokenSms(String token, TokenSmsInputDto tokenSmsInputDto) {

		Long matriculaAtendente = Long
				.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll(REGEX_REPLACE_LETRAS, StringUtils.EMPTY));

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

		if (Boolean.TRUE.equals(Objects
				.requireNonNull(Boolean.parseBoolean(String.valueOf(tokenSmsInputDto.getIdentificacaoToken()))))) {
			if (Boolean.FALSE.equals(Boolean.parseBoolean(String.valueOf(tokenSmsInputDto.getTokenValido())))) {

				atendimentoCliente.setValidacaoTokenAtendimento(2L);
				atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
				atendimentoClienteRepository.save(atendimentoCliente);

				comentarioAtendimento.setDescricaoComentario(
						"Identificação Token Sms. Token Sms não validado para o número de telefone: "
								+ tokenSmsInputDto.getTokenTelefone());
				comentarioAtendimentoRepository.save(comentarioAtendimento);

				return false;
			}

			atendimentoCliente.setValidacaoTokenAtendimento(1L);
			atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);

			comentarioAtendimento
					.setDescricaoComentario("Identificação Token Sms. Token Sms validado para o número de telefone: "
							+ tokenSmsInputDto.getTokenTelefone());
			comentarioAtendimentoRepository.save(comentarioAtendimento);
		}

		if (Boolean.TRUE.equals(
				Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(tokenSmsInputDto.getAssinaturaToken()))))) {

			Long numeroNota = Long.parseLong(tokenSmsInputDto.getNumeroNota());
			Long numeroProtocolo = Long.parseLong(tokenSmsInputDto.getNumeroProtocolo());

			AssinaturaNota assinaturaNota = new AssinaturaNota();
			assinaturaNota.setNumeroNota(numeroNota);
			assinaturaNota.setCpfClienteAssinante(atendimentoCliente.getCpfCliente());
			assinaturaNota.setTipoAssinatura((char) '1');
			assinaturaNota.setOrigemAssinatura((char) '1');
			assinaturaNotaRepository.save(assinaturaNota);
			pendenciaAtendimentoNotaRepository.inserePendenciaAtendimento(numeroNota);

			notaNegociacaoRepository.assinaNotaCliente(numeroNota);
			relatorioNotaNegociacaoRepository.assinaNotaCliente(numeroNota);

			TokenSmsOutputDto tokenSmsOutputDto = new TokenSmsOutputDto();
			tokenSmsOutputDto.setStatus(true);
			tokenSmsOutputDto.setNotas(notas(numeroProtocolo));
			return tokenSmsOutputDto;

		}

		return true;

	}

	public List<Object> notas(Long numeroProtocolo) {
		List<NotasByProtocoloOutputDTO> notas = notaNegociacaoRepositoryImpl.notasByProtocolo(numeroProtocolo);
		List<NotasByProtocoloOutputDTO> notasTokenSms = notaNegociacaoRepositoryImpl.notasByProtocoloTokenSms(numeroProtocolo);
		List<NotasByProtocoloOutputDTO> notasLista = new ArrayList<>();
		notasLista.addAll(notas);
		notasLista.addAll(notasTokenSms);

		Map<String, NotasByProtocoloOutputDTO> mapaPorGrupo = new HashMap<String, NotasByProtocoloOutputDTO>();

		for (NotasByProtocoloOutputDTO notaByProtocolo : notasLista) {

			String chaveMapa = notaByProtocolo.getNumeroNota();

			if (!mapaPorGrupo.containsKey(chaveMapa)) {

				mapaPorGrupo.put(chaveMapa, notaByProtocolo);
			}
		}

		return new ArrayList<>(mapaPorGrupo.values());
	}

}
