package br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;

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
@XmlRootElement(name = "CriaDesafioOutputDTO")
public class CriaDesafioOutputDTO {

	@JsonRawValue
	@XmlElement(name = "response")
	private String response;

	@XmlElement(name = "statusCode")
	private String statusCode;

	@XmlElement(name = "statusMessage")
	private String statusMessage;

	@XmlElement(name = "statusCreated")
	private boolean statusCreated;

	@XmlElement(name = "dataCreated")
	private String dataCreated;

}
