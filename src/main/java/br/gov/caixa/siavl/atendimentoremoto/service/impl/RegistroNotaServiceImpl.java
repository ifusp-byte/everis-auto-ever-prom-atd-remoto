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

import java.sql.Clob;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaEnviaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaEnviaNotaTokenService;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
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
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NegocioAgenciaVirtualRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.PendenciaAtendimentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RelatorioNotaNegociacaoRepository;
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
	AuditoriaRegistraNotaService auditoriaRegistraNotaService;

	@Autowired
	AuditoriaEnviaNotaTokenService auditoriaEnviaNotaTokenService;

	@Autowired
	NegocioAgenciaVirtualRepository negocioAgenciaVirtualRepository;

	@Autowired
	RelatorioNotaNegociacaoRepository relatorioNotaNegociacaoRepository;

	@Autowired
	PendenciaAtendimentoNotaRepository pendenciaAtendimentoNotaRepository;

	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	private static final String DOCUMENT_TYPE_CPF = "CPF";
	private static final String DOCUMENT_TYPE_CNPJ = "CNPJ";
	private static final String ORIGEM_CADASTRO_NOTA_MFE = "P";
	private static final String SITUACAO_NOTA_TOKEN = "Pendente de Contratacao";
	private static final String STEP3_COMPONENTE_TOKEN = "step3_componente_token";
	private static final String SITUACAO_NOTA = "Aguardando assinatura do cliente";
	private static final String STEP4_COMPROVANTE_ASSINAR_PELO_APP = "step4_comprovante_assinar_pelo_app";
	private static final String STEP3_COMPONENTE_TOKEN_ERRO_SUBMFE_TOKEN = "Erro SubMFE. Token Nao Validado.";

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
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll(REGEX_REPLACE_LETRAS,
				StringUtils.EMPTY);
		boolean statusRetornoSicli = true;
		String numeroProtocolo = registraNotaInputDto.getNumeroProtocolo();
		String numeroContaAtendimento = registraNotaInputDto.getContaAtendimento().replace(".", "").replace("-", "")
				.trim();
		String versaoSistema = registraNotaInputDto.getVersaoSistema();

		String valorMeta = registraNotaInputDto.getValorMeta().replace(".", "").replace("R$", "")
				.replaceAll("\u00A0", "").trim();
		valorMeta = valorMeta.replace(",", ".");

		Long nuUnidade = Long.parseLong(numeroContaAtendimento.substring(0, 4));
		Long nuProduto = Long.parseLong(numeroContaAtendimento.substring(4, 8));
		Long coIdentificacao = Long.parseLong(numeroContaAtendimento.substring(8, numeroContaAtendimento.length()));

		numeroEquipe = equipeAtendimentoRepository.findEquipeByUnidadeSR(numeroUnidade);

		RegistraNotaOutputDto registraNotaOutputDto = new RegistraNotaOutputDto();

		if (numeroEquipe == null) {

			registraNotaOutputDto = RegistraNotaOutputDto.builder().statusNotaRegistrada(false)
					.mensagem("A unidade não possui equipe vinculada.").build();

		} else {

			registraNotaOutputDto = vinculaDocumento(registraNotaOutputDto, numeroModeloNota);

			ModeloNotaNegocio modeloNotaNegocio = modeloNotaRepository.prazoValidade(numeroModeloNota);
			Date dataValidade = dataUtils.formataDataValidade(modeloNotaNegocio.getPrazoValidade(),
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
			Long qtdMeta = qtdMetaInput == null || StringUtils.isBlank(qtdMetaInput) ? 0
					: Long.parseLong(qtdMetaInput.replace(".", "").replace(",", "").trim());
			Optional.ofNullable(qtdMeta).ifPresent(notaNegociacao::setQtdItemNegociacao);

			notaNegociacao.setValorSolicitadoNota(Double.parseDouble(valorMeta));
			notaNegociacao.setNuUnidade(nuUnidade);
			notaNegociacao.setNuProduto(nuProduto);
			notaNegociacao.setCoIdentificacao(coIdentificacao);
			notaNegociacao.setOrigemCadastroNota(ORIGEM_CADASTRO_NOTA_MFE);
			notaNegociacao = notaNegociacaoRepository.save(notaNegociacao);

			AtendimentoNegocio atendimentoNegocio = new AtendimentoNegocio();
			atendimentoNegocio.setNumeroProtocolo(Long.parseLong(registraNotaInputDto.getNumeroProtocolo()));
			atendimentoNegocio.setNumeroNegocio(negocioAgenciaVirtual.getNumeroNegocio());
			atendimentoNegocio = atendimentoNegocioRepository.save(atendimentoNegocio);

			AtendimentoNota atendimentoNota = new AtendimentoNota();
			atendimentoNota.setNumeroNota(notaNegociacao.getNumeroNota());
			atendimentoNota.setMatriculaAtendente(Long.parseLong(matriculaAtendente));
			atendimentoNota.setMatriculaAtendenteConclusao(Long.parseLong(matriculaAtendente));
			atendimentoNota.setDtInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());
			atendimentoNota.setDtConclusaoAtendimentoNota(dataUtils.formataDataBanco());
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
					.dataRegistroNota(String.valueOf(dataUtils.formataData(new Date()))).transacaoSistema("189")
					.versaoSistema(versaoSistema).tipoPessoa(tipoPessoa).ipUsuario(tokenUtils.getIpFromToken(token))
					.produto(registraNotaInputDto.getProduto()).build();

			String descricaoTransacao = metodosUtils.writeValueAsString(auditoriaPncRegistraNotaInputDTO);

			AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
			auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoTransacao(descricaoTransacao)
					.ipTerminalUsuario(tokenUtils.getIpFromToken(token)).nomeMfe("mfe_avl_atendimentoremoto")
					.numeroUnidadeLotacaoUsuario(50L).ambienteAplicacao("NACIONAL").tipoDocumento(tipoDocumento)
					.numeroIdentificacaoCliente(cpfCnpjPnc).numeroUnidadeContaCliente(nuUnidade)
					.numeroOperacaoProduto(nuProduto).numeroConta(coIdentificacao).build();

			auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
			auditoriaRegistraNotaService.auditar(String.valueOf(dataUtils.formataData(new Date())), token, cpfCnpj,
					matriculaAtendente, String.valueOf(statusRetornoSicli), numeroProtocolo, numeroContaAtendimento,
					String.valueOf(notaNegociacao.getNumeroNota()), versaoSistema, registraNotaInputDto.getProduto(),
					String.valueOf(atendimentoCliente.getCpfCliente()));

		}

		return registraNotaOutputDto;

	}

	@Override
	public Boolean enviaCliente(String token, Long numeroNota, EnviaClienteInputDto enviaClienteInputDto) {

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

				auditoriaToken(enviaClienteInputDto, numeroNota, token, tipoDocumento, cpfCnpjPnc, nuUnidade, nuProduto,
						coIdentificacao, matriculaAtendente, statusRetornoSicli, numeroProtocolo,
						numeroContaAtendimento);
				return false;
			}

			notaNegociacaoRepository.assinaNotaCliente(numeroNota);
			relatorioNotaNegociacaoRepository.assinaNotaCliente(numeroNota);
			atendimentoCliente.setValidacaoTokenAtendimento(1L);
			atendimentoCliente.setDataEnvioToken(dataUtils.formataDataBanco());
			atendimentoClienteRepository.save(atendimentoCliente);

			auditoriaToken(enviaClienteInputDto, numeroNota, token, tipoDocumento, cpfCnpjPnc, nuUnidade, nuProduto,
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

		return true;
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
	
	public void auditoriaToken(EnviaClienteInputDto enviaClienteInputDto, Long numeroNota, String token,
			String tipoDocumento, Long cpfCnpjPnc, Long nuUnidade, Long nuProduto, Long coIdentificacao,
			String matriculaAtendente, Boolean statusRetornoSicli, String numeroProtocolo,
			String numeroContaAtendimento) {

		String tokenValido = null;

		if (Boolean.TRUE.equals(Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getTokenValido()))))) {
			tokenValido = VALIDO;
		}

		if (Boolean.FALSE.equals(Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getTokenValido()))))) {
			tokenValido = INVALIDO;
		}
		
		
		/*
		if (Boolean.FALSE.equals(Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getTokenValido()))))
				&& StringUtils.isNotBlank(enviaClienteInputDto.getTokenValidoTelefone())) {
			tokenValido = INVALIDO;
		}
		
		if (Boolean.FALSE.equals(Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getTokenValido()))))
				&& StringUtils.isBlank(enviaClienteInputDto.getTokenValidoTelefone())) {
			tokenValido = STEP3_COMPONENTE_TOKEN_ERRO_SUBMFE_TOKEN;
		}
		*/

		AuditoriaPncEnviaNotaTokenInputDTO auditoriaPncEnviaNotaTokenInputDTO = new AuditoriaPncEnviaNotaTokenInputDTO();
		auditoriaPncEnviaNotaTokenInputDTO = AuditoriaPncEnviaNotaTokenInputDTO.builder()
				.situacaoNota(SITUACAO_NOTA_TOKEN).numeroProtocolo(enviaClienteInputDto.getNumeroProtocolo())
				.numeroNota(String.valueOf(numeroNota)).versaoSistema(enviaClienteInputDto.getVersaoSistema())
				.dataHoraTransacao(dataUtils.formataData(new Date()))
				.assinaturaToken(Boolean.TRUE.equals(Objects.requireNonNull(Boolean.parseBoolean(String.valueOf(enviaClienteInputDto.getAssinaturaToken())))) ? SIM : NAO)
				.tokenValido(tokenValido).tokenValidoTelefone(enviaClienteInputDto.getTokenValidoTelefone()).build();

		String descricaoEnvioTransacao = Base64.getEncoder()
				.encodeToString(metodosUtils.writeValueAsString(auditoriaPncEnviaNotaTokenInputDTO).getBytes());
		String descricaoTransacao = metodosUtils.writeValueAsString(STEP3_COMPONENTE_TOKEN);

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoEnvioTransacao(descricaoEnvioTransacao)
				.descricaoTransacao(descricaoTransacao).ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe(NOME_MFE_AVL_ATENDIMENTOREMOTO).numeroUnidadeLotacaoUsuario(50L)
				.ambienteAplicacao(AMBIENTE_NACIONAL).tipoDocumento(tipoDocumento)
				.numeroIdentificacaoCliente(cpfCnpjPnc).numeroUnidadeContaCliente(nuUnidade)
				.numeroOperacaoProduto(nuProduto).numeroConta(coIdentificacao).build();

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
