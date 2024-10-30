package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoMenuNotaDinamicoCamposOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoMenuNotaDinamicoNotaProdutoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoMenuNotaDinamicoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoMenuNotaNumeroOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.FluxoAtendimento;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocioFavorito;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNegocioRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.CampoModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.FluxoAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaFavoritoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NegocioAgenciaVirtualRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RoteiroFechamentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.ModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class ModeloNotaServiceImpl implements ModeloNotaService {

	@Autowired
	DataUtils dataUtils;

	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	DocumentoUtils documentoUtils;

	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;

	@Autowired
	ModeloNotaRepositoryImpl modeloNotaRepositoryImpl;

	@Autowired
	CampoModeloNotaRepository campoModeloNotaRepository;

	@Autowired
	FluxoAtendimentoRepository fluxoAtendimentoRepository;

	@Autowired
	ModeloNotaFavoritoRepository modeloNotaFavoritoRepository;

	@Autowired
	AtendimentoNegocioRepository atendimentoNegocioRepository;

	@Autowired
	NegocioAgenciaVirtualRepository negocioAgenciaVirtualRepository;

	@Autowired
	RoteiroFechamentoNotaRepository roteiroFechamentoNotaRepository;

	//private static ObjectMapper mapper = new ObjectMapper();

	public List<ModeloNotaOutputDto> consultaModeloNota(String cpfCnpj) {
		List<ModeloNotaOutputDto> modelosNota = new ArrayList<>();
		List<Object[]> findModeloNota = modeloNotaFavoritoRepository.findModeloNota(documentoUtils.retornaPublicoAlvo(cpfCnpj));
		if (!findModeloNota.isEmpty()) {
			findModeloNota.stream().forEach(modeloNota -> {
				ModeloNotaOutputDto modeloNotaOutputDto = null;
				modeloNotaOutputDto = ModeloNotaOutputDto.builder().numeroModeloNota(String.valueOf(modeloNota[0]))
						.numeroAcaoProduto(String.valueOf(modeloNota[1]))
						.descricaoAcaoProduto(String.valueOf(modeloNota[2])).build();
				Optional<FluxoAtendimento> fluxoAtendimento = fluxoAtendimentoRepository
						.possuiFluxo(Long.parseLong(modeloNotaOutputDto.getNumeroModeloNota()));
				if (!fluxoAtendimento.isPresent()) {
					modelosNota.add(modeloNotaOutputDto);
				}
			});
		}
		return modelosNota;
	}

	public List<ModeloNotaOutputDto> consultaModeloNotaMaisUtilizada(String cpfCnpj) {
		List<ModeloNotaOutputDto> modelosNota = new ArrayList<>();
		List<Object[]> findModeloNotaMaisUtilizada = modeloNotaRepositoryImpl
				.modeloNotaMaisUtilizada(documentoUtils.retornaPublicoAlvo(cpfCnpj));
		if (!findModeloNotaMaisUtilizada.isEmpty()) {
			findModeloNotaMaisUtilizada.stream().forEach(modeloNota -> {
				ModeloNotaOutputDto modeloNotaOutputDto = null;
				modeloNotaOutputDto = ModeloNotaOutputDto.builder().numeroModeloNota(String.valueOf(modeloNota[1]))
						.numeroAcaoProduto(String.valueOf(modeloNota[2]))
						.descricaoAcaoProduto(String.valueOf(modeloNota[3])).build();
				modelosNota.add(modeloNotaOutputDto);
			});
		}
		return modelosNota;
	}

	@Override
	public List<ModeloNotaOutputDto> consultaModeloNotaFavorita(String token, String cpfCnpj) {
		Long matriculaAtendente = null;
		matriculaAtendente = matriculaCriacaoNota(token);
		List<ModeloNotaOutputDto> modelosNotaFavorita = new ArrayList<>();
		List<ModeloNotaOutputDto> modelosNotaFavorita2 = new ArrayList<>();
		List<ModeloNotaOutputDto> modelosNotaFavoritaRetorno = new ArrayList<>();
		List<Object[]> findModeloNotaFavorita = modeloNotaFavoritoRepository.findModeloNotaFavorita(matriculaAtendente,
				documentoUtils.retornaPublicoAlvo(cpfCnpj));

		if (!findModeloNotaFavorita.isEmpty()) {
			findModeloNotaFavorita.stream().forEach(modeloNotaFavorita -> {
				ModeloNotaOutputDto modeloNotaOutputDto = null;
				modeloNotaOutputDto = ModeloNotaOutputDto.builder()
						.numeroModeloNota(String.valueOf(modeloNotaFavorita[0]))
						.numeroAcaoProduto(String.valueOf(modeloNotaFavorita[1]))
						.descricaoAcaoProduto(String.valueOf(modeloNotaFavorita[2]))
						.dataEscolhaFavorito(dataUtils.formataDataModelo(modeloNotaFavorita[3])).build();

				Optional<FluxoAtendimento> fluxoAtendimento = fluxoAtendimentoRepository
						.possuiFluxo(Long.parseLong(modeloNotaOutputDto.getNumeroModeloNota()));
				if (!fluxoAtendimento.isPresent()) {
					modelosNotaFavorita.add(modeloNotaOutputDto);
				}
			});
			modelosNotaFavorita2.addAll(modelosNotaFavorita);
			for (ModeloNotaOutputDto favorita : modelosNotaFavorita) {
				for (int i = 0; i < modelosNotaFavorita.size(); i++) {
					if (modelosNotaFavorita.get(i).getNumeroModeloNota().equals(favorita.getNumeroModeloNota())
							&& modelosNotaFavorita.get(i).getNumeroAcaoProduto().equals(favorita.getNumeroAcaoProduto())
							&& modelosNotaFavorita.get(i).getDataEscolhaFavorito()
									.after(favorita.getDataEscolhaFavorito())) {
						modelosNotaFavorita2.remove(favorita);
					}
				}
			}
			modelosNotaFavoritaRetorno = retornoListaFavorito(modelosNotaFavorita2, modelosNotaFavoritaRetorno);
		}
		return modelosNotaFavoritaRetorno;
	}

	public List<ModeloNotaOutputDto> retornoListaFavorito(List<ModeloNotaOutputDto> modelosNotaFavorita2,
			List<ModeloNotaOutputDto> modelosNotaFavoritaRetorno) {

		if (modelosNotaFavorita2.size() >= 5) {
			modelosNotaFavoritaRetorno = modelosNotaFavorita2.subList(0, 5);
		} else {
			modelosNotaFavoritaRetorno = modelosNotaFavorita2;
		}
		return modelosNotaFavoritaRetorno;
	}

	@Override
	public Boolean adicionaModeloNotaFavorita(String token, Long numeroModeloNota) {

		Boolean statusNotaFavorita = null;
		Long matriculaCriacaoNota = null;
		matriculaCriacaoNota = matriculaCriacaoNota(token);

		ModeloNotaNegocioFavorito modeloNotaNegocioFavorito = new ModeloNotaNegocioFavorito();
		modeloNotaNegocioFavorito.setMatriculaFavorito(matriculaCriacaoNota);
		modeloNotaNegocioFavorito.setNumeroModeloNota(numeroModeloNota);
		modeloNotaNegocioFavorito.setDataEscolhaFavorito(new Date());

		modeloNotaFavoritoRepository.save(modeloNotaNegocioFavorito);
		statusNotaFavorita = true;
		return statusNotaFavorita;
	}

	public Object modeloNotaDinamico(String token, Long numeroModeloNota,
			ModeloNotaDinamicoInputDTO modeloNotaDinamicoInputDTO) throws Exception {

		List<ModeloNotaDinamicoMenuNotaDinamicoNotaProdutoOutputDTO> notaProdutoLista = new ArrayList<>();

		modeloNotaFavoritoRepository.notaProduto(numeroModeloNota).stream().forEach(notaProduto -> {

			ModeloNotaDinamicoMenuNotaDinamicoNotaProdutoOutputDTO notaProdutoItem = null;
			notaProdutoItem = ModeloNotaDinamicoMenuNotaDinamicoNotaProdutoOutputDTO.builder()
					.nota(String.valueOf(notaProduto[0])).produto(String.valueOf(notaProduto[1])).build();

			notaProdutoLista.add(notaProdutoItem);
		});

		ModeloNotaDinamicoMenuNotaNumeroOutputDTO modeloNotaDinamicoMenuNotaNumeroOutputDTO = new ModeloNotaDinamicoMenuNotaNumeroOutputDTO();
		modeloNotaDinamicoMenuNotaNumeroOutputDTO.setDataModificacao(dataUtils.formataDataModelo(new Date()));

		List<ModeloNotaDinamicoMenuNotaDinamicoOutputDTO> dinamicos = new ArrayList<>();
		modeloNotaFavoritoRepository.modeloNotaDinamico(numeroModeloNota).stream().forEach(dinamico -> {

			ModeloNotaDinamicoMenuNotaDinamicoOutputDTO modeloDinamico = null;
			modeloDinamico = ModeloNotaDinamicoMenuNotaDinamicoOutputDTO.builder()
					.idModeloNota(String.valueOf(dinamico[0])).idCampoModeloNota(String.valueOf(dinamico[1]))
					.numeroOrdemCampo(String.valueOf(dinamico[2])).nomeCampoModeloNota(String.valueOf(dinamico[3]))
					.campoDefinido(String.valueOf(dinamico[4])).campoEditavel(String.valueOf(dinamico[5]))
					.campoObrigatorio(String.valueOf(dinamico[6])).espacoReservado(String.valueOf(dinamico[7]))
					.tipoDadoCampo(String.valueOf(dinamico[8])).tipoEntradaCampo(String.valueOf(dinamico[9]))
					.descricaoCampo(String.valueOf(dinamico[10])).quantidadeCaracterCampo(String.valueOf(dinamico[11]))
					.valorInicialCampo(String.valueOf(dinamico[12])).mascaraCampo(String.valueOf(dinamico[13])).build();
			dinamicos.add(modeloDinamico);
		});

		dinamicos.stream().forEach(dinamico -> {
			List<ModeloNotaDinamicoMenuNotaDinamicoCamposOutputDTO> options = new ArrayList<>();
			campoModeloNotaRepository.modeloNotaDinamicoCampos(Long.parseLong(dinamico.getIdCampoModeloNota())).stream()
					.forEach(campo -> {
						ModeloNotaDinamicoMenuNotaDinamicoCamposOutputDTO modeloDinamicoCampos = null;
						modeloDinamicoCampos = ModeloNotaDinamicoMenuNotaDinamicoCamposOutputDTO.builder()
								.numeroConteudoCampoMultiplo(String.valueOf(campo[0]))
								.descricaoConteudoCampoMultiplo(String.valueOf(campo[1]))

								.build();

						options.add(modeloDinamicoCampos);
					});
			dinamico.setOptions(options);
		});

		ModeloNotaDinamicoOutputDTO modeloNotaDinamicoOutputDTO = new ModeloNotaDinamicoOutputDTO();
		modeloNotaDinamicoOutputDTO.setMenuNotaNumero(modeloNotaDinamicoMenuNotaNumeroOutputDTO);
		modeloNotaDinamicoOutputDTO.setMenuNotaDinamico(metodosUtils.writeValueAsString(dinamicos));
		modeloNotaDinamicoOutputDTO.setMenuNotaProduto(notaProdutoLista.get(0));

		Optional<List<Clob>> roteiroListaConsulta = roteiroFechamentoNotaRepository.roteiro(numeroModeloNota);
		List<Clob> roteiroRetorno = roteiroListaConsulta.isPresent() ? roteiroListaConsulta.get() : new ArrayList<>();
		Clob roteiro = roteiroRetorno.isEmpty() ? roteiro = null : roteiroRetorno.get(0);

		if (roteiro != null) {
			int tamanho = Integer.parseInt(String.valueOf(roteiro.length()));
			modeloNotaDinamicoOutputDTO.setRoteiroFechamento(String.valueOf(roteiro.getSubString(1, tamanho)));
		}

		return modeloNotaDinamicoOutputDTO;
	}

	public Long matriculaCriacaoNota(String token) {
		return Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", ""));
	}

}
