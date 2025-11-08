package com.empresa.immediate.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
@XmlRootElement(name = "CalendarioDTO")
public class CalendarioDTO {

	@XmlElement(name = "criacao")
	private Date criacao;

	@Valid
	@NotNull
	@Min(1)
	@XmlElement(name = "expiracao")
	private int expiracao;

	public CalendarioDTO() {
		super();
	}

	public CalendarioDTO(Date criacao, @Valid @NotNull @Min(1) int expiracao) {
		super();
		this.criacao = criacao;
		this.expiracao = expiracao;
	}

}
