package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.constants.MessageConstants.CONTRATA_NOTA_SUCESSO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REGEX_REPLACE_LETRAS;
import static br.gov.caixa.siavl.atendimentoremoto.util.DataUtils.formataDataBancoUtc;
import static br.gov.caixa.siavl.atendimentoremoto.util.MessageUtils.getMessage;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.contrataNotaSucessoOutputBuild;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.getToken;
import static br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils.matriculaFromToken;
import static br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils.unidadeFromToken;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.model.NotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RelatorioNotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.ContrataNotaService;
import br.gov.caixa.siavl.atendimentoremoto.validation.service.ContrataNotaViolacoesService;

@Service
@SuppressWarnings("all")
public class ContrataNotaServiceImpl implements ContrataNotaService {

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;

	@Autowired
	AtendimentoNotaRepository atendimentoNotaRepository;

	@Autowired
	EquipeAtendimentoRepository equipeAtendimentoRepository;

	@Autowired
	ContrataNotaViolacoesService contrataNotaViolacoesService;

	@Autowired
	RelatorioNotaNegociacaoRepository relatorioNotaNegociacaoRepository;

	public Object contrataNota(ContrataNotaInputDTO contrataNotaInputDto, String token) throws Exception {

		contrataNotaInputDto.setToken(getToken(token));

		Object response = null;

		response = contrataNotaViolacoesService.violacoesValidar(contrataNotaInputDto);

		if (response != null) {
			return response;
		}

		Long numeroNota = Long.valueOf(String.valueOf(contrataNotaInputDto.getNumeroNota()));
		contrataNota(numeroNota);
		contrataNotaRelatorio(numeroNota);
		historicoAtendimento(contrataNotaInputDto);

		return response = contrataNotaSucessoOutputBuild(HttpStatus.OK.value(),
				getMessage(CONTRATA_NOTA_SUCESSO, String.valueOf(contrataNotaInputDto.getNumeroNota())),
				contrataNotaInputDto);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void contrataNota(Long numeroNota) {
		notaNegociacaoRepository.contrataNota(numeroNota);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void contrataNotaRelatorio(Long numeroNota) {
		relatorioNotaNegociacaoRepository.contrataNota(numeroNota);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void historicoAtendimento(ContrataNotaInputDTO contrataNotaInputDto) {

		String matriculaAtendente = matriculaFromToken(contrataNotaInputDto.getToken()).replaceAll(REGEX_REPLACE_LETRAS,
				StringUtils.EMPTY);
		Long numeroUnidade = Long.parseLong(unidadeFromToken(contrataNotaInputDto.getToken()));
		Long numeroEquipe = equipeAtendimentoRepository.findEquipeByUnidadeSR(numeroUnidade);
		Long numeroNota = Long.valueOf(String.valueOf(contrataNotaInputDto.getNumeroNota()));

		Optional<NotaNegociacao> notaNegociacaoOpt = notaNegociacaoRepository.findByNumeroNota(numeroNota);
		NotaNegociacao notaNegociacao = null;

		if (notaNegociacaoOpt.isPresent()) {

			notaNegociacao = notaNegociacaoOpt.get();
			
			
			atendimentoNotaRepository.insereHistoricoAtendimento(numeroNota, Long.parseLong(matriculaAtendente), numeroEquipe,
					notaNegociacao.getDataCriacaoNota(), formataDataBancoUtc(), Long.parseLong(matriculaAtendente), "CO");
			

			/*
			AtendimentoNota atendimentoNota = new AtendimentoNota();
			atendimentoNota.setNumeroNota(numeroNota);
			atendimentoNota.setMatriculaAtendente(Long.parseLong(matriculaAtendente));
			atendimentoNota.setMatriculaAtendenteConclusao(Long.parseLong(matriculaAtendente));
			atendimentoNota.setDtInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());
			atendimentoNota.setDtConclusaoAtendimentoNota(formataDataBancoUtc());
			atendimentoNota.setNumeroEquipe(numeroEquipe);
			atendimentoNota.setUnidadeDesignada('A');
			atendimentoNota.setAcaoAtendimento("CO");
			atendimentoNotaRepository.save(atendimentoNota);
			*/

		}

	}

}
