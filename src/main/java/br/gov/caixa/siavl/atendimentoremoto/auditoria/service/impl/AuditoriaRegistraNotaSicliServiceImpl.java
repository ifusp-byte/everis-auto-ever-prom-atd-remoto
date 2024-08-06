package br.gov.caixa.siavl.atendimentoremoto.auditoria.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.sql.rowset.serial.SerialClob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaRegistraNotaSicliDsLogPlataformaDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.model.LogPlataforma;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.repository.LogPlataformaRepository;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaSicliService;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.dto.ContaAtendimentoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class AuditoriaRegistraNotaSicliServiceImpl implements AuditoriaRegistraNotaSicliService {
	
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	LogPlataformaRepository logPlataformaRepository;
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Long TRANSACAO_SISTEMA_SUCESSO_SICLI = 284L;
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	
	public void auditar(ContaAtendimentoOutputDTO contaAtendimento, String token, String cpfCnpj) {
		
		String tipoPessoa = null;  
		
		if (cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim().length() == 11) {
			tipoPessoa = PERSON_TYPE_PF;
		} else {	
			tipoPessoa = PERSON_TYPE_PJ;	
		}
		
		LogPlataforma logPlataforma = new LogPlataforma();
		AuditoriaRegistraNotaSicliDsLogPlataformaDTO dsLogPlataformaDTO = new AuditoriaRegistraNotaSicliDsLogPlataformaDTO();
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", "");
		
		dsLogPlataformaDTO = AuditoriaRegistraNotaSicliDsLogPlataformaDTO.builder()			
				.contaAtendimento(contaAtendimento)
				.cpfCnpj(cpfCnpj)
				.matriculaAtendente(matriculaAtendente)
				.dataChamadaSicli(formataData(new Date()))
				.versaoSistema("1.7.0_API")
				.ipUsuario(tokenUtils.getIpFromToken(token))
				.tipoPessoa(tipoPessoa)
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
				.versaoSistemaAgenciaVirtual("1.7.0_API")
				.cpfCnpj(Long.parseLong(cpfCnpj.replace(".", "").replace("-", "").trim()))
				.tipoPessoa(tipoPessoa)
				.anoMesReferencia(Long.parseLong(formataDataAnoMes(new Date()).replace("-", "")))
				.jsonLogPlataforma(dsLogPlataformaClob)
				.build();

		logPlataformaRepository.save(logPlataforma);
			
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

	private String formataDataAnoMes(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

}
