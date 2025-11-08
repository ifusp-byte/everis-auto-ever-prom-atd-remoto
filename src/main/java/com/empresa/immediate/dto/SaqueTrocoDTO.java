package com.empresa.immediate.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Null;
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
@XmlRootElement(name = "SaqueTrocoDTO")
public class SaqueTrocoDTO {

	@Valid
	@Null
	@XmlElement(name = "valor")
	private String valor;

	@Valid
	@Max(0)
	@XmlElement(name = "modalidadeAlteracao")
	private int modalidadeAlteracao;

	@Valid
	@Null
	@XmlElement(name = "modalidadeAgente")
	private String modalidadeAgente;

	@Valid
	@Null
	@XmlElement(name = "prestadorDoServicoDeSaque")
	private String prestadorDoServicoDeSaque;

	public SaqueTrocoDTO() {
		super();

	}

}
