package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.BEARER_1;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaSucessoOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaSucessoOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.ErroOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@Component
@SuppressWarnings("all")
public class MetodosUtils {

	private static ObjectMapper mapper = new ObjectMapper();

	public String writeValueAsString(Object object) {

		String value = null;

		try {
			value = mapper.writeValueAsString(object).replaceAll("\\u005C", "").replaceAll("\\n", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public static String writeAsString(Object object) {

		String value = null;

		try {
			value = mapper.writeValueAsString(object).replaceAll("\\u005C", "").replaceAll("\\n", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public String writeValueAsStringXml(Object object) {

		String value = null;

		try {
			value = mapper.writeValueAsString(object).replaceAll("\\n", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public String writeValueAsStringPnc(Object object) {

		String value = null;

		try {
			value = mapper.writeValueAsString(object).replaceAll("\\u005C", "").replaceAll("\\n", "")
					.replaceAll("\\u0022", "");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public JsonNode readTree(Object object) {

		JsonNode value = null;

		try {
			value = mapper.readTree(String.valueOf(object));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public static JsonNode read(Object object) {

		JsonNode value = null;

		try {
			value = mapper.readTree(String.valueOf(object));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	public Clob newSerialClob(String object) {

		Clob clob = null;

		try {
			clob = new SerialClob(object.toCharArray());
		} catch (SerialException e) {

			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return clob;

	}

	public static ResponseEntity response(HttpStatus status, Object body) {
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(body);
	}

	public static ResponseEntity notaErroOutputBuild(int status, String message, List<ViolacaoDto> body) {
		ErroOutputDto erroOutputDto = new ErroOutputDto();
		erroOutputDto.setCodErro(status);
		erroOutputDto.setDescricao(message);
		erroOutputDto.setViolacoes(body);
		return response(HttpStatus.valueOf(status), erroOutputDto);
	}

	public static ResponseEntity criaNotaSucessoOutputBuild(int status, String message,
			CriaNotaInputDTO criaNotaInputDto) {
		CriaNotaSucessoOutputDto criaNotaSucessoOutputDto = new CriaNotaSucessoOutputDto();
		criaNotaSucessoOutputDto.setMensagem(message);
		criaNotaSucessoOutputDto.setDadosDaNota(criaNotaInputDto.getRegistraNotaDto());
		return response(HttpStatus.valueOf(status), criaNotaSucessoOutputDto);
	}

	public static ResponseEntity contrataNotaSucessoOutputBuild(int status, String message,
			ContrataNotaInputDTO contrataNotaInputDTO) {
		ContrataNotaSucessoOutputDto contrataNotaSucessoOutputDto = new ContrataNotaSucessoOutputDto();
		contrataNotaSucessoOutputDto.setMensagem(message);
		contrataNotaSucessoOutputDto.setDadosDaNota(contrataNotaInputDTO);
		return response(HttpStatus.valueOf(status), contrataNotaSucessoOutputDto);
	}

	public static String converterMascaraParaRegex(String mascara) {
		StringBuilder regex = new StringBuilder();

		for (char c : mascara.toCharArray()) {
			if (c == '9') {
				regex.append("\\d");
			} else {
				regex.append("\\").append(c);
			}
		}
		return regex.toString();
	}

	public static boolean validarValorComMascara(String valor, String mascara) {
		String regex = converterMascaraParaRegex(mascara);
		return !valor.matches(regex);
	}

	public static String getToken(String token) {
		return token.trim().replace(BEARER_1, StringUtils.EMPTY);
	}

	public static Object newObjectFromFileContrataNota(String filePath) {

		try {
			return mapper.readValue(new ClassPathResource(filePath).getFile(), ContrataNotaInputDTO.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
