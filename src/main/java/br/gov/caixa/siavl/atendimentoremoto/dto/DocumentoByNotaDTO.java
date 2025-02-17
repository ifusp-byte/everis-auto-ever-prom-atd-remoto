package br.gov.caixa.siavl.atendimentoremoto.dto;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
@SuppressWarnings("all")
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DocumentoByNotaDTO")
public class DocumentoByNotaDTO {

	@Valid
	@XmlElement(name = "numeroNota")
	private String numeroNota;

	@Valid
	@XmlElement(name = "numeroTipoDocumento")
	private String numeroTipoDocumento;

	@Valid
	@XmlElement(name = "nomeTipoDocumento")
	private String nomeTipoDocumento;

	@Valid
	@XmlElement(name = "codGed")
	private String codGed;

	@Valid
	@XmlElement(name = "nomeAnexo")
	private String nomeAnexo;

	@Valid
	@XmlElement(name = "categoriaDocumento")
	private String categoriaDocumento;
	
	@Valid
	@XmlElement(name = "nomeAnexoFormatado")
	private String nomeAnexoFormatado;

}
