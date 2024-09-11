package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaEnviaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaEnviaNotaTokenService;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.CamposNotaOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ConteudoCampoMultiploOutPutDTO;
import br.gov.caixa.siavl.atendimentoremoto.repository.CampoModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.NegociacaoOutputDTO;
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
import br.gov.caixa.siavl.atendimentoremoto.model.CampoModeloNota;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.NegocioAgenciaVirtual;
import br.gov.caixa.siavl.atendimentoremoto.model.NotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.model.RelatorioNotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.repository.AssinaturaNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNegocioRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NegocioAgenciaVirtualRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.PendenciaAtendimentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RelatorioNotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.RegistroNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaFavoritoRepository;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import org.hibernate.Hibernate;


@Service
@SuppressWarnings("all")
public class RegistroNotaServiceImpl implements RegistroNotaService {

	@Autowired
	AuditoriaPncGateway auditoriaPncGateway;

	@Autowired
	AuditoriaEnviaNotaService auditoriaEnviaNotaService;

	@Autowired
	AuditoriaRegistraNotaService auditoriaRegistraNotaService;

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
	AuditoriaEnviaNotaTokenService auditoriaEnviaNotaTokenService;

	@Autowired
	ModeloNotaFavoritoRepository modeloNotaFavoritoRepository;

	@Autowired
	CampoModeloNotaRepository campoModeloNotaRepository;

	@Autowired
	TokenUtils tokenUtils;

	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	private static final String DOCUMENT_TYPE_CPF = "CPF";
	private static final String DOCUMENT_TYPE_CNPJ = "CNPJ";
	private static final String PATTERN_MATRICULA = "[a-zA-Z]";
	private static final String STEP4_COMPROVANTE_ASSINAR_PELO_APP = "step4_comprovante_assinar_pelo_app";
	private static final String SITUACAO_NOTA = "Aguardando assinatura do cliente";
	private static final String SITUACAO_NOTA_TOKEN = "Pendente de Contratação";
	private static final String STEP3_COMPONENTE_TOKEN = "step3_componente_token";

	static Logger LOG = Logger.getLogger(RegistroNotaServiceImpl.class.getName());

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object registraNota(String token, RegistraNotaInputDto registraNotaInputDto, Long numeroModeloNota)
			throws Exception {

		String tipoPessoa = null;
		String tipoDocumento = null;
		Long cpfCnpjPnc = Long.parseLong(registraNotaInputDto.getCpfCnpj().replace(".", "").replace("-", "").trim());
		Long numeroUnidade = Long.parseLong(tokenUtils.getUnidadeFromToken(token));
		Long numeroEquipe = null;
		String cpfCnpj = null;
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll(PATTERN_MATRICULA, "");
		boolean statusRetornoSicli = true;
		String numeroProtocolo = registraNotaInputDto.getNumeroProtocolo();
		String numeroContaAtendimento = registraNotaInputDto.getContaAtendimento().replace(".", "").replace("-", "").trim();
		String versaoSistema = registraNotaInputDto.getVersaoSistema();

		String valorMeta = registraNotaInputDto.getValorMeta().replace(".", "").replace("R$", "").replaceAll("\u00A0", "").trim();
		valorMeta = valorMeta.replace(",", ".");

		Long nuUnidade = Long.parseLong(numeroContaAtendimento.substring(0, 4));
		Long nuProduto = Long.parseLong(numeroContaAtendimento.substring(4, 8));
		Long coIdentificacao = Long.parseLong(numeroContaAtendimento.substring(8,
				numeroContaAtendimento.length()));

		numeroEquipe = equipeAtendimentoRepository.findEquipeByUnidadeSR(numeroUnidade);

		RegistraNotaOutputDto registraNotaOutputDto = new RegistraNotaOutputDto();

		if (numeroEquipe == null) {

			registraNotaOutputDto = RegistraNotaOutputDto.builder().statusNotaRegistrada(false)
					.mensagem("A unidade não possui equipe vinculada.").build();

		} else {

			registraNotaOutputDto = vinculaDocumento(registraNotaOutputDto, numeroModeloNota);

			ModeloNotaNegocio modeloNotaNegocio = modeloNotaRepository.prazoValidade(numeroModeloNota);
			Date dataValidade = formataDataValidade(modeloNotaNegocio.getPrazoValidade(),
					modeloNotaNegocio.getHoraValidade());

			NegocioAgenciaVirtual negocioAgenciaVirtual = new NegocioAgenciaVirtual();
			negocioAgenciaVirtual.setDataCriacaoNegocio(new Date());
			negocioAgenciaVirtual.setSituacaoNegocio("E".charAt(0));
			negocioAgenciaVirtual = negocioAgenciaVirtualRepository.save(negocioAgenciaVirtual);

			NotaNegociacao notaNegociacao = null;
			RelatorioNotaNegociacao relatorioNotaNegociacao = null;

			if (registraNotaInputDto.getNumeroNota() == null) {

				notaNegociacao = new NotaNegociacao();
				relatorioNotaNegociacao = new RelatorioNotaNegociacao();

			} else {

				notaNegociacao = notaNegociacaoRepository
						.getReferenceById(Long.parseLong(registraNotaInputDto.getNumeroNota()));						
				relatorioNotaNegociacao = relatorioNotaNegociacaoRepository
						.findByNumeroNota(Long.parseLong(registraNotaInputDto.getNumeroNota()));
			}
	

			AtendimentoCliente atendimentoCliente = atendimentoClienteRepository
					.getReferenceById(Long.parseLong(registraNotaInputDto.getNumeroProtocolo()));

			if (registraNotaInputDto.getCpfSocio() != null && !registraNotaInputDto.getCpfSocio().isBlank()) {
				Long cpfSocio = Long.parseLong(
						registraNotaInputDto.getCpfSocio().replace(".", "").replace("-", "").replace("/", "").trim());
				relatorioNotaNegociacao.setCpf(cpfSocio);
				atendimentoCliente.setCpfCliente(cpfSocio);
				atendimentoCliente = atendimentoClienteRepository.save(atendimentoCliente);
			}

			notaNegociacao.setNumeroNegocio(negocioAgenciaVirtual.getNumeroNegocio());
			notaNegociacao.setNumeroModeloNota(numeroModeloNota);
			notaNegociacao.setDataCriacaoNota(formataDataBanco());
			notaNegociacao.setDataModificacaoNota(formataDataBanco());
			notaNegociacao.setNumeroMatriculaCriacaoNota(matriculaCriacaoNota(token));
			notaNegociacao.setNumeroMatriculaModificacaoNota(matriculaCriacaoNota(token));
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
			notaNegociacao = notaNegociacaoRepository.save(notaNegociacao);

			//Todo: Melhorar e a forma com que o springboot trabalha com o xml
			AtualizarXML( registraNotaInputDto,numeroModeloNota, notaNegociacao.getNumeroNota());

			AtendimentoNegocio atendimentoNegocio = new AtendimentoNegocio();
			atendimentoNegocio.setNumeroProtocolo(Long.parseLong(registraNotaInputDto.getNumeroProtocolo()));
			atendimentoNegocio.setNumeroNegocio(negocioAgenciaVirtual.getNumeroNegocio());
			atendimentoNegocio = atendimentoNegocioRepository.save(atendimentoNegocio);

			AtendimentoNota atendimentoNota = new AtendimentoNota();
			atendimentoNota.setNumeroNota(notaNegociacao.getNumeroNota());
			atendimentoNota.setMatriculaAtendente(Long.parseLong(matriculaAtendente));
			atendimentoNota.setMatriculaAtendenteConclusao(Long.parseLong(matriculaAtendente));
			atendimentoNota.setDtInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());
			atendimentoNota.setDtConclusaoAtendimentoNota(formataDataBanco());
			atendimentoNota.setNumeroEquipe(numeroEquipe);
			atendimentoNota.setUnidadeDesignada('A');
			atendimentoNotaRepository.save(atendimentoNota);

			if (registraNotaInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim()
					.length() == 11) {
				cpfCnpj = registraNotaInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim();
				relatorioNotaNegociacao.setCpf(Long.parseLong(cpfCnpj));
				tipoPessoa = PERSON_TYPE_PF;
				tipoDocumento = DOCUMENT_TYPE_CPF;
				registraNotaInputDto.setNomeSocio(StringUtils.EMPTY);
				registraNotaInputDto.setCpfSocio(StringUtils.EMPTY);
			} else {
				cpfCnpj = registraNotaInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim();
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

			AuditoriaPncRegistraNotaInputDTO auditoriaPncRegistraNotaInputDTO = new AuditoriaPncRegistraNotaInputDTO();
			auditoriaPncRegistraNotaInputDTO = AuditoriaPncRegistraNotaInputDTO.builder()

					.cpfCnpj(cpfCnpj).matriculaAtendente(matriculaAtendente)
					.statusRetornoSicli(String.valueOf(statusRetornoSicli)).numeroProtocolo(numeroProtocolo)
					.numeroContaAtendimento(numeroContaAtendimento)
					.numeroNota(String.valueOf(notaNegociacao.getNumeroNota()))
					.dataRegistroNota(String.valueOf(formataData(new Date()))).transacaoSistema("189")
					.versaoSistema(versaoSistema).tipoPessoa(tipoPessoa).ipUsuario(tokenUtils.getIpFromToken(token))
					.produto(registraNotaInputDto.getProduto()).build();

			String descricaoTransacao = null;

			try {
				descricaoTransacao = mapper.writeValueAsString(auditoriaPncRegistraNotaInputDTO);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}

			AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
			auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoTransacao(descricaoTransacao)
					.ipTerminalUsuario(tokenUtils.getIpFromToken(token)).nomeMfe("mfe_avl_atendimentoremoto")
					.numeroUnidadeLotacaoUsuario(50L).ambienteAplicacao("NACIONAL").tipoDocumento(tipoDocumento)
					.numeroIdentificacaoCliente(cpfCnpjPnc).numeroUnidadeContaCliente(nuUnidade)
					.numeroOperacaoProduto(nuProduto).numeroConta(coIdentificacao).build();

			auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
			auditoriaRegistraNotaService.auditar(String.valueOf(formataData(new Date())), token, cpfCnpj,
					matriculaAtendente, String.valueOf(statusRetornoSicli), numeroProtocolo, numeroContaAtendimento,
					String.valueOf(notaNegociacao.getNumeroNota()), versaoSistema, registraNotaInputDto.getProduto(),
					String.valueOf(atendimentoCliente.getCpfCliente()));

		}

		return registraNotaOutputDto;

	}
	private void AtualizarXML(RegistraNotaInputDto registraNotaInputDto, Long numeroModeloNota, Long numeroNota){
		//Começar aqui
    //Mapear o objeto com as informações do banco
		List<CamposNotaOutputDTO> camposNotas = new ArrayList<>();
		JsonNode relatorio = registraNotaInputDto.getRelatorioNota();
		//Busco os modelos dessa nota	
		modeloNotaFavoritoRepository.modeloNotaDinamico(numeroModeloNota).stream().forEach(dinamico -> {
			CamposNotaOutputDTO camposNota = null;
			String nomeCampo = String.valueOf(dinamico[3]);
			JsonNode valor = relatorio.get(nomeCampo);
			String texto = "";
			if(valor!=null && !valor.isNull()){
				texto = valor.asText();
			}
			
			camposNota = CamposNotaOutputDTO.builder()
					.id(String.valueOf(dinamico[0])).idCampo(String.valueOf(dinamico[1]))
					.ordemCampo(String.valueOf(dinamico[2])).nome(String.valueOf(dinamico[3]))
					.predefinido("1".equals(String.valueOf(dinamico[4]))).editavel("1".equals(String.valueOf(dinamico[5])))
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
						conteudoCampoMultiplo = conteudoCampoMultiplo.builder()
								.id(String.valueOf(campo[0]))
								.descricao(String.valueOf(campo[1]))
								.build();

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
			String novoXml = xmlContent.replace("<ConteudoCampoMultiploOutPutDTO>","<conteudoCampoMultiplo>").replace("<CamposNotaOutputDTO>","<camposNota>").replace("<NegociacaoOutputDTO>", "<notaNegociacao><camposNota>").replace("</NegociacaoOutputDTO>", "</camposNota></notaNegociacao>");
			//add o xml a model
			notaNegociacaoRepository.updateXmlDataById(numeroNota,novoXml);
		} catch (JAXBException e) {
				e.printStackTrace();
		}

	}

	@Override
	public Boolean enviaCliente(String token, Long numeroNota, EnviaClienteInputDto enviaClienteInputDto) {

		String numeroContaAtendimento = enviaClienteInputDto.getNumeroConta().replace(".", "").replace("-", "").trim();
		String numeroProtocolo = enviaClienteInputDto.getNumeroProtocolo();
		boolean statusRetornoSicli = true;
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll(PATTERN_MATRICULA, "");
		String tipoDocumento = null;
		Boolean statusContratacao = null;

		Long cpfCnpjPnc = Long.parseLong(enviaClienteInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim());
		Long nuUnidade = Long.parseLong(numeroContaAtendimento.substring(0, 4));
		Long nuProduto = Long.parseLong(numeroContaAtendimento.substring(4, 8));
		Long coIdentificacao = Long.parseLong(numeroContaAtendimento.substring(8, numeroContaAtendimento.length()));
	
		notaNegociacaoRepository.enviaNotaCliente(numeroNota);
		relatorioNotaNegociacaoRepository.enviaNotaCliente(numeroNota);
		statusContratacao = true;

		if (Boolean.TRUE.equals(statusContratacao)) {

			AtendimentoCliente atendimentoCliente = atendimentoClienteRepository.getReferenceById(Long.parseLong(enviaClienteInputDto.getNumeroProtocolo()));
			AssinaturaNota assinaturaNota = new AssinaturaNota();
			assinaturaNota.setNumeroNota(numeroNota);
			assinaturaNota.setCpfClienteAssinante(atendimentoCliente.getCpfCliente());
			assinaturaNota.setTipoAssinatura((char) '1');
			assinaturaNota.setOrigemAssinatura((char) '1');
			assinaturaNotaRepository.save(assinaturaNota);
			pendenciaAtendimentoNotaRepository.inserePendenciaAtendimento(numeroNota);

			if (Boolean.TRUE.equals(Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getAssinaturaToken()))))) { 
				NotaNegociacao notaNegociacao = notaNegociacaoRepository.getReferenceById(numeroNota);
				RelatorioNotaNegociacao relatorioNotaNegociacao = relatorioNotaNegociacaoRepository.findByNumeroNota(numeroNota);

				if (Boolean.TRUE.equals(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getTokenValido())))) {			
					notaNegociacaoRepository.assinaNotaCliente(numeroNota);
					relatorioNotaNegociacaoRepository.assinaNotaCliente(numeroNota);
				}

				AuditoriaPncEnviaNotaTokenInputDTO auditoriaPncEnviaNotaTokenInputDTO = new AuditoriaPncEnviaNotaTokenInputDTO();
				auditoriaPncEnviaNotaTokenInputDTO = AuditoriaPncEnviaNotaTokenInputDTO.builder()
						.situacaoNota(SITUACAO_NOTA_TOKEN)
						.numeroProtocolo(enviaClienteInputDto.getNumeroProtocolo())
						.numeroNota(String.valueOf(numeroNota))
						.versaoSistema(enviaClienteInputDto.getVersaoSistema())
						.dataHoraTransacao(formataData(new Date()))
						.assinaturaToken(Boolean.TRUE.equals(enviaClienteInputDto.getAssinaturaToken()) ? "sim" : "não")
						.tokenValido(enviaClienteInputDto.getTokenValido())
						.tokenValidoTelefone(enviaClienteInputDto.getTokenValidoTelefone())
						.build();

				String descricaoEnvioTransacao = null;
				String descricaoTransacao = null;

				try {
					descricaoTransacao = mapper.writeValueAsString(STEP3_COMPONENTE_TOKEN);
					descricaoEnvioTransacao = Base64.getEncoder()
							.encodeToString(mapper.writeValueAsString(auditoriaPncEnviaNotaTokenInputDTO).getBytes());
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}

				AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
				auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoEnvioTransacao(descricaoEnvioTransacao)
						.descricaoTransacao(descricaoTransacao)
						.ipTerminalUsuario(tokenUtils.getIpFromToken(token))
						.nomeMfe("mfe_avl_atendimentoremoto")
						.numeroUnidadeLotacaoUsuario(50L)
						.ambienteAplicacao("NACIONAL")
						.tipoDocumento(tipoDocumento)
						.numeroIdentificacaoCliente(cpfCnpjPnc)
						.numeroUnidadeContaCliente(nuUnidade)
						.numeroOperacaoProduto(nuProduto)
						.numeroConta(coIdentificacao)
						.build();

				auditoriaEnviaNotaTokenService.auditar(String.valueOf(formataData(new Date())), token,
						enviaClienteInputDto.getCpfCnpj(), matriculaAtendente, String.valueOf(statusRetornoSicli),
						numeroProtocolo, numeroContaAtendimento, String.valueOf(numeroNota), "0",
						enviaClienteInputDto.getProduto().trim(), String.valueOf(enviaClienteInputDto.getCpfSocio()),
						enviaClienteInputDto.getAssinaturaToken(), enviaClienteInputDto.getTokenValido(),
						enviaClienteInputDto.getTokenValidoTelefone());
				auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
			}
		}

		if (enviaClienteInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim()
				.length() == 11) {
			tipoDocumento = DOCUMENT_TYPE_CPF;
		} else {
			tipoDocumento = DOCUMENT_TYPE_CNPJ;
		}

		AuditoriaPncEnviaNotaInputDTO auditoriaPncEnviaNotaInputDTO = new AuditoriaPncEnviaNotaInputDTO();
		auditoriaPncEnviaNotaInputDTO = AuditoriaPncEnviaNotaInputDTO.builder()
				.situacaoNota(SITUACAO_NOTA)
				.numeroProtocolo(enviaClienteInputDto.getNumeroProtocolo())
				.numeroNota(String.valueOf(numeroNota))
				.versaoSistema(enviaClienteInputDto.getVersaoSistema())
				.dataHoraTransacao(formataData(new Date()))
				.build();

		String descricaoEnvioTransacao = null;
		String descricaoTransacao = null;

		try {
			descricaoTransacao = mapper.writeValueAsString(STEP4_COMPROVANTE_ASSINAR_PELO_APP);
			descricaoEnvioTransacao = Base64.getEncoder()
					.encodeToString(mapper.writeValueAsString(auditoriaPncEnviaNotaInputDTO).getBytes());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoEnvioTransacao(descricaoEnvioTransacao)
				.descricaoTransacao(descricaoTransacao).ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe("mfe_avl_atendimentoremoto").numeroUnidadeLotacaoUsuario(50L).ambienteAplicacao("NACIONAL")
				.tipoDocumento(tipoDocumento).numeroIdentificacaoCliente(cpfCnpjPnc)
				.numeroUnidadeContaCliente(nuUnidade).numeroOperacaoProduto(nuProduto).numeroConta(coIdentificacao)
				.build();

		auditoriaEnviaNotaService.auditar(String.valueOf(formataData(new Date())), token,
				enviaClienteInputDto.getCpfCnpj(), matriculaAtendente, String.valueOf(statusRetornoSicli),
				numeroProtocolo, numeroContaAtendimento, String.valueOf(numeroNota), "0",
				enviaClienteInputDto.getProduto().trim(), String.valueOf(enviaClienteInputDto.getCpfSocio()));
		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);

		return statusContratacao;
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

	public RegistraNotaOutputDto vinculaDocumento(RegistraNotaOutputDto registraNotaOutputDto, Long numeroModeloNota) {

		Optional<Long> existeModeloNota = modeloNotaRepository.vinculaDocumento(numeroModeloNota);

		if (existeModeloNota.isPresent()) {
			registraNotaOutputDto.setVinculaDocumento(true);
		} else {
			registraNotaOutputDto.setVinculaDocumento(false);
		}

		return registraNotaOutputDto;

	}

}
