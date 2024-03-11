package br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRawValue;

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
