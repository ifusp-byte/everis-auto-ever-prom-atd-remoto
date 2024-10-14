package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.AMBIENTE_NACIONAL;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.INVALIDO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.NAO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.NOME_MFE_AVL_ATENDIMENTOREMOTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PONTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REGEX_REPLACE_LETRAS;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.SIM;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TRACO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.VALIDO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ZERO_CHAR;

import java.io.StringWriter;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialClob;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaEnviaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaEnviaNotaTokenService;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.CamposNotaOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ConteudoCampoMultiploOutPutDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.NegociacaoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.NotasByProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncEnviaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncEnviaNotaTokenInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncRegistraNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.gateway.AuditoriaPncGateway;
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
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.NotaNegociacaoRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.service.RegistroNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class RegistroNotaServiceImpl implements RegistroNotaService {

	@Autowired
	DataUtils dataUtils;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	DocumentoUtils documentoUtils;

	@Autowired
	AuditoriaPncGateway auditoriaPncGateway;

	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;

	@Autowired
	AssinaturaNotaRepository assinaturaNotaRepository;

	@Autowired
	AtendimentoNotaRepository atendimentoNotaRepository;

	@Autowired
	AuditoriaEnviaNotaService auditoriaEnviaNotaService;

	@Autowired
	EquipeAtendimentoRepository equipeAtendimentoRepository;

	@Autowired
	AtendimentoNegocioRepository atendimentoNegocioRepository;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;
	
	@Autowired
	NotaNegociacaoRepositoryImpl notaNegociacaoRepositoryImpl;

	@Autowired
	AuditoriaRegistraNotaService auditoriaRegistraNotaService;

	@Autowired
	AuditoriaEnviaNotaTokenService auditoriaEnviaNotaTokenService;

	@Autowired
	NegocioAgenciaVirtualRepository negocioAgenciaVirtualRepository;

	@Autowired
	RelatorioNotaNegociacaoRepository relatorioNotaNegociacaoRepository;

	@Autowired
	PendenciaAtendimentoNotaRepository pendenciaAtendimentoNotaRepository;

	@Autowired
	ModeloNotaFavoritoRepository modeloNotaFavoritoRepository;

	@Autowired
	CampoModeloNotaRepository campoModeloNotaRepository;

	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	private static final String DOCUMENT_TYPE_CPF = "CPF";
	private static final String DOCUMENT_TYPE_CNPJ = "CNPJ";
	private static final String ORIGEM_CADASTRO_NOTA_MFE = "P";
	private static final String SITUACAO_NOTA_TOKEN_VALIDO = "Pendente de Contratacao";
	private static final String SITUACAO_NOTA_TOKEN_INVALIDO = "Em atendimento Agencia";
	private static final String STEP3_COMPONENTE_TOKEN = "step3_componente_token";
	private static final String SITUACAO_NOTA = "Aguardando assinatura do cliente";
	private static final String STEP2_REALIZAR_NEGOCIO = "step2_realizar_negocio";
	private static final String STEP4_COMPROVANTE_ASSINAR_PELO_APP = "step4_comprovante_assinar_pelo_app";
	private static final String STEP3_COMPONENTE_TOKEN_ERRO_SUBMFE_TOKEN = "Erro SubMFE. Token Nao Validado.";

	static Logger LOG = Logger.getLogger(RegistroNotaServiceImpl.class.getName());

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object registraNota(String token, RegistraNotaInputDto registraNotaInputDto, Long numeroModeloNota)
			throws Exception {

		String tipoPessoa = null;
		String tipoDocumento = null;
		Long cpfCnpjPnc = Long.parseLong(documentoUtils.formataDocumento(registraNotaInputDto.getCpfCnpj()));
		Long numeroUnidade = Long.parseLong(tokenUtils.getUnidadeFromToken(token));
		Long numeroEquipe = null;
		String cpfCnpj = null;
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll(REGEX_REPLACE_LETRAS, StringUtils.EMPTY);
		boolean statusRetornoSicli = true;
		String numeroProtocolo = registraNotaInputDto.getNumeroProtocolo();
		String numeroContaAtendimento = registraNotaInputDto.getContaAtendimento().replace(".", "").replace("-", "").trim();
		String versaoSistema = registraNotaInputDto.getVersaoSistema();

		String valorMeta = registraNotaInputDto.getValorMeta().replace(".", "").replace("R$", "").replaceAll("\u00A0", "").trim();
		valorMeta = valorMeta.replace(",", ".");

		Long nuUnidade = Long.parseLong(numeroContaAtendimento.substring(0, 4));
		Long nuProduto = Long.parseLong(numeroContaAtendimento.substring(4, 8));
		Long coIdentificacao = Long.parseLong(numeroContaAtendimento.substring(8, numeroContaAtendimento.length()));

		numeroEquipe = equipeAtendimentoRepository.findEquipeByUnidadeSR(numeroUnidade);

		RegistraNotaOutputDto registraNotaOutputDto = new RegistraNotaOutputDto(); 

		if (numeroEquipe == null) {
			registraNotaOutputDto = RegistraNotaOutputDto.builder().statusNotaRegistrada(false).mensagem("A unidade não possui equipe vinculada.").build();
		} else {
			registraNotaOutputDto = vinculaDocumento(registraNotaOutputDto, numeroModeloNota);
			ModeloNotaNegocio modeloNotaNegocio = modeloNotaRepository.prazoValidade(numeroModeloNota);
			Date dataValidade = dataUtils.formataDataValidade(modeloNotaNegocio.getPrazoValidade(), modeloNotaNegocio.getHoraValidade());

			NotaNegociacao notaNegociacao = null;
			RelatorioNotaNegociacao relatorioNotaNegociacao = null;
			NegocioAgenciaVirtual negocioAgenciaVirtual = null;
			AtendimentoNegocio atendimentoNegocio = null; 

			if (registraNotaInputDto.getNumeroNota() == null || StringUtils.isBlank(registraNotaInputDto.getNumeroNota())) {
				
				notaNegociacao = new NotaNegociacao();
				relatorioNotaNegociacao = new RelatorioNotaNegociacao();
				
				negocioAgenciaVirtual = new NegocioAgenciaVirtual();
				negocioAgenciaVirtual.setDataCriacaoNegocio(new Date());
				negocioAgenciaVirtual.setSituacaoNegocio("E".charAt(0));
				negocioAgenciaVirtual = negocioAgenciaVirtualRepository.save(negocioAgenciaVirtual);
				
				atendimentoNegocio = new AtendimentoNegocio();
				atendimentoNegocioRepository.salvaAtendimentoNegocio(Long.parseLong(registraNotaInputDto.getNumeroProtocolo()), negocioAgenciaVirtual.getNumeroNegocio());
				
				notaNegociacao.setNumeroNegocio(negocioAgenciaVirtual.getNumeroNegocio());
								
			} else {
				notaNegociacao = notaNegociacaoRepository.getReferenceById(Long.parseLong(registraNotaInputDto.getNumeroNota()));
				relatorioNotaNegociacao = relatorioNotaNegociacaoRepository.findByNumeroNota(Long.parseLong(registraNotaInputDto.getNumeroNota()));
			}

			AtendimentoCliente atendimentoCliente = atendimentoClienteRepository.getReferenceById(Long.parseLong(registraNotaInputDto.getNumeroProtocolo()));

			if (registraNotaInputDto.getCpfSocio() != null && !registraNotaInputDto.getCpfSocio().isBlank()) {
				Long cpfSocio = Long.parseLong(documentoUtils.formataDocumento(registraNotaInputDto.getCpfSocio()));
				relatorioNotaNegociacao.setCpf(cpfSocio);
				atendimentoCliente.setCpfCliente(cpfSocio);
				atendimentoCliente = atendimentoClienteRepository.save(atendimentoCliente);
			}

			notaNegociacao.setNumeroModeloNota(numeroModeloNota);
			notaNegociacao.setDataCriacaoNota(dataUtils.formataDataBanco());
			notaNegociacao.setDataModificacaoNota(dataUtils.formataDataBanco());
			notaNegociacao.setNumeroMatriculaCriacaoNota(Long.parseLong(matriculaAtendente));
			notaNegociacao.setNumeroMatriculaModificacaoNota(Long.parseLong(matriculaAtendente));
			notaNegociacao.setNumeroSituacaoNota(16L);
			notaNegociacao.setQtdItemNegociacao(1L);
			notaNegociacao.setIcOrigemNota(1L);
			notaNegociacao.setDataPrazoValidade(dataValidade);
			notaNegociacao.setIcOrigemNota(1L);
			notaNegociacao.setNumeroEquipe(numeroEquipe);

			String qtdMetaInput = registraNotaInputDto.getQuantidadeMeta();
			Long qtdMeta = qtdMetaInput == null || StringUtils.isBlank(qtdMetaInput) ? 0 : Long.parseLong(qtdMetaInput.replace(".", "").replace(",", "").trim());
			Optional.ofNullable(qtdMeta).ifPresent(notaNegociacao::setQtdItemNegociacao);

			notaNegociacao.setValorSolicitadoNota(Double.parseDouble(valorMeta));
			notaNegociacao.setNuUnidade(nuUnidade);
			notaNegociacao.setNuProduto(nuProduto);
			notaNegociacao.setCoIdentificacao(coIdentificacao);
			notaNegociacao.setOrigemCadastroNota(ORIGEM_CADASTRO_NOTA_MFE);
			notaNegociacao = notaNegociacaoRepository.save(notaNegociacao);

			AtualizarXML(registraNotaInputDto, numeroModeloNota, notaNegociacao.getNumeroNota());

			AtendimentoNota atendimentoNota = new AtendimentoNota();
			atendimentoNota.setNumeroNota(notaNegociacao.getNumeroNota());
			atendimentoNota.setMatriculaAtendente(Long.parseLong(matriculaAtendente));
			atendimentoNota.setMatriculaAtendenteConclusao(Long.parseLong(matriculaAtendente));
			atendimentoNota.setDtInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());
			atendimentoNota.setDtConclusaoAtendimentoNota(dataUtils.formataDataBanco());
			atendimentoNota.setNumeroEquipe(numeroEquipe);
			atendimentoNota.setUnidadeDesignada('A');
			atendimentoNotaRepository.save(atendimentoNota);
			
			if (documentoUtils.retornaCpf(registraNotaInputDto.getCpfCnpj())) {
				cpfCnpj = documentoUtils.formataDocumento(registraNotaInputDto.getCpfCnpj());
				relatorioNotaNegociacao.setCpf(Long.parseLong(cpfCnpj));
				tipoPessoa = PERSON_TYPE_PF;
				tipoDocumento = DOCUMENT_TYPE_CPF;
				registraNotaInputDto.setNomeSocio(StringUtils.EMPTY);
				registraNotaInputDto.setCpfSocio(StringUtils.EMPTY);
			} else {
				cpfCnpj = documentoUtils.formataDocumento(registraNotaInputDto.getCpfCnpj());
				relatorioNotaNegociacao.setCnpj(Long.parseLong(cpfCnpj));
				tipoPessoa = PERSON_TYPE_PJ;
				tipoDocumento = DOCUMENT_TYPE_CNPJ;
			}

			registraNotaInputDto.setNumeroNota(String.valueOf(notaNegociacao.getNumeroNota()));
			String dsRelatorioNota = mapper.writeValueAsString(registraNotaInputDto);
			Clob relatorioNota = new SerialClob(dsRelatorioNota.toCharArray());

			relatorioNotaNegociacao.setNumeroEquipe(numeroEquipe);
			relatorioNotaNegociacao.setRelatorioNota(relatorioNota);
			relatorioNotaNegociacao.setNumeroNota(notaNegociacao.getNumeroNota());
			relatorioNotaNegociacao.setNumeroProtocolo(Long.parseLong(numeroProtocolo));
			relatorioNotaNegociacao.setNomeCliente(registraNotaInputDto.getNomeCliente());
			relatorioNotaNegociacao.setMatriculaAtendente(Long.parseLong(matriculaAtendente));
			relatorioNotaNegociacao.setMatriculaAlteracao(Long.parseLong(matriculaAtendente));
			relatorioNotaNegociacao.setProduto(registraNotaInputDto.getProduto());
			relatorioNotaNegociacao.setSituacaoNota(16L);
			relatorioNotaNegociacao.setDataCriacaoNota(notaNegociacao.getDataCriacaoNota());
			relatorioNotaNegociacao.setInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());
			relatorioNotaNegociacao.setDataValidade(notaNegociacao.getDataPrazoValidade());
			relatorioNotaNegociacao.setAcaoProduto(registraNotaInputDto.getAcaoProduto());

			relatorioNotaNegociacaoRepository.save(relatorioNotaNegociacao);

			registraNotaOutputDto.setStatusNotaRegistrada(true);
			registraNotaOutputDto.setMensagem("Nota registrada com sucesso!");
			registraNotaOutputDto.setNumeroNota(String.valueOf(notaNegociacao.getNumeroNota()));

			auditoriaRegistraNota(cpfCnpj, matriculaAtendente, statusRetornoSicli, numeroProtocolo,
					numeroContaAtendimento, notaNegociacao, versaoSistema, tipoPessoa, token, registraNotaInputDto,
					tipoDocumento, cpfCnpjPnc, nuUnidade, nuProduto, coIdentificacao, atendimentoCliente);

		}

		return registraNotaOutputDto;

	}

	private void AtualizarXML(RegistraNotaInputDto registraNotaInputDto, Long numeroModeloNota, Long numeroNota) {
		// Começar aqui
		// Mapear o objeto com as informações do banco
		List<CamposNotaOutputDTO> camposNotas = new ArrayList<>();
		JsonNode relatorio = registraNotaInputDto.getRelatorioNota();
		// Busco os modelos dessa nota
		modeloNotaFavoritoRepository.modeloNotaDinamico(numeroModeloNota).stream().forEach(dinamico -> {
			CamposNotaOutputDTO camposNota = null;
			String nomeCampo = String.valueOf(dinamico[3]);
			JsonNode valor = relatorio.get(nomeCampo);
			String texto = "";
			if (valor != null && !valor.isNull()) {
				texto = valor.asText();
			}

			camposNota = CamposNotaOutputDTO.builder().id(String.valueOf(dinamico[0]))
					.idCampo(String.valueOf(dinamico[1])).ordemCampo(String.valueOf(dinamico[2]))
					.nome(String.valueOf(dinamico[3])).predefinido("1".equals(String.valueOf(dinamico[4])))
					.editavel("1".equals(String.valueOf(dinamico[5])))
					.obrigatorio("1".equals(String.valueOf(dinamico[6]))).espacoReservado(String.valueOf(dinamico[7]))
					.tipoCampo(String.valueOf(dinamico[8])).tipoDado(String.valueOf(dinamico[9]))
					.descricao(String.valueOf(dinamico[10])).tamanhoMaximo(String.valueOf(dinamico[11]))
					.valorCampo(texto).mascaraCampo(String.valueOf(dinamico[13])).build();

			camposNotas.add(camposNota);
		});

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
			// Cria o contexto JAXB para a classe NotaNegociacao
			JAXBContext jaxbContext = JAXBContext.newInstance(NegociacaoOutputDTO.class);
			// Cria o Marshaller para a conversão
			Marshaller marshaller = jaxbContext.createMarshaller();
			// Formata o XML de saída
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Converte o objeto em XML e imprime no console
			StringWriter sw = new StringWriter();
			marshaller.marshal(notaNegociacaoXML, sw);
			// Imprime o XML
			String xmlContent = sw.toString();
			String novoXml = xmlContent.replace("<ConteudoCampoMultiploOutPutDTO>", "<conteudoCampoMultiplo>")
					.replace("<CamposNotaOutputDTO>", "<camposNota>")
					.replace("<NegociacaoOutputDTO>", "<notaNegociacao><camposNota>")
					.replace("</NegociacaoOutputDTO>", "</camposNota></notaNegociacao>");
			// add o xml a model
			notaNegociacaoRepository.updateXmlDataById(numeroNota, novoXml);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object enviaCliente(String token, Long numeroNota, EnviaClienteInputDto enviaClienteInputDto) {

		Boolean statusRetornoSicli = true;
		String numeroContaAtendimento = enviaClienteInputDto.getNumeroConta().replace(PONTO, StringUtils.EMPTY).replace(TRACO, StringUtils.EMPTY).trim();
		String numeroProtocolo = enviaClienteInputDto.getNumeroProtocolo();
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll(REGEX_REPLACE_LETRAS, StringUtils.EMPTY);
		String tipoDocumento = documentoUtils.retornaCpf(enviaClienteInputDto.getCpfCnpj()) ? DOCUMENT_TYPE_CPF : DOCUMENT_TYPE_CNPJ;
		Long cpfCnpjPnc = Long.parseLong(documentoUtils.formataDocumento(enviaClienteInputDto.getCpfCnpj()));
		Long nuUnidade = Long.parseLong(numeroContaAtendimento.substring(0, 4));
		Long nuProduto = Long.parseLong(numeroContaAtendimento.substring(4, 8));
		Long coIdentificacao = Long.parseLong(numeroContaAtendimento.substring(8, numeroContaAtendimento.length()));
		AtendimentoCliente atendimentoCliente = atendimentoClienteRepository.getReferenceById(Long.parseLong(enviaClienteInputDto.getNumeroProtocolo()));

		if (Boolean.TRUE.equals(Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getAssinaturaToken()))))) {
			if (Boolean.FALSE.equals(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getTokenValido())))) {
				atendimentoCliente.setValidacaoTokenAtendimento(2L);
				atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
				atendimentoClienteRepository.save(atendimentoCliente);

				auditoriaTokenSms(enviaClienteInputDto, numeroNota, token, tipoDocumento, cpfCnpjPnc, nuUnidade, nuProduto,
						coIdentificacao, matriculaAtendente, statusRetornoSicli, numeroProtocolo,
						numeroContaAtendimento);
				return false;
			}

			notaNegociacaoRepository.assinaNotaCliente(numeroNota);
			relatorioNotaNegociacaoRepository.assinaNotaCliente(numeroNota);
			atendimentoCliente.setValidacaoTokenAtendimento(1L);
			atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);

			auditoriaTokenSms(enviaClienteInputDto, numeroNota, token, tipoDocumento, cpfCnpjPnc, nuUnidade, nuProduto,
					coIdentificacao, matriculaAtendente, statusRetornoSicli, numeroProtocolo, numeroContaAtendimento);
			return true;
		}

		notaNegociacaoRepository.enviaNotaCliente(numeroNota);
		relatorioNotaNegociacaoRepository.enviaNotaCliente(numeroNota);

		AssinaturaNota assinaturaNota = new AssinaturaNota();
		assinaturaNota.setNumeroNota(numeroNota);
		assinaturaNota.setCpfClienteAssinante(atendimentoCliente.getCpfCliente());
		assinaturaNota.setTipoAssinatura((char) '1');
		assinaturaNota.setOrigemAssinatura((char) '1');
		assinaturaNotaRepository.save(assinaturaNota);
		pendenciaAtendimentoNotaRepository.inserePendenciaAtendimento(numeroNota);

		auditoriaApp(enviaClienteInputDto, numeroNota, token, tipoDocumento, cpfCnpjPnc, nuUnidade, nuProduto,
				coIdentificacao, matriculaAtendente, statusRetornoSicli, numeroProtocolo, numeroContaAtendimento);

		return notas(Long.parseLong(numeroProtocolo));
	}
	
	public List<NotasByProtocoloOutputDTO> notas(Long numeroProtocolo) {
		
		return notaNegociacaoRepositoryImpl.notasByProtocolo(numeroProtocolo);
			
	}

	public RegistraNotaOutputDto vinculaDocumento(RegistraNotaOutputDto registraNotaOutputDto, Long numeroModeloNota) {

		Optional<Long> existeModeloNota = modeloNotaRepository.vinculaDocumento(numeroModeloNota);

		if (existeModeloNota.isPresent()) {
			registraNotaOutputDto.setVinculaDocumento(true);
		} else {
			registraNotaOutputDto.setVinculaDocumento(false);
		}

		return registraNotaOutputDto;

	}

	public void auditoriaRegistraNota(String cpfCnpj, String matriculaAtendente, Boolean statusRetornoSicli,
			String numeroProtocolo, String numeroContaAtendimento, NotaNegociacao notaNegociacao, String versaoSistema,
			String tipoPessoa, String token, RegistraNotaInputDto registraNotaInputDto, String tipoDocumento,
			Long cpfCnpjPnc, Long nuUnidade, Long nuProduto, Long coIdentificacao,
			AtendimentoCliente atendimentoCliente) {

		AuditoriaPncRegistraNotaInputDTO auditoriaPncRegistraNotaInputDTO = new AuditoriaPncRegistraNotaInputDTO();
		auditoriaPncRegistraNotaInputDTO = AuditoriaPncRegistraNotaInputDTO.builder()
				.cpfCnpj(cpfCnpj)
				.statusRetornoSicli(String.valueOf(statusRetornoSicli))
				.numeroProtocolo(numeroProtocolo)
				.numeroNota(String.valueOf(notaNegociacao.getNumeroNota()))
				.dataRegistroNota(String.valueOf(dataUtils.formataData(new Date())))
				.versaoSistema(versaoSistema)
				.produto(registraNotaInputDto.getProduto())
				.cpfSocio(registraNotaInputDto.getCpfSocio())				
				.nomeSocio(registraNotaInputDto.getNomeSocio())			
				.build();
		
		String descricaoEnvioTransacao = Base64.getEncoder().encodeToString(metodosUtils.writeValueAsString(auditoriaPncRegistraNotaInputDTO).getBytes());
		String descricaoTransacao = metodosUtils.writeValueAsString(STEP2_REALIZAR_NEGOCIO);
		
		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder()
				.descricaoEnvioTransacao(descricaoEnvioTransacao)
				.descricaoTransacao(descricaoTransacao)
				.ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe(NOME_MFE_AVL_ATENDIMENTOREMOTO)
				.numeroUnidadeLotacaoUsuario(50L)
				.ambienteAplicacao(AMBIENTE_NACIONAL)
				.tipoDocumento(tipoDocumento)
				.numeroIdentificacaoCliente(cpfCnpjPnc)
				.numeroUnidadeContaCliente(nuUnidade)
				.numeroOperacaoProduto(nuProduto)
				.numeroConta(coIdentificacao)
				.build();

		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
		auditoriaRegistraNotaService.auditar(String.valueOf(dataUtils.formataData(new Date())), token, cpfCnpj,
				matriculaAtendente, String.valueOf(statusRetornoSicli), numeroProtocolo, numeroContaAtendimento,
				String.valueOf(notaNegociacao.getNumeroNota()), versaoSistema, registraNotaInputDto.getProduto(),
				String.valueOf(atendimentoCliente.getCpfCliente()));

	}

	public void auditoriaTokenSms(EnviaClienteInputDto enviaClienteInputDto, Long numeroNota, String token,
			String tipoDocumento, Long cpfCnpjPnc, Long nuUnidade, Long nuProduto, Long coIdentificacao,
			String matriculaAtendente, Boolean statusRetornoSicli, String numeroProtocolo,
			String numeroContaAtendimento) {

		String tokenValido = null;
		String situacaoNota = null;

		if (Boolean.TRUE.equals(
				Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getTokenValido()))))) {
			tokenValido = VALIDO;
			situacaoNota = SITUACAO_NOTA_TOKEN_VALIDO;
		}

		if (Boolean.FALSE.equals(
				Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getTokenValido()))))) {
			tokenValido = INVALIDO;
			situacaoNota = SITUACAO_NOTA_TOKEN_INVALIDO;
		}

		AuditoriaPncEnviaNotaTokenInputDTO auditoriaPncEnviaNotaTokenInputDTO = new AuditoriaPncEnviaNotaTokenInputDTO();
		auditoriaPncEnviaNotaTokenInputDTO = AuditoriaPncEnviaNotaTokenInputDTO.builder().situacaoNota(situacaoNota)
				.numeroProtocolo(enviaClienteInputDto.getNumeroProtocolo()).numeroNota(String.valueOf(numeroNota))
				.versaoSistema(enviaClienteInputDto.getVersaoSistema())
				.dataHoraTransacao(dataUtils.formataData(new Date()))
				.assinaturaToken(Boolean.TRUE.equals(Objects.requireNonNull(
						Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getAssinaturaToken())))) ? SIM : NAO)
				.tokenValido(tokenValido).tokenValidoTelefone(enviaClienteInputDto.getTokenValidoTelefone()).build();

		String descricaoEnvioTransacao = Base64.getEncoder()
				.encodeToString(metodosUtils.writeValueAsString(auditoriaPncEnviaNotaTokenInputDTO).getBytes());
		String descricaoTransacao = metodosUtils.writeValueAsString(STEP3_COMPONENTE_TOKEN);

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder()
				.descricaoEnvioTransacao(descricaoEnvioTransacao)
				.descricaoTransacao(descricaoTransacao)
				.ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe(NOME_MFE_AVL_ATENDIMENTOREMOTO)
				.numeroUnidadeLotacaoUsuario(50L)
				.ambienteAplicacao(AMBIENTE_NACIONAL)
				.tipoDocumento(tipoDocumento)
				.numeroIdentificacaoCliente(cpfCnpjPnc)
				.numeroUnidadeContaCliente(nuUnidade)
				.numeroOperacaoProduto(nuProduto)
				.numeroConta(coIdentificacao)
				.build();

		auditoriaEnviaNotaTokenService.auditar(String.valueOf(dataUtils.formataData(new Date())), token,
				enviaClienteInputDto.getCpfCnpj(), matriculaAtendente, String.valueOf(statusRetornoSicli),
				numeroProtocolo, numeroContaAtendimento, String.valueOf(numeroNota), ZERO_CHAR,
				enviaClienteInputDto.getProduto().trim(), String.valueOf(enviaClienteInputDto.getCpfSocio()),
				enviaClienteInputDto.getAssinaturaToken(), enviaClienteInputDto.getTokenValido(),
				enviaClienteInputDto.getTokenValidoTelefone());
		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);

	}

	public void auditoriaApp(EnviaClienteInputDto enviaClienteInputDto, Long numeroNota, String token,
			String tipoDocumento, Long cpfCnpjPnc, Long nuUnidade, Long nuProduto, Long coIdentificacao,
			String matriculaAtendente, Boolean statusRetornoSicli, String numeroProtocolo,
			String numeroContaAtendimento) {

		AuditoriaPncEnviaNotaInputDTO auditoriaPncEnviaNotaInputDTO = new AuditoriaPncEnviaNotaInputDTO();
		auditoriaPncEnviaNotaInputDTO = AuditoriaPncEnviaNotaInputDTO.builder().situacaoNota(SITUACAO_NOTA)
				.numeroProtocolo(enviaClienteInputDto.getNumeroProtocolo()).numeroNota(String.valueOf(numeroNota))
				.versaoSistema(enviaClienteInputDto.getVersaoSistema())
				.dataHoraTransacao(dataUtils.formataData(new Date())).build();

		String descricaoEnvioTransacao = Base64.getEncoder()
				.encodeToString(metodosUtils.writeValueAsString(auditoriaPncEnviaNotaInputDTO).getBytes());
		String descricaoTransacao = metodosUtils.writeValueAsString(STEP4_COMPROVANTE_ASSINAR_PELO_APP);

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoEnvioTransacao(descricaoEnvioTransacao)
				.descricaoTransacao(descricaoTransacao).ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe(NOME_MFE_AVL_ATENDIMENTOREMOTO).numeroUnidadeLotacaoUsuario(50L)
				.ambienteAplicacao(AMBIENTE_NACIONAL).tipoDocumento(tipoDocumento)
				.numeroIdentificacaoCliente(cpfCnpjPnc).numeroUnidadeContaCliente(nuUnidade)
				.numeroOperacaoProduto(nuProduto).numeroConta(coIdentificacao).build();

		auditoriaEnviaNotaService.auditar(String.valueOf(dataUtils.formataData(new Date())), token,
				enviaClienteInputDto.getCpfCnpj(), matriculaAtendente, String.valueOf(statusRetornoSicli),
				numeroProtocolo, numeroContaAtendimento, String.valueOf(numeroNota), ZERO_CHAR,
				enviaClienteInputDto.getProduto().trim(), String.valueOf(enviaClienteInputDto.getCpfSocio()));
		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);

	}

}
