package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SimtrOutputDto")
public class SimtrOutputDto {

	@Valid
	@XmlElement(name = "statusCode")
	private String statusCode;

	@Valid
	@XmlElement(name = "statusMessage")
	private String statusMessage;

	@Valid
	@XmlElement(name = "statusCreated")
	private boolean statusCreated;

	@Valid
	@XmlElement(name = "tipoPessoa")
	private String tipoPessoa;

	@Valid
	@XmlElement(name = "idDossie")
	private String idDossie;

	@Valid
	@XmlElement(name = "documentos")
	private SimtrDocumentoTipologiaDto documentos;

	@Valid
	@Hidden
	@XmlElement(name = "binario")
	private String binario;

	@Valid
	@Hidden
	@XmlElement(name = "mimeType")
	private String mimeType;

	@Valid
	@Hidden
	@XmlElement(name = "extensao")
	private String extensao;

	@Valid
	@Hidden
	@XmlElement(name = "tipologia")
	private String tipologia;

}
