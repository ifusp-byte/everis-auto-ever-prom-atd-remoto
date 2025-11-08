package com.empresa.immediate.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Null;
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
@XmlRootElement(name = "LocDTO")
public class LocDTO {

	@Valid
	@Null
	@XmlElement(name = "id")
	private int id;

	@Valid
	@Size(max = 77)
	private String location;

	@Valid
	@Size(max = 4)
	private String tipoCob;

	@Valid
	private Date criacao;

	public LocDTO() {
		super();
	}

}
