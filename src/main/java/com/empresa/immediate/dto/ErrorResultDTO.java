package com.empresa.immediate.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Validated
@JsonInclude(Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ErrorResultDTO")
public class ErrorResultDTO {

	@XmlElement(name = "description")
	private String description;

	@XmlElement(name = "responseCode")
	private String responseCode;

	@XmlElement(name = "type")
	private String type;

	@XmlElement(name = "title")
	private String title;

	@XmlElement(name = "status")
	private int status;

	@XmlElement(name = "detail")
	private String detail;

	@XmlElement(name = "correlationId")
	private String correlationId;

	@XmlElement(name = "violacoes")
	private List<ViolacaoDTO> violacoes;

	public ErrorResultDTO(String description, String responseCode, String type, String title, int status, String detail,
			String correlationId, List<ViolacaoDTO> violacoes) {
		super();
		this.description = description;
		this.responseCode = responseCode;
		this.type = type;
		this.title = title;
		this.status = status;
		this.detail = detail;
		this.correlationId = correlationId;
		this.violacoes = violacoes;
	}

}
