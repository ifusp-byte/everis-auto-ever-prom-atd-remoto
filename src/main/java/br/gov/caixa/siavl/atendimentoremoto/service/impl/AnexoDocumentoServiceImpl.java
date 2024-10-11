package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaDocumentoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.TipoDocumentoClienteOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants.SiecmConstants;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos.ClasseDocumento;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmCamposDinamico;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmDocumentosIncluirDadosRequisicaoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmDocumentosIncluirDestinoDocumentoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmDocumentosIncluirDocumentoAtributosCamposInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmDocumentosIncluirDocumentoAtributosInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmDocumentosIncluirDocumentoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmDocumentosIncluirInputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto.SiecmOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.gateway.SiecmGateway;
import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoNotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.model.TipoDocumentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.DocumentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.DocumentoNotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.TipoDocumentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.AnexoDocumentoService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class AnexoDocumentoServiceImpl implements AnexoDocumentoService {

	private static final Logger LOG = Logger.getLogger(AnexoDocumentoServiceImpl.class.getName());

	private static String DEFAULT_LOCAL_ARMAZENAMENTO = "OS_CAIXA";
	private static String DEFAULT_LOCAL_GRAVACAO = "DOSSIE";
	private static String DEFAULT_DOCUMENTO_TIPO = "pdf";
	private static String DEFAULT_MIME_TYPE = "application/pdf";

	@Autowired
	DataUtils dataUtils;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	SiecmGateway siecmGateway;

	@Autowired
	DocumentoUtils documentoUtils;

	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository;

	@Autowired
	DocumentoClienteRepository documentoClienteRepository;

	@Autowired
	DocumentoNotaNegociacaoRepository documentoNotaNegociacaoRepository;

	private static ObjectMapper mapper = new ObjectMapper();

	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	private static final String PATTERN_MATRICULA = "[a-zA-Z]";
	private static final String INCLUI_DOCUMENTO_OPCIONAL = " Requisicao - Incluir Documento Opcional Step2: ";
	private static final String INCLUI_DOCUMENTO_OBRIGATORIO = " Requisicao - Incluir Documento Obrigatorio Step3: ";

	@Override
	public SiecmOutputDto enviaDocumento(String token, String cpfCnpj, EnviaDocumentoInputDto enviaDocumentoInputDto)
			throws Exception {

		SiecmOutputDto siecmOutputDto = null;

		String cpfCnpjSiecm = documentoUtils.formataDocumento(cpfCnpj);
		String tipoPessoa = documentoUtils.tipoPessoa(cpfCnpj);

		siecmGateway.dossieCriar(token, cpfCnpjSiecm);

		SiecmDocumentosIncluirDadosRequisicaoInputDto siecmDocumentosIncluirDadosRequisicao = new SiecmDocumentosIncluirDadosRequisicaoInputDto();
		siecmDocumentosIncluirDadosRequisicao.setIpUsuarioFinal(tokenUtils.getIpFromToken(token));
		siecmDocumentosIncluirDadosRequisicao.setLocalArmazenamento(DEFAULT_LOCAL_ARMAZENAMENTO);

		SiecmDocumentosIncluirDestinoDocumentoInputDto siecmDocumentosIncluirDestinoDocumento = new SiecmDocumentosIncluirDestinoDocumentoInputDto();
		siecmDocumentosIncluirDestinoDocumento.setIdDestino(cpfCnpjSiecm);
		siecmDocumentosIncluirDestinoDocumento.setLocalGravacao(DEFAULT_LOCAL_GRAVACAO);
		siecmDocumentosIncluirDestinoDocumento.setSubPasta(StringUtils.EMPTY);

		SiecmDocumentosIncluirDocumentoAtributosCamposInputDto siecmDocumentosIncluirDocumentoAtributosCampos = new SiecmDocumentosIncluirDocumentoAtributosCamposInputDto();
		siecmDocumentosIncluirDocumentoAtributosCampos.setClasse(enviaDocumentoInputDto.getCodGED().trim());

		List<Object> siecmCamposDinamicoObrigatorios = new ArrayList<>();
		siecmCamposDinamicoObrigatorios
				.add(new SiecmCamposDinamico(SiecmConstants.EMISSOR, SiecmConstants.SIAVL, SiecmConstants.STRING));
		siecmCamposDinamicoObrigatorios.add(new SiecmCamposDinamico(SiecmConstants.DATA_EMISSAO,
				dataUtils.formataDataSiecm(new Date()), SiecmConstants.DATE));
		siecmCamposDinamicoObrigatorios.add(new SiecmCamposDinamico(SiecmConstants.CLASSIFICACAO_SIGILO,
				SiecmConstants.INTERNO_TODOS, SiecmConstants.STRING));
		siecmCamposDinamicoObrigatorios.add(new SiecmCamposDinamico(SiecmConstants.RESPONSAVEL_CAPTURA,
				tokenUtils.getMatriculaFromToken(token), SiecmConstants.STRING));
		siecmCamposDinamicoObrigatorios.add(new SiecmCamposDinamico(SiecmConstants.STATUS, "0", SiecmConstants.STRING));
		siecmCamposDinamicoObrigatorios.addAll(enviaDocumentoInputDto.getListaCamposDinamico());

		siecmDocumentosIncluirDocumentoAtributosCampos.setCampo(siecmCamposDinamicoObrigatorios);
		siecmDocumentosIncluirDocumentoAtributosCampos.setTipo(DEFAULT_DOCUMENTO_TIPO);
		siecmDocumentosIncluirDocumentoAtributosCampos.setMimeType(DEFAULT_MIME_TYPE);
		siecmDocumentosIncluirDocumentoAtributosCampos.setGerarThumbnail(true);
		siecmDocumentosIncluirDocumentoAtributosCampos.setNome(dataUtils.formataData(new Date()) + "_"
				+ enviaDocumentoInputDto.getCodGED().trim() + "." + DEFAULT_DOCUMENTO_TIPO);

		SiecmDocumentosIncluirDocumentoAtributosInputDto siecmDocumentosIncluirDocumentoAtributos = new SiecmDocumentosIncluirDocumentoAtributosInputDto();
		siecmDocumentosIncluirDocumentoAtributos.setBinario(enviaDocumentoInputDto.getArquivoContrato());
		siecmDocumentosIncluirDocumentoAtributos.setAtributos(siecmDocumentosIncluirDocumentoAtributosCampos);

		SiecmDocumentosIncluirDocumentoInputDto siecmDocumentosIncluirDocumento = new SiecmDocumentosIncluirDocumentoInputDto();
		siecmDocumentosIncluirDocumento.setAtributos(siecmDocumentosIncluirDocumentoAtributos);
		siecmDocumentosIncluirDocumento.setBinario(enviaDocumentoInputDto.getArquivoContrato());

		SiecmDocumentosIncluirInputDto siecmDocumentosIncluir = new SiecmDocumentosIncluirInputDto();
		siecmDocumentosIncluir.setDadosRequisicao(siecmDocumentosIncluirDadosRequisicao);
		siecmDocumentosIncluir.setDestinoDocumento(siecmDocumentosIncluirDestinoDocumento);
		siecmDocumentosIncluir.setDocumento(siecmDocumentosIncluirDocumentoAtributos);

		String requestAnexarDocumento = null;
		requestAnexarDocumento = metodosUtils.writeValueAsString(siecmDocumentosIncluir);

		siecmOutputDto = siecmGateway.documentoIncluir(token, cpfCnpj, requestAnexarDocumento);

		Long numeroTipoDoc = tipoDocumentoRepository
				.numeroTipoDocumentoCliente(enviaDocumentoInputDto.getCodGED().trim());

		Date dtInclusaoDocumento = dataUtils.formataDataBanco();

		DocumentoCliente documentoCliente = new DocumentoCliente();
		documentoCliente.setTipoDocumentoCliente(numeroTipoDoc);
		documentoCliente.setCpfCnpjCliente(Long.parseLong(cpfCnpjSiecm));
		documentoCliente.setTipoPessoa(tipoPessoa);
		documentoCliente.setMatriculaAtendente(Long
				.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll(PATTERN_MATRICULA, StringUtils.EMPTY)));
		documentoCliente.setMimetypeAnexo(DEFAULT_MIME_TYPE);
		documentoCliente.setExtensaoAnexo(DEFAULT_DOCUMENTO_TIPO);
		documentoCliente.setNomeAnexo(siecmDocumentosIncluirDocumentoAtributosCampos.getNome());
		documentoCliente.setCodGED(siecmOutputDto.getId());
		documentoCliente.setStcoDocumentoCliente(1L);
		documentoCliente.setSituacaoDocumentoCliente(1L);
		documentoCliente.setInclusaoDocumento(dtInclusaoDocumento);
		documentoClienteRepository.save(documentoCliente);

		String mensagemDocumento = INCLUI_DOCUMENTO_OPCIONAL;
		String numeroNota = enviaDocumentoInputDto.getNumeroNota();

		if (!StringUtils.isEmpty(enviaDocumentoInputDto.getNumeroNota())) {

			DocumentoNotaNegociacao documentoNotaNegociacao = new DocumentoNotaNegociacao();
			documentoNotaNegociacao.setTipoPessoa(tipoPessoa);
			documentoNotaNegociacao.setNumeroNota(Long.parseLong(enviaDocumentoInputDto.getNumeroNota()));
			documentoNotaNegociacao.setCpfCnpjCliente(Long.parseLong(cpfCnpjSiecm));
			documentoNotaNegociacao.setTipoDocumentoCliente(numeroTipoDoc);
			documentoNotaNegociacao.setInclusaoDocumento(dtInclusaoDocumento);
			documentoNotaNegociacaoRepository.save(documentoNotaNegociacao);
			mensagemDocumento = INCLUI_DOCUMENTO_OBRIGATORIO;

		}

		LOG.log(Level.INFO, "Nota: " + numeroNota + mensagemDocumento + requestAnexarDocumento);

		return siecmOutputDto;

	}

	@Override
	public Object tipoDocumento(String cpfCnpj) throws Exception {

		TipoDocumentoClienteOutputDTO tipoDocumentoClienteOutputDTO = new TipoDocumentoClienteOutputDTO();

		List<TipoDocumentoCliente> documentosOpcionais = new ArrayList<>();
		List<TipoDocumentoCliente> documentosObrigatorios = new ArrayList<>();

		if (documentoUtils.retornaCpf(cpfCnpj)) {
			
			documentosOpcionais.addAll(tipoDocumentoRepository.opcionalTipoDocumentoPF());
			documentosOpcionais.addAll(tipoDocumentoRepository.opcionalTipoDocumentoAmbosPFPJ());
			tipoDocumentoClienteOutputDTO.setDocumentosOpcionais(documentosOpcionais);
			
			documentosObrigatorios.addAll(tipoDocumentoRepository.obrigatorioTipoDocumentoPF());
			tipoDocumentoClienteOutputDTO.setDocumentosObrigatorios(documentosObrigatorios);

		} else {
			
			documentosOpcionais.addAll(tipoDocumentoRepository.opcionalTipoDocumentoPJ());
			documentosOpcionais.addAll(tipoDocumentoRepository.opcionalTipoDocumentoAmbosPFPJ());
			tipoDocumentoClienteOutputDTO.setDocumentosOpcionais(documentosOpcionais);
			
			documentosObrigatorios.addAll(tipoDocumentoRepository.obrigatorioTipoDocumentoPJ());
			tipoDocumentoClienteOutputDTO.setDocumentosObrigatorios(documentosObrigatorios);

		}
		return tipoDocumentoClienteOutputDTO;
	}

	@Override
	public Object tipoDocumentoCampos(String codGED) throws Exception {
		return ClasseDocumento.valueOf(codGED);
	}

}
