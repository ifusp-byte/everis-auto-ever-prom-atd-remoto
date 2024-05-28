package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialClob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncEnviaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncRegistraNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.gateway.AuditoriaPncGateway;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.EnviaClienteInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.AssinaturaNota;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoNota;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.NegocioAgenciaVirtual;
import br.gov.caixa.siavl.atendimentoremoto.model.NotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.model.PendenciaAtendimentoNota;
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

@Service
public class RegistroNotaServiceImpl implements RegistroNotaService {

	@Autowired
	AuditoriaPncGateway auditoriaPncGateway;

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
	TokenUtils tokenUtils;
	
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	private static final String DOCUMENT_TYPE_CPF = "CPF";
	private static final String DOCUMENT_TYPE_CNPJ = "CNPJ";
	private static final String PATTERN_MATRICULA = "[a-zA-Z]";

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
		String numeroContaAtendimento = registraNotaInputDto.getContaAtendimento();
		String versaoSistema = registraNotaInputDto.getVersaoSistema();
		String valorMeta = registraNotaInputDto.getValorMeta().replace(".", "").replace("R$", "").replaceAll("\u00A0", "").trim();
		valorMeta = valorMeta.replace(",", ".");
		
		Long nuUnidade = Long.parseLong(registraNotaInputDto.getContaAtendimento().substring(0, 4)); 
		Long nuProduto = Long.parseLong(registraNotaInputDto.getContaAtendimento().substring(4, 8)); 
		Long coIdentificacao = Long.parseLong(registraNotaInputDto.getContaAtendimento().substring(8, registraNotaInputDto.getContaAtendimento().length()));
		
		numeroEquipe = equipeAtendimentoRepository.findEquipeByUnidade(numeroUnidade);

		RegistraNotaOutputDto registraNotaOutputDto = new RegistraNotaOutputDto();

		if (numeroEquipe == null) {

			registraNotaOutputDto = RegistraNotaOutputDto.builder().statusNotaRegistrada(false)
					.mensagem("A unidade não possui equipe vinculada.").build();

		} else {
			
			String dsRelatorioNota = mapper.writeValueAsString(registraNotaInputDto);
			Clob relatorioNota = new SerialClob(dsRelatorioNota.toCharArray());
			


			ModeloNotaNegocio modeloNotaNegocio = modeloNotaRepository.prazoValidade(numeroModeloNota);	
			Date dataValidade = formataDataValidade(modeloNotaNegocio.getPrazoValidade(), modeloNotaNegocio.getHoraValidade()); 
				
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
				
				notaNegociacao = notaNegociacaoRepository.getReferenceById(Long.parseLong(registraNotaInputDto.getNumeroNota()));	
				relatorioNotaNegociacao = relatorioNotaNegociacaoRepository.findByNumeroNota(Long.parseLong(registraNotaInputDto.getNumeroNota()));
			}
			
			AtendimentoCliente atendimentoCliente = atendimentoClienteRepository.getReferenceById(Long.parseLong(registraNotaInputDto.getNumeroProtocolo()));
			
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
			notaNegociacao.setIcOrigemNota(1L);	
			notaNegociacao.setNumeroEquipe(numeroEquipe);		
			notaNegociacao.setQtdItemNegociacao(Long.parseLong(registraNotaInputDto.getQuantidadeMeta().replace(".", "").replace(",", "").trim()));
			notaNegociacao.setValorSolicitadoNota(Double.parseDouble(valorMeta));
			notaNegociacao.setNuUnidade(nuUnidade);
			notaNegociacao.setNuProduto(nuProduto);		
			notaNegociacao.setCoIdentificacao(coIdentificacao);			
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
		    atendimentoNota.setDtConclusaoAtendimentoNota(formataDataBanco());
		    atendimentoNota.setNumeroEquipe(numeroEquipe);
		    atendimentoNota.setUnidadeDesignada('A');
		    atendimentoNotaRepository.save(atendimentoNota);
		    
			
			if (registraNotaInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim().length() == 11) {
				cpfCnpj = registraNotaInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim();
				relatorioNotaNegociacao.setCpf(Long.parseLong(cpfCnpj));
				tipoPessoa = PERSON_TYPE_PF;
				tipoDocumento = DOCUMENT_TYPE_CPF;
			} else {
				cpfCnpj = registraNotaInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim();
				relatorioNotaNegociacao.setCnpj(Long.parseLong(cpfCnpj));	
				tipoPessoa = PERSON_TYPE_PJ;	
				tipoDocumento = DOCUMENT_TYPE_CNPJ;
			}

			relatorioNotaNegociacao.setNumeroEquipe(numeroEquipe);
			relatorioNotaNegociacao.setRelatorioNota(relatorioNota);
			relatorioNotaNegociacao.setNumeroNota(notaNegociacao.getNumeroNota());
			relatorioNotaNegociacao.setNumeroProtocolo(Long.parseLong(numeroProtocolo));
			relatorioNotaNegociacao.setNomeCliente(registraNotaInputDto.getNomeCliente());
			relatorioNotaNegociacao.setMatriculaAtendente(Long.parseLong(matriculaAtendente));
			relatorioNotaNegociacao.setMatriculaAlteracao(Long.parseLong(matriculaAtendente));			
			relatorioNotaNegociacao.setProduto(registraNotaInputDto.getProduto());
			relatorioNotaNegociacao.setSituacaoNota(22L);
			relatorioNotaNegociacao.setDataCriacaoNota(notaNegociacao.getDataCriacaoNota());
			relatorioNotaNegociacao.setInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());	
			relatorioNotaNegociacao.setDataValidade(notaNegociacao.getDataPrazoValidade());	
			relatorioNotaNegociacao.setAcaoProduto(registraNotaInputDto.getAcaoProduto());
								
			relatorioNotaNegociacaoRepository.save(relatorioNotaNegociacao);
			registraNotaOutputDto = RegistraNotaOutputDto.builder()
					.statusNotaRegistrada(true)
					.mensagem("Nota registrada com sucesso!")
					.numeroNota(String.valueOf(notaNegociacao.getNumeroNota()))				
					.build();
					
			AuditoriaPncRegistraNotaInputDTO auditoriaPncRegistraNotaInputDTO = new AuditoriaPncRegistraNotaInputDTO();
			auditoriaPncRegistraNotaInputDTO = AuditoriaPncRegistraNotaInputDTO.builder()

					.cpfCnpj(cpfCnpj).matriculaAtendente(matriculaAtendente)
					.statusRetornoSicli(String.valueOf(statusRetornoSicli))
					.numeroProtocolo(numeroProtocolo)
					.numeroContaAtendimento(numeroContaAtendimento)
					.numeroNota(String.valueOf(notaNegociacao.getNumeroNota()))
					.dataRegistroNota(String.valueOf(formataData(new Date())))
					.transacaoSistema("189")
					.versaoSistema(versaoSistema)
					.tipoPessoa(tipoPessoa)
					.ipUsuario(tokenUtils.getIpFromToken(token))
				    .produto(registraNotaInputDto.getProduto())				
				    .build();

			String descricaoTransacao = null;

			try {
				descricaoTransacao = mapper.writeValueAsString(auditoriaPncRegistraNotaInputDTO);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}

			AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
			auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoTransacao(descricaoTransacao)
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

			auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
			auditoriaRegistraNotaService.auditar(String.valueOf(formataData(new Date())), token, cpfCnpj, matriculaAtendente,
					String.valueOf(statusRetornoSicli), numeroProtocolo, numeroContaAtendimento, String.valueOf(notaNegociacao.getNumeroNota()),
					versaoSistema, registraNotaInputDto.getProduto(), String.valueOf(atendimentoCliente.getCpfCliente()));
		
		}

		return registraNotaOutputDto;

	}

	@Override
	public Boolean enviaCliente(String token, Long numeroNota, EnviaClienteInputDto enviaClienteInputDto) {
		
		String tipoPessoa = null; 
		String tipoDocumento = null; 
		Boolean statusContratacao = null;
		Long cpfCnpjPnc = Long.parseLong(enviaClienteInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim());
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll(PATTERN_MATRICULA, "");
		
		Long nuUnidade = Long.parseLong(enviaClienteInputDto.getNumeroConta().substring(0, 4)); 
		Long nuProduto = Long.parseLong(enviaClienteInputDto.getNumeroConta().substring(4, 8)); 
		Long coIdentificacao = Long.parseLong(enviaClienteInputDto.getNumeroConta().substring(8, enviaClienteInputDto.getNumeroConta().length()));

		notaNegociacaoRepository.enviaNotaCliente(numeroNota);
		statusContratacao = true;
			

		if (Boolean.TRUE.equals(statusContratacao)) {	
		
			AssinaturaNota assinaturaNota = new AssinaturaNota();
			assinaturaNota.setNumeroNota(numeroNota);
			assinaturaNota.setCpfClienteAssinante(cpfCnpjPnc);	
			assinaturaNota.setTipoAssinatura((char) '1');
			assinaturaNota.setOrigemAssinatura((char) '1');	
			assinaturaNota.setDtAssinatura(formataDataBanco());					
			assinaturaNotaRepository.save(assinaturaNota); 	
			
			NotaNegociacao notaNegociacao = notaNegociacaoRepository.getReferenceById(numeroNota);
					
			PendenciaAtendimentoNota pendenciaAtendimentoNota = new PendenciaAtendimentoNota();
			pendenciaAtendimentoNota.setNumeroNota(numeroNota);
			pendenciaAtendimentoNota.setMatriculaAtendente(Long.parseLong(matriculaAtendente));
			pendenciaAtendimentoNota.setTipoPendencia(9L);
			pendenciaAtendimentoNota.setDtInicioAtendimentoNota(notaNegociacao.getDataCriacaoNota());
			pendenciaAtendimentoNota.setDtInclusaoPendencia(new Date());			
			pendenciaAtendimentoNotaRepository.save(pendenciaAtendimentoNota);	
				
		}	
		

		if (enviaClienteInputDto.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "").trim().length() == 11) {
			tipoPessoa = PERSON_TYPE_PF;
			tipoDocumento = DOCUMENT_TYPE_CPF;
		} else {
			tipoPessoa = PERSON_TYPE_PJ;	
			tipoDocumento = DOCUMENT_TYPE_CNPJ;
		}
		
		AuditoriaPncEnviaNotaInputDTO auditoriaPncEnviaNotaInputDTO = new AuditoriaPncEnviaNotaInputDTO();
		auditoriaPncEnviaNotaInputDTO = AuditoriaPncEnviaNotaInputDTO.builder()
				
				.cpfCnpj(enviaClienteInputDto.getCpfCnpj().trim())
				.matriculaAtendente(matriculaAtendente)
				.statusRetornoSicli(String.valueOf(true))
				.statusRetornoIdPositiva(String.valueOf(true))
				.dataEnvioNota(String.valueOf(formataData(new Date())))
				.numeroProtocolo(enviaClienteInputDto.getNumeroProtocolo())				
				.numeroContaAtendimento(enviaClienteInputDto.getNumeroConta())
				.numeroNota(String.valueOf(numeroNota))
				.versaoSistema(enviaClienteInputDto.getVersaoSistema())
				.ipUsuario(tokenUtils.getIpFromToken(token))
				.tipoPessoa(tipoPessoa)
				.produto(enviaClienteInputDto.getProduto())	
			    .build();

		String descricaoTransacao = null;

		try {
			descricaoTransacao = mapper.writeValueAsString(auditoriaPncEnviaNotaInputDTO);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoTransacao(descricaoTransacao)
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


}
