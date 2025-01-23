package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.constants.Constants.TOKEN_VALIDO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaDinamicoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.FluxoAtendimento;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocioFavorito;
import br.gov.caixa.siavl.atendimentoremoto.repository.CampoModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.FluxoAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaFavoritoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RoteiroFechamentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.TipoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.ModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@SpringBootTest
@SuppressWarnings("all")
class ModeloNotaServiceImplTest {

	@Mock
	private DataUtils dataUtils;

	@Mock
	private TokenUtils tokenUtils;

	@MockBean
	private TokenUtils tokenUtilsBean;

	@Mock
	private MetodosUtils metodosUtils;

	@Mock
	private DocumentoUtils documentoUtils;

	@Mock
	private TipoNotaRepository tipoNotaRepository;

	@InjectMocks
	private ModeloNotaServiceImpl modeloNotaService;

	@Mock
	private ModeloNotaRepository modeloNotaRepository;

	@SpyBean
	private ModeloNotaServiceImpl modeloNotaServiceSpy;

	@Mock
	private ModeloNotaRepositoryImpl modeloNotaRepositoryImpl;

	@Mock
	private CampoModeloNotaRepository campoModeloNotaRepository;

	@Mock
	private FluxoAtendimentoRepository fluxoAtendimentoRepository;

	@Mock
	private ModeloNotaFavoritoRepository modeloNotaFavoritoRepository;

	@Mock
	private RoteiroFechamentoNotaRepository roteiroFechamentoNotaRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(tokenUtilsBean.getMatriculaFromToken(TOKEN_VALIDO)).thenReturn("AB12345");
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl1Test() {
		String cpfCnpj = "12345678901";
		List<Object[]> mockModeloNota = new ArrayList<>();
		mockModeloNota.add(new Object[] { 1, 2, "AcaoProduto", 3, "TipoNota" });
		when(documentoUtils.retornaPublicoAlvo(anyString())).thenReturn(123L);
		when(modeloNotaFavoritoRepository.findModeloNota(anyLong())).thenReturn(mockModeloNota);
		when(fluxoAtendimentoRepository.possuiFluxo(anyLong())).thenReturn(Optional.empty());
		List<ModeloNotaOutputDto> result = modeloNotaService.consultaModeloNota(cpfCnpj);
		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(modeloNotaFavoritoRepository).findModeloNota(anyLong());
		verify(documentoUtils).retornaPublicoAlvo(anyString());
		verify(fluxoAtendimentoRepository).possuiFluxo(anyLong());
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl2Test() {
		String cpfCnpj = "12345678901";
		when(documentoUtils.retornaPublicoAlvo(anyString())).thenReturn(123L);
		when(modeloNotaFavoritoRepository.findModeloNota(anyLong())).thenReturn(Collections.emptyList());
		List<ModeloNotaOutputDto> result = modeloNotaService.consultaModeloNota(cpfCnpj);
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(modeloNotaFavoritoRepository).findModeloNota(anyLong());
		verify(documentoUtils).retornaPublicoAlvo(anyString());
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl3Test() {
		String cpfCnpj = "12345678901";
		List<Object[]> mockModeloNotaMaisUtilizada = new ArrayList<>();
		mockModeloNotaMaisUtilizada.add(new Object[] { 1, 2, "AcaoProduto", 3, "TipoNota", "Nome" });
		when(documentoUtils.retornaPublicoAlvo(anyString())).thenReturn(123L);
		when(modeloNotaRepositoryImpl.modeloNotaMaisUtilizada(anyLong())).thenReturn(mockModeloNotaMaisUtilizada);
		List<ModeloNotaOutputDto> result = modeloNotaService.consultaModeloNotaMaisUtilizada(cpfCnpj);
		assertNotNull(result);
		assertFalse(result.isEmpty());
		verify(modeloNotaRepositoryImpl).modeloNotaMaisUtilizada(anyLong());
		verify(documentoUtils).retornaPublicoAlvo(anyString());
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl4Test() {
		Long numeroModeloNota = 12345L;
		when(tokenUtils.getMatriculaFromToken(anyString())).thenReturn("12345");
		Boolean result = modeloNotaService.adicionaModeloNotaFavorita(TOKEN_VALIDO, numeroModeloNota);
		assertTrue(result);
		verify(modeloNotaFavoritoRepository).save(any(ModeloNotaNegocioFavorito.class));
		verify(tokenUtils).getMatriculaFromToken(anyString());
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl5Test() throws Exception {
		Long numeroModeloNota = 123L;
		ModeloNotaDinamicoInputDTO inputDTO = new ModeloNotaDinamicoInputDTO();
		List<Object[]> mockNotaProduto = new ArrayList<>();
		mockNotaProduto.add(new Object[] { "Nota1", "Produto1" });
		when(modeloNotaFavoritoRepository.notaProduto(numeroModeloNota)).thenReturn(mockNotaProduto);
		when(documentoUtils.retornaPublicoAlvo(anyString())).thenReturn(123L);
		when(metodosUtils.writeValueAsString(anyList())).thenReturn("[]");
		Object result = modeloNotaService.modeloNotaDinamico(TOKEN_VALIDO, numeroModeloNota, inputDTO);
		assertNotNull(result);
		assertTrue(result instanceof ModeloNotaDinamicoOutputDTO);
		verify(modeloNotaFavoritoRepository).notaProduto(numeroModeloNota);
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl6Test() {
		List<Object[]> favoritosRepo = new ArrayList<>();
		favoritosRepo.add(new Object[] { "1", "A123", "Produto X", new Date(1L), "Tipo1", "NomeTipo1" });
		when(documentoUtils.retornaPublicoAlvo(anyString())).thenReturn(123L);
		when(fluxoAtendimentoRepository.possuiFluxo(anyLong())).thenReturn(Optional.empty());
		when(modeloNotaFavoritoRepository.findModeloNotaFavorita(anyLong(), anyLong())).thenReturn(favoritosRepo);
		List<ModeloNotaOutputDto> favoritos = new ArrayList<>();
		favoritos.add(ModeloNotaOutputDto.builder().numeroModeloNota("1").numeroAcaoProduto("456")
				.descricaoAcaoProduto("Descricao 1").dataEscolhaFavorito(new Date(1L)).numeroTipoNota("789")
				.nomeTipoNota("Tipo A").build());
		favoritos.add(ModeloNotaOutputDto.builder().numeroModeloNota("124").numeroAcaoProduto("457")
				.descricaoAcaoProduto("Descricao 2").dataEscolhaFavorito(new Date(1L)).numeroTipoNota("790")
				.nomeTipoNota("Tipo B").build());
		favoritos.add(ModeloNotaOutputDto.builder().numeroModeloNota("124").numeroAcaoProduto("457")
				.descricaoAcaoProduto("Descricao 2").dataEscolhaFavorito(new Date(1L)).numeroTipoNota("791")
				.nomeTipoNota("Tipo B").build());
		doAnswer(new Answer<List<ModeloNotaOutputDto>>() {
			@Override
			public List<ModeloNotaOutputDto> answer(InvocationOnMock invocation) throws Throwable {
				invocation.callRealMethod();
				Object arg1 = invocation.getArgument(0);
				Object arg2 = invocation.getArgument(1);
				return favoritos;
			}
		}).when(modeloNotaServiceSpy).consultaModeloNotaFavorita(TOKEN_VALIDO, "AB123456");
		List<ModeloNotaOutputDto> result = modeloNotaServiceSpy.consultaModeloNotaFavorita(TOKEN_VALIDO, "AB123456");
		assertFalse(result.isEmpty());
		assertEquals(3, result.size());
		assertEquals("1", result.get(0).getNumeroModeloNota());
		assertEquals("456", result.get(0).getNumeroAcaoProduto());
		assertEquals("Descricao 1", result.get(0).getDescricaoAcaoProduto());
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl7Test() {
		List<Object[]> favoritosRepo = new ArrayList<>();
		favoritosRepo.add(new Object[] { "1", "A123", "Produto X", new Date(1L), "Tipo1", "NomeTipo1" });
		when(documentoUtils.retornaPublicoAlvo(anyString())).thenReturn(123L);
		when(fluxoAtendimentoRepository.possuiFluxo(anyLong())).thenReturn(Optional.empty());
		when(modeloNotaFavoritoRepository.findModeloNotaFavorita(anyLong(), anyLong())).thenReturn(favoritosRepo);
		List<ModeloNotaOutputDto> favoritos = new ArrayList<>();
		favoritos.add(ModeloNotaOutputDto.builder().numeroModeloNota("123").numeroAcaoProduto("456")
				.descricaoAcaoProduto("Descricao 1").dataEscolhaFavorito(new Date(1L)).numeroTipoNota("789")
				.nomeTipoNota("Tipo A").build());
		doAnswer(new Answer<List<ModeloNotaOutputDto>>() {
			@Override
			public List<ModeloNotaOutputDto> answer(InvocationOnMock invocation) throws Throwable {
				invocation.callRealMethod();
				Object arg1 = invocation.getArgument(0);
				Object arg2 = invocation.getArgument(1);
				return favoritos;
			}
		}).when(modeloNotaServiceSpy).consultaModeloNotaFavorita(TOKEN_VALIDO, "AB123456");
		List<ModeloNotaOutputDto> result = modeloNotaServiceSpy.consultaModeloNotaFavorita(TOKEN_VALIDO, "AB123456");
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl8Test() {
		List<Object[]> favoritos = new ArrayList<>();
		favoritos.add(new Object[] { "1", "A123", "Produto X", new Date(1L), "Tipo1", "NomeTipo1" });
		when(documentoUtils.retornaPublicoAlvo(anyString())).thenReturn(123L);
		when(modeloNotaFavoritoRepository.findModeloNotaFavorita(anyLong(), anyLong())).thenReturn(favoritos);
		when(fluxoAtendimentoRepository.possuiFluxo(anyLong())).thenReturn(Optional.of(new FluxoAtendimento()));
		List<ModeloNotaOutputDto> result = modeloNotaServiceSpy.consultaModeloNotaFavorita(TOKEN_VALIDO, "AB123456");
		assertTrue(result.isEmpty());
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl9Test() {
		Object[] dadosFavoritos = { "1", "A123", "Produto", new Date(1L), "Tipo", "NomeTipo" };
		List<Object[]> favoritos = new ArrayList<>();
		favoritos.add(dadosFavoritos);
		when(modeloNotaFavoritoRepository.findModeloNotaFavorita(123456L, 123456L)).thenReturn(favoritos);
		when(fluxoAtendimentoRepository.possuiFluxo(anyLong())).thenReturn(Optional.of(new FluxoAtendimento()));
		List<ModeloNotaOutputDto> result = modeloNotaServiceSpy.consultaModeloNotaFavorita(TOKEN_VALIDO, "AB123456");
		assertTrue(result.isEmpty());
	}

	@Test
	@Tag("modeloNota")
	void modeloNotaServiceImpl10Test() {
		String cpfCnpj = "12345678901";
		List<Object[]> mockModeloNotaMaisUtilizada = new ArrayList<>();
		when(documentoUtils.retornaPublicoAlvo(anyString())).thenReturn(123L);
		when(modeloNotaRepositoryImpl.modeloNotaMaisUtilizada(anyLong())).thenReturn(mockModeloNotaMaisUtilizada);
		List<ModeloNotaOutputDto> result = modeloNotaService.consultaModeloNotaMaisUtilizada(cpfCnpj);
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(modeloNotaRepositoryImpl).modeloNotaMaisUtilizada(anyLong());
		verify(documentoUtils).retornaPublicoAlvo(anyString());
	}

}
