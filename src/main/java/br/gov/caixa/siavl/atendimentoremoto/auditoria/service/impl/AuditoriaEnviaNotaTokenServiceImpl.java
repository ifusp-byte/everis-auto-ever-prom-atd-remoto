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

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaEnvioNotaTokenDsLogPlataformaDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.model.LogPlataforma;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.repository.LogPlataformaRepository;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaEnviaNotaTokenService;
import br.gov.caixa.siavl.atendimentoremoto.repository.DocumentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.DocumentoNotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.TipoDocumentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class AuditoriaEnviaNotaTokenServiceImpl implements AuditoriaEnviaNotaTokenService {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	LogPlataformaRepository logPlataformaRepository;

	@Autowired
	TipoDocumentoRepository tipoDocumentoRepository;

	@Autowired
	DocumentoClienteRepository documentoClienteRepository;

	@Autowired
	DocumentoNotaNegociacaoRepository documentoNotaNegociacaoRepository;

	private static ObjectMapper mapper = new ObjectMapper();
	private static final Long TRANSACAO_SISTEMA_CONTRATA_NOTA_SMS = 304L;
	private static final Long TRANSACAO_SISTEMA_CONTRATA_NOTA_SMS_ERRO = 302L;
	private static final String STEP3_COMPONENTE_TOKEN = "step3_componente_token";
	private static final String PERSON_TYPE_PF = "PF";
	private static final String PERSON_TYPE_PJ = "PJ";

	public void auditar(String dataRegistroNota, String token, String cpfCnpj, String matriculaAtendente,
			String statusRetornoSicli, String numeroProtocolo, String numeroContaAtendimento, String numeroNota,
			String versaoSistema, String produto, String cpfSocio, String assinaturaToken, String tokenValido, String tokenValidoTelefone, String tipoAssinatura) {

		String tipoPessoa = null;
		String cpfConsulta = cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim();

		if (cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim().length() == 11) {
			tipoPessoa = PERSON_TYPE_PF;
		} else {
			tipoPessoa = PERSON_TYPE_PJ;
		}

		LogPlataforma logPlataforma = new LogPlataforma();
		AuditoriaEnvioNotaTokenDsLogPlataformaDTO dsLogPlataformaDTO = new AuditoriaEnvioNotaTokenDsLogPlataformaDTO();

		dsLogPlataformaDTO.setTipoAssinatura(tipoAssinatura);
		dsLogPlataformaDTO.setCpfCnpj(cpfCnpj);
		dsLogPlataformaDTO.setCpfSocio(cpfSocio);
		dsLogPlataformaDTO.setMatriculaAtendente(matriculaAtendente);
		dsLogPlataformaDTO.setStatusRetornoSicli(statusRetornoSicli);
		dsLogPlataformaDTO.setNumeroProtocolo(numeroProtocolo);
		dsLogPlataformaDTO.setNumeroContaAtendimento(numeroContaAtendimento);
		dsLogPlataformaDTO.setNumeroNota(numeroNota);
		dsLogPlataformaDTO.setDataRegistroNota(dataRegistroNota);
		dsLogPlataformaDTO.setVersaoSistema(versaoSistema);
		dsLogPlataformaDTO.setIpUsuario(tokenUtils.getIpFromToken(token));
		dsLogPlataformaDTO.setTipoPessoa(tipoPessoa);
		dsLogPlataformaDTO.setTransacaoSistema(Boolean.TRUE.equals(Boolean.parseBoolean(tokenValido)) ? TRANSACAO_SISTEMA_CONTRATA_NOTA_SMS : TRANSACAO_SISTEMA_CONTRATA_NOTA_SMS_ERRO);
		dsLogPlataformaDTO.setProduto(produto);
		dsLogPlataformaDTO.setAssinaturaToken(assinaturaToken);
		dsLogPlataformaDTO.setTokenValido(tokenValido);
		dsLogPlataformaDTO.setTokenValidoTelefone(tokenValidoTelefone);
		
		String dsLogPlataformaJson = null;
		Clob dsLogPlataformaClob = null;

		try {
			dsLogPlataformaJson = mapper.writeValueAsString(dsLogPlataformaDTO);
			dsLogPlataformaClob = new SerialClob(dsLogPlataformaJson.toCharArray());
		} catch (JsonProcessingException | SQLException e) {
			throw new RuntimeException(e);
		}

		logPlataforma = LogPlataforma.builder().transacaoSistema(Boolean.TRUE.equals(Boolean.parseBoolean(tokenValido)) ? TRANSACAO_SISTEMA_CONTRATA_NOTA_SMS : TRANSACAO_SISTEMA_CONTRATA_NOTA_SMS_ERRO)
				.matriculaAtendente(Long.parseLong(matriculaAtendente.replaceAll("[a-zA-Z]", "")))
				.dataCriacaoLogPlataforma(formataDataBanco()).ipUsuario(tokenUtils.getIpFromToken(token))
				.versaoSistemaAgenciaVirtual(versaoSistema)
				.cpfCnpj(Long.parseLong(cpfCnpj.replace(".", "").replace("-", "").trim())).tipoPessoa(tipoPessoa)
				.anoMesReferencia(Long.parseLong(formataDataAnoMes(new Date()).replace("-", "")))
				.jsonLogPlataforma(dsLogPlataformaClob).numeroNota(Long.parseLong(numeroNota)).build();

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
