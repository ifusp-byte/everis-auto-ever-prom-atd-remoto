package br.gov.caixa.siavl.atendimentoremoto.controller.crianota;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.CampoModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.service.impl.RegistroNotaServiceImpl;
import br.gov.caixa.siavl.atendimentoremoto.validation.service.impl.CriaNotaViolacoesServiceImpl;

@SuppressWarnings("all")
class AtendimentoRemotoControllerCriaNotaTest extends ControllerTest {

	@InjectMocks
	private RegistroNotaServiceImpl service;

	@MockitoSpyBean
	ModeloNotaRepository modeloNotaRepository;

	@MockitoSpyBean
	CriaNotaViolacoesServiceImpl criaNotaViolacoesServiceImpl;

	@MockitoSpyBean
	CampoModeloNotaRepositoryImpl campoModeloNotaRepositoryImpl;

	void criaNotaPF1() throws StreamReadException, DatabindException, IOException {
		when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(10003L));
		when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(743L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(anyLong())).thenReturn(Arrays.asList(209L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoTamanho(anyLong()))
				.thenReturn(Arrays.asList(mockDinamicos()));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong()))
				.thenReturn(Arrays.asList(mockDinamicos()));
		when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());

		String BASE_URL = atdremotoUrl + "/notas";
		CriaNotaInputDTO registraNotaInputDto = mapper
				.readValue(new ClassPathResource("/crianota/1criaNotaInputDto.json").getFile(), CriaNotaInputDTO.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(registraNotaInputDto), Object.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	void criaNotaPF2() throws StreamReadException, DatabindException, IOException {

		String BASE_URL = atdremotoUrl + "/notas";

		for (int i = 1; i < 20; i++) {

			when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(10003L));
			when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(743L));
			when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(anyLong()))
					.thenReturn(Arrays.asList(209L));
			when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoTamanho(anyLong()))
					.thenReturn(Arrays.asList(mockDinamicos()));
			when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong()))
					.thenReturn(Arrays.asList(mockDinamicos()));
			when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());

			try {
				CriaNotaInputDTO registraNotaInputDto = mapper.readValue(
						new ClassPathResource("/crianota/" + (i + 1) + "criaNotaInputDto.json").getFile(),
						CriaNotaInputDTO.class);
				ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
						newRequestEntity(registraNotaInputDto), Object.class);

			} catch (Exception e) {
				Assertions.assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value())));
			}

		}
	}

	void criaNotaPF3() throws StreamReadException, DatabindException, IOException {

		String BASE_URL = atdremotoUrl + "/notas";

		when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(9124l));
		when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(1263L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(anyLong())).thenReturn(
				Arrays.asList(168L, 178L, 179L, 193L, 218L, 1960L, 2140L, 2186L, 2972L, 2960L, 3332L, 3225L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoTamanho(anyLong())).thenReturn(mockDinamicosLista());
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong())).thenReturn(mockDinamicosLista());
		when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());

		CriaNotaInputDTO registraNotaInputDto = mapper.readValue(
				new ClassPathResource("/crianota/" + "criaNotaInputDto.json").getFile(), CriaNotaInputDTO.class);
		ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
				newRequestEntity(registraNotaInputDto), Object.class);

	}

	void criaNotaPF4() throws StreamReadException, DatabindException, IOException {

		String BASE_URL = atdremotoUrl + "/notas";

		for (int i = 0; i < 8; i++) {
			when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(9124l));
			when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(1263L));
			when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(anyLong())).thenReturn(
					Arrays.asList(168L, 178L, 179L, 193L, 218L, 1960L, 2140L, 2186L, 2972L, 2960L, 3332L, 3225L));
			when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoTamanho(anyLong()))
					.thenReturn(mockDinamicosLista());
			when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong()))
					.thenReturn(mockDinamicosLista());
			when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());
			try {
				CriaNotaInputDTO registraNotaInputDto = mapper.readValue(
						new ClassPathResource("/crianota/dinamicos/" + (i + 1) + "criaNotaInputDto.json").getFile(),
						CriaNotaInputDTO.class);
				ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
						newRequestEntity(registraNotaInputDto), Object.class);
			} catch (Exception e) {
				Assertions.assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value())));
			}
		}

	}

	void criaNotaPF5() throws StreamReadException, DatabindException, IOException {

		String BASE_URL = atdremotoUrl + "/notas";

		when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(9124l));
		when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(1263L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(anyLong())).thenReturn(
				Arrays.asList(168L, 178L, 179L, 193L, 218L, 1960L, 2140L, 2186L, 2972L, 2960L, 3332L, 3225L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoTamanho(anyLong())).thenReturn(mockDinamicosLista());
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong())).thenReturn(mockDinamicosLista());
		when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());

		try {
			CriaNotaInputDTO registraNotaInputDto = mapper.readValue(
					new ClassPathResource("/crianota/" + (20) + "criaNotaInputDto.json").getFile(),
					CriaNotaInputDTO.class);
			ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
					newRequestEntityError(registraNotaInputDto), Object.class);
		} catch (Exception e) {
			Assertions.assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value())));
		}

	}

	void criaNotaPF6() throws StreamReadException, DatabindException, IOException {

		String BASE_URL = atdremotoUrl + "/notas";

		when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(9124l));
		when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(1263L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(anyLong())).thenReturn(
				Arrays.asList(168L, 178L, 123456L, 193L, 218L, 1960L, 2140L, 2186L, 2972L, 2960L, 3332L, 3225L));
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoObrigatorio(anyLong())).thenReturn(
				Arrays.asList(168L, 179L, 178L, 193L, 218L, 1960L, 2140L, 2186L, 2972L, 2960L, 3332L, 3225L));
		when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());

		try {
			CriaNotaInputDTO registraNotaInputDto = mapper.readValue(
					new ClassPathResource("/crianota/" + "criaNotaInputDto02.json").getFile(), CriaNotaInputDTO.class);
			ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
					newRequestEntity(registraNotaInputDto), Object.class);
		} catch (Exception e) {
			Assertions.assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value())));
		}

	}

	void criaNotaPF7() throws StreamReadException, DatabindException, IOException {

		String BASE_URL = atdremotoUrl + "/notas";

		when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(9124l));
		when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(1263L));
		doReturn(new ArrayList<>()).when(criaNotaViolacoesServiceImpl).violacoesDinamicosFiltro(any());
		doReturn(new ArrayList<>()).when(criaNotaViolacoesServiceImpl).violacoesDinamicosObrigatorio(any());
		doReturn(new ArrayList<>()).when(criaNotaViolacoesServiceImpl).violacoesDinamicosTamanho(any());
		doReturn(new ArrayList<>()).when(criaNotaViolacoesServiceImpl).violacoesDinamicosFormato(any());
		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong())).thenReturn(mockDinamicosLista());
		when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());

		try {
			CriaNotaInputDTO registraNotaInputDto = mapper.readValue(
					new ClassPathResource("/crianota/" + "criaNotaInputDto03.json").getFile(), CriaNotaInputDTO.class);
			ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
					newRequestEntity(registraNotaInputDto), Object.class);
		} catch (Exception e) {
			Assertions.assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value())));
		}

	}

	void criaNotaPF8() throws Exception {

		String BASE_URL = atdremotoUrl + "/notas";

		when(modeloNotaRepository.notaValida(anyLong())).thenReturn(Optional.of(9124l));
		when(modeloNotaRepository.modeloProdutoValido(anyLong(), anyLong())).thenReturn(Optional.of(1263L));
		doReturn(new ArrayList<>()).when(criaNotaViolacoesServiceImpl).violacoesDinamicosFiltro(any());
		doReturn(new ArrayList<>()).when(criaNotaViolacoesServiceImpl).violacoesDinamicosObrigatorio(any());
		doReturn(new ArrayList<>()).when(criaNotaViolacoesServiceImpl).violacoesDinamicosTamanho(any());
		doReturn(new ArrayList<>()).when(criaNotaViolacoesServiceImpl).violacoesDinamicosMascara(any());

		when(campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(anyLong())).thenReturn(mockDinamicosLista2());
		when(modeloNotaRepository.prazoValidade(anyLong())).thenReturn(mockModelo());

		try {
			CriaNotaInputDTO registraNotaInputDto = mapper.readValue(
					new ClassPathResource("/crianota/" + "criaNotaInputDto04.json").getFile(), CriaNotaInputDTO.class);
			ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
					newRequestEntity(registraNotaInputDto), Object.class);
		} catch (Exception e) {
			Assertions.assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.BAD_REQUEST.value())));
		}

	}

	public CamposDinamicosModeloNotaValidacaoDTO mockDinamicos() {
		return CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("209").nomeCampoDinamico("Valor")
				.flagbrigatorio("1").flagTipoDado("M").quantidadeCaracteres("9999").mascaraCampo(null).build();
	}

	public List<CamposDinamicosModeloNotaValidacaoDTO> mockDinamicosLista() {

		List<CamposDinamicosModeloNotaValidacaoDTO> lista = new ArrayList<>();

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("168")
				.nomeCampoDinamico("Tipo de garantia").flagbrigatorio("0").flagTipoDado("T").quantidadeCaracteres("30")
				.mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("178")
				.nomeCampoDinamico("CPF dos beneficiários").flagbrigatorio("1").flagTipoDado("T")
				.quantidadeCaracteres("15").mascaraCampo("999.999.999-99").build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("179")
				.nomeCampoDinamico("Data da primeira prestação").flagbrigatorio("1").flagTipoDado("D")
				.quantidadeCaracteres("10").mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("193")
				.nomeCampoDinamico("Número do contrato").flagbrigatorio("1").flagTipoDado("T")
				.quantidadeCaracteres("23").mascaraCampo("99.9999.9999.999999-99").build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("218")
				.nomeCampoDinamico("Valor da parcela").flagbrigatorio("0").flagTipoDado("M").quantidadeCaracteres("8")
				.mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("1960")
				.nomeCampoDinamico("E-mail Para Recebimento Fatura").flagbrigatorio("0").flagTipoDado("T")
				.quantidadeCaracteres("30").mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("2140")
				.nomeCampoDinamico("Gerente Concessor").flagbrigatorio("0").flagTipoDado("T").quantidadeCaracteres("10")
				.mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("2186").nomeCampoDinamico("CNPJ")
				.flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("19").mascaraCampo("99.999.999/9999-99")
				.build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("2972")
				.nomeCampoDinamico("Estado Civil").flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("10")
				.mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("2960").nomeCampoDinamico("Agro")
				.flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("10").mascaraCampo(null).build());

		lista.add(
				CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("3332").nomeCampoDinamico("tipoFatura")
						.flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("25").mascaraCampo(null).build());

		lista.add(
				CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("3225").nomeCampoDinamico("Campo teste")
						.flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("1").mascaraCampo(null).build());

		return lista;
	}

	public List<CamposDinamicosModeloNotaValidacaoDTO> mockDinamicosLista2() {

		List<CamposDinamicosModeloNotaValidacaoDTO> lista = new ArrayList<>();

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("168")
				.nomeCampoDinamico("Tipo de garantia").flagbrigatorio("0").flagTipoDado("T").quantidadeCaracteres("30")
				.mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("178")
				.nomeCampoDinamico("CPF dos beneficiários").flagbrigatorio("1").flagTipoDado("T")
				.quantidadeCaracteres("15").mascaraCampo("999.999.999-99").build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("179")
				.nomeCampoDinamico("Data da primeira prestação").flagbrigatorio("1").flagTipoDado("D")
				.quantidadeCaracteres("10").mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("193")
				.nomeCampoDinamico("Número do contrato").flagbrigatorio("1").flagTipoDado("N")
				.quantidadeCaracteres("23").mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("218")
				.nomeCampoDinamico("Valor da parcela").flagbrigatorio("0").flagTipoDado("M").quantidadeCaracteres("8")
				.mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("1960")
				.nomeCampoDinamico("E-mail Para Recebimento Fatura").flagbrigatorio("0").flagTipoDado("T")
				.quantidadeCaracteres("30").mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("2140")
				.nomeCampoDinamico("Gerente Concessor").flagbrigatorio("0").flagTipoDado("T").quantidadeCaracteres("10")
				.mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("2186").nomeCampoDinamico("CNPJ")
				.flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("19").mascaraCampo("99.999.999/9999-99")
				.build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("2972")
				.nomeCampoDinamico("Estado Civil").flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("10")
				.mascaraCampo(null).build());

		lista.add(CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("2960").nomeCampoDinamico("Agro")
				.flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("10").mascaraCampo(null).build());

		lista.add(
				CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("3332").nomeCampoDinamico("tipoFatura")
						.flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("25").mascaraCampo(null).build());

		lista.add(
				CamposDinamicosModeloNotaValidacaoDTO.builder().idCampoDinamico("3225").nomeCampoDinamico("Campo teste")
						.flagbrigatorio("1").flagTipoDado("T").quantidadeCaracteres("1").mascaraCampo(null).build());

		return lista;
	}

	public ModeloNotaNegocio mockModelo() {
		return ModeloNotaNegocio.builder().prazoValidade(30).horaValidade("14:14").build();
	}

}
