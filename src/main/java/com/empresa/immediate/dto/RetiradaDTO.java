package com.empresa.immediate.dto;

import javax.validation.Valid;
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
@XmlRootElement(name = "RetiradaDTO")
public class RetiradaDTO {

	@Valid
	@XmlElement(name = "saque")
	private SaqueTrocoDTO saque;

	@Valid
	@XmlElement(name = "saque")
	private SaqueTrocoDTO troco;

	public RetiradaDTO() {
		super();
	}

}
