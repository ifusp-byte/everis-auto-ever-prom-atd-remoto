package br.gov.caixa.siavl.atendimentoremoto.controller.contratanota;

import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.newObjectFromFileContrataNota;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.NotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.CampoModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.NotaNegociacaoRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.service.impl.RegistroNotaServiceImpl;

@SuppressWarnings("all")
class AtendimentoRemotoControllerContrataNotaTest extends ControllerTest {

	@InjectMocks
	private RegistroNotaServiceImpl service;

	@MockitoSpyBean
	ModeloNotaRepository modeloNotaRepository;

	@MockitoSpyBean
	NotaNegociacaoRepository notaNegociacaoRepository;

	@MockitoSpyBean
	NotaNegociacaoRepositoryImpl notaNegociacaoRepositoryImpl;

	@MockitoSpyBean
	CampoModeloNotaRepositoryImpl campoModeloNotaRepositoryImpl;

	void contrataNotaPF1() throws Exception {
		when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(10003L));
		when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(743L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(anyLong())).thenReturn(Arrays.asList(209L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoTamanho(anyLong()))
				.thenReturn(Arrays.asList(mockDinamicos()));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong()))
				.thenReturn(Arrays.asList(mockDinamicos()));
		when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());
		when(notaNegociacaoRepositoryImpl.dadosNota(anyLong())).thenReturn(Arrays.asList(mockContrataNota()));
		when(notaNegociacaoRepository.getReferenceById(anyLong())).thenReturn(mockNotaNegociacao());

		String BASE_URL = atdremotoUrl + "/notas/contratar";

		ContrataNotaInputDTO contrataNotaOK = (ContrataNotaInputDTO) newObjectFromFileContrataNota(
				"/contratanota/1contrataNotaInputDto.json");
		ResponseEntity<Object> responseOK = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(contrataNotaOK), Object.class);
		Assertions.assertEquals(HttpStatus.OK, responseOK.getStatusCode());

	}

	void contrataNotaPF2() throws Exception {
		when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(10003L));
		when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(743L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(anyLong())).thenReturn(Arrays.asList(209L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoTamanho(anyLong()))
				.thenReturn(Arrays.asList(mockDinamicos()));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong()))
				.thenReturn(Arrays.asList(mockDinamicos()));
		when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());
		when(notaNegociacaoRepositoryImpl.dadosNota(anyLong())).thenReturn(Arrays.asList(mockContrataNota()));
		when(notaNegociacaoRepository.getReferenceById(anyLong())).thenReturn(mockNotaNegociacao());

		String BASE_URL = atdremotoUrl + "/notas/contratar";

		for (int i = 1; i < 6; i++) {
			try {
				ContrataNotaInputDTO contrataNotaNOK = (ContrataNotaInputDTO) newObjectFromFileContrataNota(
						"/contratanota/" + (i + 1) + "contrataNotaInputDto.json");
				ResponseEntity<Object> responseNOK = restTemplate.exchange(BASE_URL, HttpMethod.POST,
						newRequestEntity(contrataNotaNOK), Object.class);
			} catch (Exception e) {
				Assertions.assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value())));
			}

		}

	}

	public CamposDinamicosModeloNotaValidacaoDTO mockDinamicos() {
		return CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("209").nomeCampoDinamico("Valor")
				.flagbrigatorio("1").flagTipoDado("M").quantidadeCaracteres("9999").mascaraCampo(null).build();
	}

	public ModeloNotaNegocio mockModelo() {
		return ModeloNotaNegocio.builder().prazoValidade(30).horaValidade("14:14").build();
	}

	public ContrataNotaInputDTO mockContrataNota() {
		return ContrataNotaInputDTO.builder().numeroNota("849390").cpf("51515151522").cnpj(null).valor("1200")
				.numeroProtocolo("613042").numeroSituacaoNota("23").descricaoSituacaoNota("Pendente de Contrataçao")
				.origemNota("AUTOMATIZADA").build();
	}

	public NotaNegociacao mockNotaNegociacao() {
		return NotaNegociacao.builder().dataCriacaoNota(new Date()).build();
	}

}
