package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EnviaDocumentoInputDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codGED;

	@JsonRawValue
	private String listaCamposDinamico;

	private MultipartFile arquivoContrato;
}
