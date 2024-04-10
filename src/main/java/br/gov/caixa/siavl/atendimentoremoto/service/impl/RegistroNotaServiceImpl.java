package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.sql.rowset.serial.SerialClob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncRegistraNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.gateway.AuditoriaPncGateway;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.model.RelatorioNotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
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
	TokenUtils tokenUtils;

	private static final String DEFAULT_USER_IP = "123";

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object registraNota(String token, Long numeroNota, RegistraNotaInputDto registraNotaInputDto)
			throws Exception {

		Long cpfCnpjPnc = Long.parseLong(registraNotaInputDto.getCpfCnpj().trim());
		Long numeroUnidade = Long.parseLong(tokenUtils.getUnidadeFromToken(token));
		Long numeroEquipe = null;
		String cpfCnpj = null;
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", "");
		boolean statusRetornoSicli = true;
		String numeroProtocolo = registraNotaInputDto.getNumeroProtocolo();
		String numeroContaAtendimento = registraNotaInputDto.getContaAtendimento();
		String versaoSistema = registraNotaInputDto.getVersaoSistema();

		numeroEquipe = equipeAtendimentoRepository.findEquipeByUnidade(numeroUnidade);

		RegistraNotaOutputDto registraNotaOutputDto = new RegistraNotaOutputDto();

		if (numeroEquipe == null) {

			registraNotaOutputDto = RegistraNotaOutputDto.builder().statusNotaRegistrada(false)
					.mensagem("A unidade não possui equipe vinculada.").build();

		} else {

			AtendimentoCliente atendimentoCliente = new AtendimentoCliente();
			atendimentoCliente.setNumeroProtocolo(Long.parseLong(registraNotaInputDto.getNumeroProtocolo()));
			atendimentoCliente.setNomeCliente(registraNotaInputDto.getNomeCliente());

			if (registraNotaInputDto.getCpfCnpj().length() == 11) {
				cpfCnpj = registraNotaInputDto.getCpfCnpj();
				atendimentoCliente.setCpfCliente(Long.parseLong(registraNotaInputDto.getCpfCnpj()));
			} else {
				cpfCnpj = registraNotaInputDto.getCpfCnpj();
				atendimentoCliente.setCnpjCliente(Long.parseLong(registraNotaInputDto.getCpfCnpj()));
			}

			String dsRelatorioNota = mapper.writeValueAsString(registraNotaInputDto);
			Clob relatorioNota = new SerialClob(dsRelatorioNota.toCharArray());

			RelatorioNotaNegociacao relatorioNotaNegociacao = new RelatorioNotaNegociacao();
			relatorioNotaNegociacao.setNumeroEquipe(numeroEquipe);
			relatorioNotaNegociacao.setRelatorioNota(relatorioNota);

			relatorioNotaNegociacaoRepository.save(relatorioNotaNegociacao);
			registraNotaOutputDto = RegistraNotaOutputDto.builder().statusNotaRegistrada(true)
					.mensagem("Nota registrada com sucesso!").build();
		}

		AuditoriaPncRegistraNotaInputDTO auditoriaPncRegistraNotaInputDTO = new AuditoriaPncRegistraNotaInputDTO();
		auditoriaPncRegistraNotaInputDTO = AuditoriaPncRegistraNotaInputDTO.builder()

				.cpfCnpj(cpfCnpj).matriculaAtendente(matriculaAtendente)
				.statusRetornoSicli(String.valueOf(statusRetornoSicli))
				.numeroProtocolo(numeroProtocolo)
				.numeroContaAtendimento(numeroContaAtendimento)
				.numeroNota(String.valueOf(numeroNota))
				.dataRegistroNota(String.valueOf(formataData(new Date())))
				.transacaoSistema("189")
				.versaoSistema(versaoSistema)
				.tipoPessoa("PF")
				.ipUsuario("123")
			    .nomeProduto(registraNotaInputDto.getDescricaoAcaoProduto())				
			    .build();

		String descricaoTransacao = null;

		try {
			descricaoTransacao = mapper.writeValueAsString(auditoriaPncRegistraNotaInputDTO);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder().descricaoTransacao(descricaoTransacao)
				.ipTerminalUsuario(DEFAULT_USER_IP).nomeMfe("mfe_avl_atendimentoremoto").numeroUnidadeLotacaoUsuario(50L)
				.ambienteAplicacao("NACIONAL").tipoDocumento("CPF").numeroIdentificacaoCliente(cpfCnpjPnc).build();

		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);
		auditoriaRegistraNotaService.auditar(String.valueOf(formataDataBanco()), token, cpfCnpj, matriculaAtendente,
				String.valueOf(statusRetornoSicli), numeroProtocolo, numeroContaAtendimento, String.valueOf(numeroNota),
				versaoSistema);
		
		return registraNotaOutputDto;

	}

	@Override
	public Boolean enviaCliente(Long numeroNota) {
		Boolean statusContratacao = null;

		notaNegociacaoRepository.enviaNotaCliente(numeroNota);
		statusContratacao = true;

		return statusContratacao;
	}

	private Date formataDataBanco() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		return time.getTime();
	}
	
	private String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}


}
