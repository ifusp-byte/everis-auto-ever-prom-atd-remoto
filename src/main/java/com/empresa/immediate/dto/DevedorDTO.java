package com.empresa.immediate.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
@XmlRootElement(name = "DevedorDTO")
public class DevedorDTO {

	@Valid
	@Pattern(regexp = "^\\d{14}$")
	@XmlElement(name = "cnpj")
	private String cnpj;

	@Valid
	@Pattern(regexp = "^\\d{11}$")
	@XmlElement(name = "cpf")
	private String cpf;

	@Valid
	@Size(max = 200)
	@XmlElement(name = "nome")
	private String nome;

	public DevedorDTO() {
		super();

	}

	public DevedorDTO(@Valid @Pattern(regexp = "^\\d{14}$") String cnpj,
			@Valid @Pattern(regexp = "^\\d{11}$") String cpf, 
			@Valid @NotBlank @Size(max = 200) String nome) {
		super();
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.nome = nome;
	}

}
