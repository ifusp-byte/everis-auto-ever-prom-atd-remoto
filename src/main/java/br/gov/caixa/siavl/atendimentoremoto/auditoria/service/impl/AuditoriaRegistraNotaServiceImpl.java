package br.gov.caixa.siavl.atendimentoremoto.auditoria.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialClob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaRegistraNotaDsLogPlataformaDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.model.LogPlataforma;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.repository.LogPlataformaRepository;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
public class AuditoriaRegistraNotaServiceImpl implements AuditoriaRegistraNotaService {
	
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	LogPlataformaRepository logPlataformaRepository;
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Long TRANSACAO_SISTEMA_SUCESSO_SICLI = 189L;
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	
	private final static Logger LOG = Logger.getLogger(AuditoriaRegistraNotaServiceImpl.class.getName());
	
	public void auditar(String dataRegistroNota, String token, String cpfCnpj, String matriculaAtendente, String statusRetornoSicli, String numeroProtocolo, String numeroContaAtendimento, String numeroNota, String versaoSistema, String produto) {

		String tipoPessoa = null;  
		
		if (cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim().length() == 11) {
			tipoPessoa = PERSON_TYPE_PF;
		} else {	
			tipoPessoa = PERSON_TYPE_PJ;	
		}
		
		LogPlataforma logPlataforma = new LogPlataforma();
		AuditoriaRegistraNotaDsLogPlataformaDTO dsLogPlataformaDTO = new AuditoriaRegistraNotaDsLogPlataformaDTO();
		
		dsLogPlataformaDTO = AuditoriaRegistraNotaDsLogPlataformaDTO.builder()
				.cpfCnpj(cpfCnpj)
				.matriculaAtendente(matriculaAtendente)
				.statusRetornoSicli(statusRetornoSicli)
				.numeroProtocolo(numeroProtocolo)
				.numeroContaAtendimento(numeroContaAtendimento)
				.numeroNota(numeroNota)
				.dataRegistroNota(dataRegistroNota)
				.versaoSistema(versaoSistema)
				.ipUsuario(tokenUtils.getIpFromToken(token))
				.tipoPessoa(tipoPessoa)
				.transacaoSistema(TRANSACAO_SISTEMA_SUCESSO_SICLI)
				.produto(produto)
				.build();

		String dsLogPlataformaJson = null;
		Clob dsLogPlataformaClob = null;

		try {
			dsLogPlataformaJson = mapper.writeValueAsString(dsLogPlataformaDTO);
			dsLogPlataformaClob = new SerialClob(dsLogPlataformaJson.toCharArray());
		} catch (JsonProcessingException | SQLException e) {
			throw new RuntimeException(e);
		}

		logPlataforma = LogPlataforma.builder()
				.transacaoSistema(TRANSACAO_SISTEMA_SUCESSO_SICLI)
				.matriculaAtendente(Long.parseLong(matriculaAtendente.replaceAll("[a-zA-Z]", "")))
				.dataCriacaoLogPlataforma(formataDataBanco())
				.ipUsuario(tokenUtils.getIpFromToken(token))
				.versaoSistemaAgenciaVirtual(versaoSistema)
				.cpfCnpj(Long.parseLong(cpfCnpj.replace(".", "").replace("-", "").trim()))
				.tipoPessoa(tipoPessoa)
				.anoMesReferencia(Long.parseLong(formataDataAnoMes(new Date()).replace("-", "")))
				.jsonLogPlataforma(dsLogPlataformaClob)
				.numeroNota(Long.parseLong(numeroNota))
				.build();

		logPlataformaRepository.save(logPlataforma);

	}
	
	private Date formataDataBanco() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		return time.getTime();
	}

	private String formataDataAnoMes(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}
}
