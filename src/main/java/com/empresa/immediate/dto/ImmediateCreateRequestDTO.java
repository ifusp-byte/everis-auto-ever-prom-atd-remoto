package com.empresa.immediate.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.empresa.immediate.util.annotation.ValorDTOAnnotation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ImmediateCreateRequestDTO")
public class ImmediateCreateRequestDTO {

	@Valid
	@NotNull
	@NotBlank
	@Size(max = 77)
	@XmlElement(name = "chave")
	private String chave;

	@Valid
	@Size(max = 140)
	@XmlElement(name = "solicitacaoPagador")
	private String solicitacaoPagador;

	@Valid
	@Size(max = 50)
	@XmlElement(name = "infoAdicionais")
	private List<InfoAdicionalDTO> infoAdicionais;

	@Valid
	@NotBlank
	@Size(min = 26, max = 35)
	@Pattern(regexp = "^[a-zA-Z\\d-_]+$")
	@XmlElement(name = "txid")
	private String txid;

	@Valid
	@XmlElement(name = "calendario")
	private CalendarioDTO calendario;

	@Valid
	@XmlElement(name = "devedor")
	private DevedorDTO devedor;

	@Valid
	@ValorDTOAnnotation
	@XmlElement(name = "valor")
	private ValorDTO valor;

	@Valid
	@Null
	@XmlElement(name = "loc")
	private LocDTO loc;

	public ImmediateCreateRequestDTO() {
		super();
	}

}
