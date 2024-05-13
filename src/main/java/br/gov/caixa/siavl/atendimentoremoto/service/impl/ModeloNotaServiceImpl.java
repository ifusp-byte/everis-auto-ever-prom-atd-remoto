package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoMenuNotaDinamicoCamposOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoMenuNotaDinamicoNotaProdutoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoMenuNotaDinamicoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoMenuNotaNumeroOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocioFavorito;
import br.gov.caixa.siavl.atendimentoremoto.model.NegocioAgenciaVirtual;
import br.gov.caixa.siavl.atendimentoremoto.model.NotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNegocioRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.CampoModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaFavoritoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NegocioAgenciaVirtualRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RoteiroFechamentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;
import br.gov.caixa.siavl.atendimentoremoto.sicli.gateway.SicliGateway;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
public class ModeloNotaServiceImpl implements ModeloNotaService {

	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	@Autowired
	ModeloNotaFavoritoRepository modeloNotaFavoritoRepository;

	@Autowired
	NegocioAgenciaVirtualRepository negocioAgenciaVirtualRepository;

	@Autowired
	AtendimentoNegocioRepository atendimentoNegocioRepository;

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;

	@Autowired
	CampoModeloNotaRepository campoModeloNotaRepository;

	@Autowired
	SicliGateway sicliGateway;

	@Autowired
	RoteiroFechamentoNotaRepository roteiroFechamentoNotaRepository;

	private static ObjectMapper mapper = new ObjectMapper();

	public List<ModeloNotaOutputDto> consultaModeloNota() {
		List<ModeloNotaOutputDto> modelosNota = new ArrayList<>();

		modeloNotaFavoritoRepository.findModeloNota().stream().forEach(modeloNota ->

		{
			ModeloNotaOutputDto modeloNotaOutputDto = null;

			modeloNotaOutputDto = ModeloNotaOutputDto.builder().numeroModeloNota(String.valueOf(modeloNota[0]))
					.numeroAcaoProduto(String.valueOf(modeloNota[1]))
					.descricaoAcaoProduto(String.valueOf(modeloNota[2])).build();
			modelosNota.add(modeloNotaOutputDto);
		});
		return modelosNota;
	}

	public List<ModeloNotaOutputDto> consultaModeloNotaMaisUtilizada() {
		List<ModeloNotaOutputDto> modelosNota = new ArrayList<>();

		modeloNotaFavoritoRepository.findModeloNotaMaisUtilizada().stream().forEach(modeloNota ->

		{
			ModeloNotaOutputDto modeloNotaOutputDto = null;

			modeloNotaOutputDto = ModeloNotaOutputDto.builder().numeroModeloNota(String.valueOf(modeloNota[1]))
					.numeroAcaoProduto(String.valueOf(modeloNota[2]))
					.descricaoAcaoProduto(String.valueOf(modeloNota[3])).build();
			modelosNota.add(modeloNotaOutputDto);
		});
		return modelosNota.subList(0, 5);
	}

	@Override
	public List<ModeloNotaOutputDto> consultaModeloNotaFavorita(String token) {

		Long matriculaAtendente = null;
		matriculaAtendente = matriculaCriacaoNota(token);

		List<ModeloNotaOutputDto> modelosNotaFavorita = new ArrayList<>();
		List<ModeloNotaOutputDto> modelosNotaFavorita2 = new ArrayList<>();
		List<ModeloNotaOutputDto> modelosNotaFavoritaRetorno = new ArrayList<>();
		
		
		if (!modeloNotaFavoritoRepository.findModeloNotaFavorita(matriculaAtendente).isEmpty()) {		
			modeloNotaFavoritoRepository.findModeloNotaFavorita(matriculaAtendente).stream().forEach(modeloNotaFavorita ->

			{
				ModeloNotaOutputDto modeloNotaOutputDto = null;

				modeloNotaOutputDto = ModeloNotaOutputDto.builder().numeroModeloNota(String.valueOf(modeloNotaFavorita[0]))
						.numeroAcaoProduto(String.valueOf(modeloNotaFavorita[1]))
						.descricaoAcaoProduto(String.valueOf(modeloNotaFavorita[2]))
						.dataEscolhaFavorito(formataData(modeloNotaFavorita[3])).build();

				modelosNotaFavorita.add(modeloNotaOutputDto);
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
				
			if (modelosNotaFavorita2.size() >= 5) {			
				modelosNotaFavoritaRetorno = modelosNotaFavorita2.subList(0, 5);
			} else {		
				modelosNotaFavoritaRetorno = modelosNotaFavorita2;
			}
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
		
		ModeloNotaNegocio modeloNotaNegocio = modeloNotaRepository.prazoValidade(numeroModeloNota);	
		Date dataValidade = formataDataValidade(modeloNotaNegocio.getPrazoValidade(), modeloNotaNegocio.getHoraValidade()); 
		
		NegocioAgenciaVirtual negocioAgenciaVirtual = new NegocioAgenciaVirtual();
		negocioAgenciaVirtual.setDataCriacaoNegocio(new Date());
		negocioAgenciaVirtual.setSituacaoNegocio("E".charAt(0));
		negocioAgenciaVirtual = negocioAgenciaVirtualRepository.save(negocioAgenciaVirtual);

		NotaNegociacao notaNegociacao = new NotaNegociacao();
		notaNegociacao.setNumeroNegocio(negocioAgenciaVirtual.getNumeroNegocio());
		notaNegociacao.setNumeroModeloNota(numeroModeloNota);
		notaNegociacao.setDataCriacaoNota(formataDataBanco());
		notaNegociacao.setDataModificacaoNota(formataDataBanco());
		notaNegociacao.setNumeroMatriculaCriacaoNota(matriculaCriacaoNota(token));
		notaNegociacao.setNumeroMatriculaModificacaoNota(matriculaCriacaoNota(token));
		notaNegociacao.setNumeroSituacaoNota(16L); // VERIFICAR
		notaNegociacao.setQtdItemNegociacao(1L);
		notaNegociacao.setIcOrigemNota(1L);
		notaNegociacao.setDataPrazoValidade(dataValidade);

		notaNegociacao = notaNegociacaoRepository.save(notaNegociacao);

		ModeloNotaDinamicoMenuNotaNumeroOutputDTO modeloNotaDinamicoMenuNotaNumeroOutputDTO = new ModeloNotaDinamicoMenuNotaNumeroOutputDTO();
		modeloNotaDinamicoMenuNotaNumeroOutputDTO.setDataModificacao(formataData(new Date()));
		modeloNotaDinamicoMenuNotaNumeroOutputDTO.setNumeroNota(String.valueOf(notaNegociacao.getNumeroNota()));

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
		modeloNotaDinamicoOutputDTO.setMenuNotaDinamico(mapper.writeValueAsString(dinamicos));
		modeloNotaDinamicoOutputDTO.setMenuNotaProduto(notaProdutoLista.get(0));

		Clob roteiro = roteiroFechamentoNotaRepository.roteiro(numeroModeloNota);
		int tamanho = Integer.parseInt(String.valueOf(roteiro.length()));

		modeloNotaDinamicoOutputDTO.setRoteiroFechamento(String.valueOf(roteiro.getSubString(1, tamanho)));

		AtendimentoNegocio atendimentoNegocio = new AtendimentoNegocio();
		atendimentoNegocio.setNumeroProtocolo(Long.parseLong(modeloNotaDinamicoInputDTO.getProtocolo()));
		atendimentoNegocio.setNumeroNegocio(negocioAgenciaVirtual.getNumeroNegocio());
	    atendimentoNegocio = atendimentoNegocioRepository.save(atendimentoNegocio);

		return modeloNotaDinamicoOutputDTO;
	}

	public Long matriculaCriacaoNota(String token) {
		return Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", ""));
	}

	private Date formataData(Object object) {

		Date data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'.0'", locale);
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);

		try {
			data = sdfOut.parse(sdfOut.format(sdfIn.parse(String.valueOf(object))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	private String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}
	
	private Date formataDataBanco() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		return time.getTime();
	}
	
	
	private Date formataDataValidade(int prazoValidade, String horaValidade) {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		time.add(Calendar.DATE, prazoValidade);
		time.add(Calendar.HOUR, Integer.parseInt(horaValidade.substring(0, 1)));
		time.add(Calendar.MINUTE, Integer.parseInt(horaValidade.substring(3, 4)));
		
		return time.getTime();
	}

}
