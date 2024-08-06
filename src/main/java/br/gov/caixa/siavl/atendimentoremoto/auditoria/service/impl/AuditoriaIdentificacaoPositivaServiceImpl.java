package br.gov.caixa.siavl.atendimentoremoto.auditoria.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaIdentificacaoPositivaDsLogPlataformaDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaIdentificacaoPositivaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.model.LogPlataforma;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.repository.LogPlataformaRepository;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaIdentificacaoPositivaService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class AuditoriaIdentificacaoPositivaServiceImpl implements AuditoriaIdentificacaoPositivaService {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	LogPlataformaRepository logPlataformaRepository;

	private static ObjectMapper mapper = new ObjectMapper();
	private static final Long TRANSACAO_SISTEMA = 144L;
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";
	
	public Boolean auditar(String token,
			AuditoriaIdentificacaoPositivaInputDTO auditoriaIdentificacaoPositivaInputDTO) {
		
		String tipoPessoa = null; 
		String cpfSocio = null; 
		
		if (auditoriaIdentificacaoPositivaInputDTO.getCpfSocio().isBlank()) {		
			tipoPessoa = PERSON_TYPE_PF;		
			cpfSocio = StringUtils.EMPTY;
		} else {	
			cpfSocio = auditoriaIdentificacaoPositivaInputDTO.getCpfSocio();
			tipoPessoa = PERSON_TYPE_PJ;	
		}
		
		boolean statusAudtoria = false;
		LogPlataforma logPlataforma = new LogPlataforma();
		AuditoriaIdentificacaoPositivaDsLogPlataformaDTO dsLogPlataformaDTO = new AuditoriaIdentificacaoPositivaDsLogPlataformaDTO();

		dsLogPlataformaDTO = AuditoriaIdentificacaoPositivaDsLogPlataformaDTO.builder()
				.cpfCnpj(auditoriaIdentificacaoPositivaInputDTO.getCpfCnpj())
				.cpfSocio(cpfSocio)
				.matriculaAtendente(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", ""))
				.statusIdentificacaoPositiva(auditoriaIdentificacaoPositivaInputDTO.getStatusCreated())
				.dataCriacao(formataData(new Date()))
				.numeroProtocolo(auditoriaIdentificacaoPositivaInputDTO.getNumeroProtocolo())
				.versaoSistema(auditoriaIdentificacaoPositivaInputDTO.getVersaoSistemaAgenciaVirtual())
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

		logPlataforma = LogPlataforma.builder().transacaoSistema(TRANSACAO_SISTEMA)
				.matriculaAtendente(Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", "")))
				.dataCriacaoLogPlataforma(formataDataBanco())
				.ipUsuario(tokenUtils.getIpFromToken(token))
				.versaoSistemaAgenciaVirtual(auditoriaIdentificacaoPositivaInputDTO.getVersaoSistemaAgenciaVirtual())
				.cpfCnpj(Long.parseLong(auditoriaIdentificacaoPositivaInputDTO.getCpfCnpj().replace(".", "").replace("-", "").trim()))
				.tipoPessoa(tipoPessoa)
				.anoMesReferencia(Long.parseLong(formataDataAnoMes(new Date()).replace("-", "")))
				.jsonLogPlataforma(dsLogPlataformaClob).build();

		logPlataforma = logPlataformaRepository.save(logPlataforma);

		if (logPlataforma.getIdLogPlataforma() != null) {
			statusAudtoria = true;
		}

		return statusAudtoria;

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
