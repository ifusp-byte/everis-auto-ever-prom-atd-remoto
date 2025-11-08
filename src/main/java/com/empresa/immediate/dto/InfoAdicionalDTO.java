package com.empresa.immediate.dto;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@JsonInclude(Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "InfoAdicionalDTO")
public class InfoAdicionalDTO {

	@Valid
	@Size(max = 50)
	@XmlElement(name = "nome")
	private String nome;

	@Valid
	@Size(max = 200)
	@XmlElement(name = "valor")
	private String valor;

	public InfoAdicionalDTO() {
		super();
	}

}
