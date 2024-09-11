package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@XmlRootElement(name = "CamposNotaOutputDTO")
public class CamposNotaOutputDTO {

	@Valid
	@JsonIgnore
	@XmlElement(name = "id")
	private String id;
	
	@Valid
	@XmlElement(name = "idCampo")
	private String idCampo;

	@Valid
	@XmlElement(name = "ordemCampo")
	private String ordemCampo;

	@Valid
	@XmlElement(name = "nome")
	private String nome;

	@Valid
	@XmlElement(name = "predefinido")
	private Boolean predefinido;

	@Valid
	@XmlElement(name = "editavel")
	private Boolean editavel;

	@Valid
	@XmlElement(name = "obrigatorio")
	private Boolean obrigatorio;

	@Valid
	@XmlElement(name = "espacoReservado")
	private String espacoReservado;

	@Valid
	@XmlElement(name = "tipoCampo")
	private String tipoCampo;

	@Valid
	@XmlElement(name = "tipoDado")
	private String tipoDado;

	@Valid
	@XmlElement(name = "descricao")
	private String descricao;

	@Valid
	@XmlElement(name = "tamanhoMaximo")
	private String tamanhoMaximo;

	@Valid
	@XmlElement(name = "valorCampo")
	private String valorCampo;

	@Valid
	@XmlElement(name = "mascaraCampo")
	private String mascaraCampo;

	List<ConteudoCampoMultiploOutPutDTO> conteudoCampoMultiplo;

}
