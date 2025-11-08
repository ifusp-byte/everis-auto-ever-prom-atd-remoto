package com.empresa.immediate.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
@XmlRootElement(name = "ValorDTO")
public class ValorDTO {

	@Valid
	@NotNull
	@NotBlank
	@Size(max = 13)
	@Pattern(regexp = "\\d{1,10}\\.\\d{2}")
	@XmlElement(name = "original")
	private String original;

	@Valid
	@Min(0)
	@Max(1)
	@XmlElement(name = "modalidadeAlteracao")
	private int modalidadeAlteracao;

	@Valid
	@XmlElement(name = "retirada")
	private RetiradaDTO retirada;

	public ValorDTO() {
		super();
	}

	public ValorDTO(@Valid @NotNull @NotBlank @Size(max = 13) @Pattern(regexp = "\\d{1,10}\\.\\d{2}") String original,
			@Valid @Min(0) @Max(1) int modalidadeAlteracao, @Valid RetiradaDTO retirada) {
		super();
		this.original = original;
		this.modalidadeAlteracao = modalidadeAlteracao;
		this.retirada = retirada;
	}

}
