package com.empresa.immediate.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ViolacaoDTO")
public class ViolacaoDTO {

	@XmlElement(name = "razao")
	private String razao;

	@XmlElement(name = "propriedade")
	private String propriedade;

	public ViolacaoDTO() {
		super();
	}

	public ViolacaoDTO(String razao, String propriedade) {
		super();
		this.razao = razao;
		this.propriedade = propriedade;
	}

}
