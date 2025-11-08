package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "EnviaDocumentoInputDto")
public class EnviaDocumentoInputDto {

	private String codGED;

	private List<Object> listaCamposDinamico;

	private String arquivoContrato;

	private String numeroNota;

	private String nomeTipoDocumento;

	private String tipoDocumento;

	private String nomeAnexo;

}
