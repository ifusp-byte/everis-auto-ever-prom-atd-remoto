package br.gov.caixa.siavl.atendimentoremoto.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CampoType")
public class CampoOutputDto {

	@Valid
	@JsonIgnore
	@XmlElement(name = "idModelo")
	private Long idModelo;
	
	@Valid
	@XmlElement(name = "idCampo")
	private Long idCampo;
	
	@Valid
	@XmlElement(name = "nome")
	private String nome;

	@Valid
	@XmlElement(name = "tipoDado")
	private String tipoDado;

	@Valid
	@XmlElement(name = "qtdCaracteres")
	private String qtdCaracteres;
	
	@Valid
	@XmlElement(name = "isObrigatorio")
	private boolean isObrigatorio;
	
	@Valid
	@XmlElement(name = "isEditavel")
	private boolean isEditavel;
	
	
}
