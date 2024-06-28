package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaDocumentoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.TipoDocumentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.TipoDocumentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.AnexoDocumentoService;
import br.gov.caixa.siavl.atendimentoremoto.siecm.constants.SiecmConstants;
import br.gov.caixa.siavl.atendimentoremoto.siecm.documentos.ClasseDocumento;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmCamposDinamico;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmDocumentosIncluirDadosRequisicaoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmDocumentosIncluirDestinoDocumentoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmDocumentosIncluirDocumentoAtributosCamposInputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmDocumentosIncluirDocumentoAtributosInputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmDocumentosIncluirDocumentoInputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmDocumentosIncluirInputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.dto.SiecmOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.siecm.gateway.SiecmGateway;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
public class AnexoDocumentoServiceImpl implements AnexoDocumentoService {

	private final static Logger LOG = Logger.getLogger(AnexoDocumentoServiceImpl.class.getName());

	private static String DEFAULT_IP = "127.0.0.1";
	private static String DEFAULT_LOCAL_ARMAZENAMENTO = "OS_CAIXA";
	private static String DEFAULT_LOCAL_GRAVACAO = "DOSSIE";
	private static String DEFAULT_DOCUMENTO_TIPO = "pdf";
	private static String DEFAULT_MIME_TYPE = "application/pdf";

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	SiecmGateway siecmGateway;

	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository;

	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public SiecmOutputDto enviaDocumento(String token, String cpfCnpj, EnviaDocumentoInputDto enviaDocumentoInputDto)
			throws Exception {
		
		SiecmOutputDto siecmOutputDto = null;
			
		String cpfCnpjSiecm = cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim();

		siecmGateway.dossieCriar(token, cpfCnpjSiecm);

		SiecmDocumentosIncluirDadosRequisicaoInputDto siecmDocumentosIncluirDadosRequisicao = new SiecmDocumentosIncluirDadosRequisicaoInputDto();
		siecmDocumentosIncluirDadosRequisicao.setIpUsuarioFinal(DEFAULT_IP);
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
				formataDataSiecm(new Date()), SiecmConstants.DATE));
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
		siecmDocumentosIncluirDocumentoAtributosCampos
				.setNome(formataData(new Date()) + "_" + enviaDocumentoInputDto.getCodGED().trim());

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

		try {
			requestAnexarDocumento = mapper.writeValueAsString(siecmDocumentosIncluir).replaceAll("\\u005C", "")
					.replaceAll("\\n", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		LOG.log(Level.INFO, "Requisicao - Incluir Documento: " + requestAnexarDocumento);

		siecmOutputDto = siecmGateway.documentoIncluir(token, cpfCnpj, requestAnexarDocumento);

		return siecmOutputDto; 
		
	}

	@Override
	public Object tipoDocumento(String cpfCnpj) throws Exception {
		List<TipoDocumentoCliente> tpoDocumentoClienteLista = new ArrayList<>();
		if (cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim().length() == 11) {
			tpoDocumentoClienteLista = tipoDocumentoRepository.tipoDocumentoPF();
		} else {
			tpoDocumentoClienteLista = tipoDocumentoRepository.tipoDocumentoPJ();
		}
		return tpoDocumentoClienteLista;
	}

	@Override
	public Object tipoDocumentoCampos(String codGED) throws Exception {
		return ClasseDocumento.valueOf(codGED);
	}

	private String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

	private String formataDataSiecm(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}
}
