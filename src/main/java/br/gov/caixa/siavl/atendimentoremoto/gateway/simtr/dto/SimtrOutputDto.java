package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto;

import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
	@XmlElement(name = "documentos")
	private List<SimtrDocumentoDto> documentos;

	@Valid
	@XmlElement(name = "tipoPessoa")
	private String tipoPessoa;

	@Valid
	@XmlElement(name = "idDossie")
	private String idDossie;

}
