package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;

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
@XmlRootElement(name = "SiecmOutputDto")
public class SiecmOutputDto {

	@Valid
	@JsonRawValue
	@XmlElement(name = "response")
	private String response;

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
	@XmlElement(name = "dataCreated")
	private String dataCreated;

	@Valid
	@XmlElement(name = "linkThumbnail")
	private String linkThumbnail;

	@Valid
	@XmlElement(name = "id")
	private String id;

	@Valid
	@XmlElement(name = "nomeAnexo")
	private String nomeAnexo;

}
