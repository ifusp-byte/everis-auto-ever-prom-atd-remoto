package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TIPO_DOCUMENTO_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TIPO_DOCUMENTO_CPF;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.io.StringWriter;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosRelatorioNotaDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.CamposNotaOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ConteudoCampoMultiploOutPutDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.NegociacaoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ProdutoNotaDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaDto;
import br.gov.caixa.siavl.atendimentoremoto.enums.TipoCampoDinamicoEnum;
import br.gov.caixa.siavl.atendimentoremoto.model.AssinaturaNota;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoNota;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.NegocioAgenciaVirtual;
import br.gov.caixa.siavl.atendimentoremoto.model.NotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.model.RelatorioNotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.repository.AssinaturaNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNegocioRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.CampoModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaFavoritoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NegocioAgenciaVirtualRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.PendenciaAtendimentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RelatorioNotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.CampoModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.service.RegistroNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

@Service
@SuppressWarnings("all")
public class RegistroNotaServiceImpl implements RegistroNotaService {

	@Autowired
	RelatorioNotaNegociacaoRepository relatorioNotaNegociacaoRepository;

	@Autowired
	EquipeAtendimentoRepository equipeAtendimentoRepository;

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	@Autowired
	PendenciaAtendimentoNotaRepository pendenciaAtendimentoNotaRepository;

	@Autowired
	AtendimentoNegocioRepository atendimentoNegocioRepository;

	@Autowired
	NegocioAgenciaVirtualRepository negocioAgenciaVirtualRepository;

	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	@Autowired
	AssinaturaNotaRepository assinaturaNotaRepository;

	@Autowired
	AtendimentoNotaRepository atendimentoNotaRepository;

	@Autowired
	ModeloNotaFavoritoRepository modeloNotaFavoritoRepository;

	@Autowired
	CampoModeloNotaRepository campoModeloNotaRepository;

	@Autowired
	CampoModeloNotaRepositoryImpl campoModeloNotaRepositoryImpl;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	MetodosUtils metodosUtils;

	private static final String ORIGEM_CADASTRO_NOTA_API_REMOTO = "I";
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	private static final String DOCUMENT_TYPE_CPF = "CPF";
	private static final String DOCUMENT_TYPE_CNPJ = "CNPJ";
	private static final String PATTERN_MATRICULA = "[a-zA-Z]";
	private static final String SITUACAO_NOTA = "Aguardando assinatura do cliente";
	private static final String SITUACAO_NOTA_TOKEN = "Pendente de Contratação";
	private static final String ZERO = "0";

	static Logger LOG = Logger.getLogger(RegistroNotaServiceImpl.class.getName());

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public CriaNotaInputDTO registraNota(CriaNotaInputDTO criaNotaInputDto) {

		String tipoPessoa = null;
		String tipoDocumento = null;
		Long cpfCnpjPnc = Long
				.parseLong(String.valueOf(criaNotaInputDto.getCpfCnpj()).replace(".", "").replace("-", "").trim());
		Long numeroUnidade = Long.parseLong(tokenUtils.getUnidadeFromToken(criaNotaInputDto.getToken()));
		Long numeroEquipe = null;
		String cpfCnpj = null;
		Long matriculaAtendente = Long
				.parseLong(tokenUtils.getMatriculaFromToken(criaNotaInputDto.getToken()).replaceAll("[a-zA-Z]", ""));
		boolean statusRetornoSicli = true;
		String numeroProtocolo = criaNotaInputDto.getNumeroProtocolo();
		String numeroContaAtendimento = String.valueOf(criaNotaInputDto.getNumeroConta());
		String valorMeta = String.valueOf(criaNotaInputDto.getValorMeta()).replace("R$", "").replaceAll("\u00A0", "")
				.trim();
		valorMeta = valorMeta.replace(",", ".");

		Long nuUnidade = Long.parseLong(String.valueOf(criaNotaInputDto.getNumeroConta()).substring(0, 4));
		Long nuProduto = Long.parseLong(String.valueOf(criaNotaInputDto.getNumeroConta()).substring(4, 8));
		Long coIdentificacao = Long.parseLong(String.valueOf(criaNotaInputDto.getNumeroConta()).substring(8,
				String.valueOf(criaNotaInputDto.getNumeroConta()).length()));

		numeroEquipe = equipeAtendimentoRepository.findEquipeByUnidadeSR(numeroUnidade);

		ModeloNotaNegocio modeloNotaNegocio = modeloNotaRepository
				.prazoValidade(Long.parseLong(String.valueOf(criaNotaInputDto.getIdModeloNota())));
		Date dataValidade = formataDataValidade(modeloNotaNegocio.getPrazoValidade(),
				modeloNotaNegocio.getHoraValidade());

		NegocioAgenciaVirtual negocioAgenciaVirtual = new NegocioAgenciaVirtual();
		negocioAgenciaVirtual.setDataCriacaoNegocio(new Date());
		negocioAgenciaVirtual.setSituacaoNegocio("E".charAt(0));
		negocioAgenciaVirtual = negocioAgenciaVirtualRepository.save(negocioAgenciaVirtual);

		NotaNegociacao notaNegociacao = new NotaNegociacao();
		RelatorioNotaNegociacao relatorioNotaNegociacao = new RelatorioNotaNegociacao();

		AtendimentoCliente atendimentoCliente = atendimentoClienteRepository
				.getReferenceById(Long.parseLong(criaNotaInputDto.getNumeroProtocolo()));

		if (criaNotaInputDto.getCpfSocio() != null && !String.valueOf(criaNotaInputDto.getCpfSocio()).isBlank()) {
			Long cpfSocio = Long.parseLong(String.valueOf(criaNotaInputDto.getCpfSocio()).replace(".", "")
					.replace("-", "").replace("/", "").trim());
			relatorioNotaNegociacao.setCpf(cpfSocio);
			atendimentoCliente.setCpfCliente(cpfSocio);
			atendimentoCliente = atendimentoClienteRepository.save(atendimentoCliente);
		}

		notaNegociacao.setNumeroNegocio(negocioAgenciaVirtual.getNumeroNegocio());
		notaNegociacao.setNumeroModeloNota(Long.parseLong(String.valueOf(criaNotaInputDto.getIdModeloNota())));
		notaNegociacao.setDataCriacaoNota(formataDataBanco());
		notaNegociacao.setDataModificacaoNota(formataDataBanco());
		notaNegociacao.setNumeroMatriculaCriacaoNota(matriculaCriacaoNota(criaNotaInputDto.getToken()));
		notaNegociacao.setNumeroMatriculaModificacaoNota(matriculaCriacaoNota(criaNotaInputDto.getToken()));
		notaNegociacao.setNumeroSituacaoNota(22L);
		notaNegociacao.setQtdItemNegociacao(1L);
		notaNegociacao.setIcOrigemNota(1L);
		notaNegociacao.setDataPrazoValidade(dataValidade);
		notaNegociacao.setIcOrigemNota(1L);
		notaNegociacao.setNumeroEquipe(numeroEquipe);

		Object objQtdMeta = criaNotaInputDto.getQuantidadeMeta() != null ? criaNotaInputDto.getQuantidadeMeta() : 0;
		String qtdMetaInput = String.valueOf(objQtdMeta);
		Long qtdMeta = ZERO.equalsIgnoreCase(qtdMetaInput) || StringUtils.isBlank(qtdMetaInput) ? 0
				: Long.parseLong(qtdMetaInput.trim());

		notaNegociacao.setQtdItemNegociacao(Long.parseLong(String.valueOf(qtdMeta)));
		notaNegociacao.setValorSolicitadoNota(Double.parseDouble(valorMeta));
		notaNegociacao.setNuUnidade(nuUnidade);
		notaNegociacao.setNuProduto(nuProduto);
		notaNegociacao.setCoIdentificacao(coIdentificacao);
		notaNegociacao.setOrigemCadastroNota(ORIGEM_CADASTRO_NOTA_API_REMOTO);
		notaNegociacao = notaNegociacaoRepository.save(notaNegociacao);

		AtendimentoNegocio atendimentoNegocio = new AtendimentoNegocio();
		atendimentoNegocio.setNumeroProtocolo(Long.parseLong(criaNotaInputDto.getNumeroProtocolo()));
		atendimentoNegocio.setNumeroNegocio(negocioAgenciaVirtual.getNumeroNegocio());
		atendimentoNegocio = atendimentoNegocioRepository.save(atendimentoNegocio);

		AtendimentoNota atendimentoNota = new AtendimentoNota();
		atendimentoNota.setNumeroNota(notaNegociacao.getNumeroNota());
		atendimentoNota.setMatriculaAtendente(matriculaAtendente);
		atendimentoNota.setMatriculaAtendenteConclusao(matriculaAtendente);
		atendimentoNota.setDtInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());
		atendimentoNota.setDtConclusaoAtendimentoNota(formataDataBanco());
		atendimentoNota.setNumeroEquipe(numeroEquipe);
		atendimentoNota.setUnidadeDesignada('A');
		atendimentoNotaRepository.save(atendimentoNota);

		if (String.valueOf(criaNotaInputDto.getTipoDocumento()).equalsIgnoreCase(TIPO_DOCUMENTO_CPF)) {
			cpfCnpj = String.valueOf(criaNotaInputDto.getCpfCnpj()).replace(".", "").replace("-", "").replace("/", "")
					.trim();
			relatorioNotaNegociacao.setCpf(Long.parseLong(cpfCnpj));
			tipoPessoa = PERSON_TYPE_PF;
			tipoDocumento = DOCUMENT_TYPE_CPF;
			criaNotaInputDto.setNomeSocio(StringUtils.EMPTY);
			criaNotaInputDto.setCpfSocio(StringUtils.EMPTY);
		} else {
			cpfCnpj = String.valueOf(criaNotaInputDto.getCpfCnpj()).replace(".", "").replace("-", "").replace("/", "")
					.trim();
			relatorioNotaNegociacao.setCnpj(Long.parseLong(cpfCnpj));
			tipoPessoa = PERSON_TYPE_PJ;
			tipoDocumento = DOCUMENT_TYPE_CNPJ;
		}

		criaNotaInputDto.setNumeroNota(String.valueOf(notaNegociacao.getNumeroNota()));

		RegistraNotaDto registraNotaDto = new RegistraNotaDto();

		registraNotaDto.setCpfCnpj(String.valueOf(criaNotaInputDto.getCpfCnpj()));
		registraNotaDto.setNomeCliente(String.valueOf(criaNotaInputDto.getNomeCliente()));
		registraNotaDto.setNumeroProtocolo(String.valueOf(criaNotaInputDto.getNumeroProtocolo()));
		registraNotaDto.setContaAtendimento(String.valueOf(criaNotaInputDto.getNumeroConta()));
		registraNotaDto.setQuantidadeMeta(String.valueOf(criaNotaInputDto.getQuantidadeMeta()));
		registraNotaDto.setValorMeta(String.valueOf(criaNotaInputDto.getValorMeta()));
		registraNotaDto.setVersaoSistema("1.0.0");

		List<ProdutoNotaDto> produtoNotaLista = new ArrayList<>();

		modeloNotaRepository.modeloNotaProduto(Long.parseLong(String.valueOf(criaNotaInputDto.getIdModeloNota())))
				.stream().forEach(notaProduto -> {
					ProdutoNotaDto notaProdutoItem = null;
					notaProdutoItem = ProdutoNotaDto.builder().tipoNota(String.valueOf(notaProduto[0]).trim())
							.descProduto(String.valueOf(notaProduto[1]).trim())
							.idProduto(Long.parseLong(String.valueOf(notaProduto[2]))).build();
					produtoNotaLista.add(notaProdutoItem);
				});

		if (!produtoNotaLista.isEmpty()) {
			registraNotaDto.setProduto(produtoNotaLista.get(0).getDescProduto());
			registraNotaDto.setAcaoProduto(produtoNotaLista.get(0).getDescProduto());
			relatorioNotaNegociacao.setAcaoProduto(produtoNotaLista.get(0).getDescProduto());
		}

		registraNotaDto.setNumeroNota(criaNotaInputDto.getNumeroNota());

		if (String.valueOf(criaNotaInputDto.getTipoDocumento()).equalsIgnoreCase(TIPO_DOCUMENTO_CNPJ)) {
			registraNotaDto.setRazaoSocial(String.valueOf(criaNotaInputDto.getNomeCliente()));
			registraNotaDto.setCnpj(String.valueOf(criaNotaInputDto.getCpfCnpj()));
			registraNotaDto.setNomeSocio(String.valueOf(criaNotaInputDto.getNomeSocio()));
			registraNotaDto.setCpfSocio(String.valueOf(criaNotaInputDto.getCpfSocio()));
		}

		List<CamposDinamicosRelatorioNotaDto> camposDinamicosRelatorioNota = new ArrayList<>();
		JsonNode body = metodosUtils.readTree(metodosUtils.writeValueAsString(criaNotaInputDto));
		ArrayNode campos = (ArrayNode) body.get("campos");

		if (!campos.isEmpty()) {

			for (JsonNode node : campos) {
				CamposDinamicosRelatorioNotaDto camposDinamicosRelatorioNotaDto = new CamposDinamicosRelatorioNotaDto();
				camposDinamicosRelatorioNotaDto.setId(node.path("id").asText().trim());
				camposDinamicosRelatorioNotaDto
						.setNome(nomeCampoDinamico(Long.valueOf(camposDinamicosRelatorioNotaDto.getId())));
				camposDinamicosRelatorioNotaDto.setValor(node.path("valor").asText().trim());
				camposDinamicosRelatorioNota.add(camposDinamicosRelatorioNotaDto);
			}

			registraNotaDto.setRelatorioNota(camposDinamicosRelatorioNota);
			criaNotaInputDto.setRegistraNotaDto(registraNotaDto);

			atualizarXML(camposDinamicosRelatorioNota,
					Long.parseLong(String.valueOf(criaNotaInputDto.getIdModeloNota())),
					Long.parseLong(String.valueOf(notaNegociacao.getNumeroNota())));

		}

		String dsRelatorioNota = metodosUtils.writeValueAsString(registraNotaDto);
		Clob relatorioNota = metodosUtils.newSerialClob(dsRelatorioNota);

		relatorioNotaNegociacao.setNumeroEquipe(numeroEquipe);
		relatorioNotaNegociacao.setRelatorioNota(relatorioNota);
		relatorioNotaNegociacao.setNumeroNota(notaNegociacao.getNumeroNota());
		relatorioNotaNegociacao.setNumeroProtocolo(Long.parseLong(numeroProtocolo));
		relatorioNotaNegociacao.setNomeCliente(String.valueOf(criaNotaInputDto.getNomeCliente()));
		relatorioNotaNegociacao.setMatriculaAtendente(matriculaAtendente);
		relatorioNotaNegociacao.setMatriculaAlteracao(matriculaAtendente);
		relatorioNotaNegociacao.setProduto(String.valueOf(criaNotaInputDto.getIdProduto()));
		relatorioNotaNegociacao.setSituacaoNota(22L);
		relatorioNotaNegociacao.setDataCriacaoNota(notaNegociacao.getDataCriacaoNota());
		relatorioNotaNegociacao.setInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());
		relatorioNotaNegociacao.setDataValidade(notaNegociacao.getDataPrazoValidade());

		relatorioNotaNegociacaoRepository.save(relatorioNotaNegociacao);

		registraNotaDto.setRelatorioNota(campos);
		criaNotaInputDto.setRegistraNotaDto(registraNotaDto);

		return criaNotaInputDto;

	}

	@Override
	public CriaNotaInputDTO enviaCliente(CriaNotaInputDTO criaNotaInputDto) {

		String numeroContaAtendimento = String.valueOf(criaNotaInputDto.getNumeroConta());
		String numeroProtocolo = criaNotaInputDto.getNumeroProtocolo();
		boolean statusRetornoSicli = true;
		Long matriculaAtendente = Long
				.parseLong(tokenUtils.getMatriculaFromToken(criaNotaInputDto.getToken()).replaceAll("[a-zA-Z]", ""));
		String tipoDocumento = null;
		Boolean statusContratacao = null;
		Long cpfCnpjPnc = Long.parseLong(String.valueOf(criaNotaInputDto.getCpfCnpj()).replace(".", "").replace("-", "")
				.replace("/", "").trim());
		Long nuUnidade = Long.parseLong(String.valueOf(criaNotaInputDto.getNumeroConta()).substring(0, 4));
		Long nuProduto = Long.parseLong(String.valueOf(criaNotaInputDto.getNumeroConta()).substring(4, 8));
		Long coIdentificacao = Long.parseLong(String.valueOf(criaNotaInputDto.getNumeroConta()).substring(8,
				String.valueOf(criaNotaInputDto.getNumeroConta()).length()));

		notaNegociacaoRepository.enviaNotaCliente(Long.parseLong(criaNotaInputDto.getNumeroNota()));
		relatorioNotaNegociacaoRepository.enviaNotaCliente(Long.parseLong(criaNotaInputDto.getNumeroNota()));
		statusContratacao = true;

		if (Boolean.TRUE.equals(statusContratacao)) {

			AtendimentoCliente atendimentoCliente = atendimentoClienteRepository
					.getReferenceById(Long.parseLong(criaNotaInputDto.getNumeroProtocolo()));
			AssinaturaNota assinaturaNota = new AssinaturaNota();
			assinaturaNota.setNumeroNota(Long.parseLong(criaNotaInputDto.getNumeroNota()));
			assinaturaNota.setCpfClienteAssinante(atendimentoCliente.getCpfCliente());
			assinaturaNota.setTipoAssinatura((char) '1');
			assinaturaNota.setOrigemAssinatura((char) '1');
			assinaturaNotaRepository.save(assinaturaNota);
			pendenciaAtendimentoNotaRepository
					.inserePendenciaAtendimento(Long.parseLong(criaNotaInputDto.getNumeroNota()));

		}

		return criaNotaInputDto;
	}

	private void atualizarXML(List<CamposDinamicosRelatorioNotaDto> camposDinamicosRelatorioNota, Long numeroModeloNota,
			Long numeroNota) {
		List<CamposNotaOutputDTO> camposNotas = new ArrayList<>();
		List<?> campos = camposDinamicosRelatorioNota;

		if (!campos.isEmpty()) {
			campos.stream().forEach(campo -> {

				JsonNode relatorio = metodosUtils.readTree(metodosUtils.writeValueAsString(campo));

				modeloNotaFavoritoRepository.modeloNotaDinamico(numeroModeloNota).stream().forEach(dinamico -> {

					String nomeCampo = String.valueOf(dinamico[3]);
					String valorCampoApi = relatorio.get("valor").asText();
					String nomeCampoApi = relatorio.get("nome").asText();

					if (nomeCampo.equalsIgnoreCase(nomeCampoApi)) {

						CamposNotaOutputDTO camposNota = null;
						String texto = valorCampoApi;

						String tipoCampo = String.valueOf(dinamico[8]);

						if (tipoCampo.equals(TipoCampoDinamicoEnum.DATA.getSigla()) && texto.contains("-")) {
							texto = DataUtils.formataDataLocalTexto(texto);
						}

						camposNota = CamposNotaOutputDTO.builder().id(String.valueOf(dinamico[0]))
								.idCampo(String.valueOf(dinamico[1])).ordemCampo(String.valueOf(dinamico[2]))
								.nome(String.valueOf(dinamico[3])).predefinido("1".equals(String.valueOf(dinamico[4])))
								.editavel("1".equals(String.valueOf(dinamico[5])))
								.obrigatorio("1".equals(String.valueOf(dinamico[6])))
								.espacoReservado(String.valueOf(dinamico[7])).tipoCampo(String.valueOf(dinamico[8]))
								.tipoDado(String.valueOf(dinamico[9])).descricao(String.valueOf(dinamico[10]))
								.tamanhoMaximo(String.valueOf(dinamico[11])).valorCampo(texto)
								.mascaraCampo(String.valueOf(dinamico[13])).build();
						camposNotas.add(camposNota);
					}
				});
			});
		}

		camposNotas.stream().forEach(dinamico -> {
			List<ConteudoCampoMultiploOutPutDTO> conteudoCampoMultiplos = new ArrayList<>();
			campoModeloNotaRepository.modeloNotaDinamicoCampos(Long.parseLong(dinamico.getIdCampo())).stream()
					.forEach(campo -> {
						ConteudoCampoMultiploOutPutDTO conteudoCampoMultiplo = null;
						conteudoCampoMultiplo = conteudoCampoMultiplo.builder().id(String.valueOf(campo[0]))
								.descricao(String.valueOf(campo[1])).build();

						conteudoCampoMultiplos.add(conteudoCampoMultiplo);
					});
			dinamico.setConteudoCampoMultiplo(conteudoCampoMultiplos);
		});
		try {

			NegociacaoOutputDTO notaNegociacaoXML = new NegociacaoOutputDTO();
			notaNegociacaoXML.setCamposNota(camposNotas);
			JAXBContext jaxbContext = JAXBContext.newInstance(NegociacaoOutputDTO.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(notaNegociacaoXML, sw);
			String xmlContent = sw.toString();
			String novoXml = xmlContent.replace("<ConteudoCampoMultiploOutPutDTO>", "<conteudoCampoMultiplo>")
					.replace("<CamposNotaOutputDTO>", "<camposNota>")
					.replace("<NegociacaoOutputDTO>", "<notaNegociacao><camposNota>")
					.replace("</NegociacaoOutputDTO>", "</camposNota></notaNegociacao>");
			notaNegociacaoRepository.updateXmlData(novoXml, numeroNota);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
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

	public Long matriculaCriacaoNota(String token) {
		return Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll(PATTERN_MATRICULA, ""));
	}

	private String nomeCampoDinamico(Long idCampoDinamico) {

		String nomeCampoDinamico = StringUtils.EMPTY;

		Optional<CamposDinamicosModeloNotaDTO> campoDinamicoOpt = campoModeloNotaRepositoryImpl
				.campoDinamico(idCampoDinamico);

		if (campoDinamicoOpt.isPresent()) {
			nomeCampoDinamico = campoDinamicoOpt.get().getNomeCampoDinamico();

		}

		return nomeCampoDinamico;

	}

}
