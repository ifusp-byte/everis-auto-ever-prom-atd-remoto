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
@XmlRootElement(name = "ModeloNotaDinamicoMenuNotaDinamicoOutputDTO")
public class ModeloNotaDinamicoMenuNotaDinamicoOutputDTO {

	@Valid
	@JsonIgnore
	@XmlElement(name = "idModeloNota")
	private String idModeloNota;
	
	@Valid
	@XmlElement(name = "idCampoModeloNota")
	private String idCampoModeloNota;
	
	@Valid
	@XmlElement(name = "numeroOrdemCampo")
	private String numeroOrdemCampo;

	@Valid
	@XmlElement(name = "nomeCampoModeloNota")
	private String nomeCampoModeloNota;

	@Valid
	@XmlElement(name = "campoDefinido")
	private String campoDefinido;

	@Valid
	@XmlElement(name = "campoEditavel")
	private String campoEditavel;

	@Valid
	@XmlElement(name = "campoObrigatorio")
	private String campoObrigatorio;

	@Valid
	@XmlElement(name = "espacoReservado")
	private String espacoReservado;

	@Valid
	@XmlElement(name = "tipoDadoCampo")
	private String tipoDadoCampo;

	@Valid
	@XmlElement(name = "tipoEntradaCampo")
	private String tipoEntradaCampo;

	@Valid
	@XmlElement(name = "descricaoCampo")
	private String descricaoCampo;

	@Valid
	@XmlElement(name = "quantidadeCaracterCampo")
	private String quantidadeCaracterCampo;

	@Valid
	@XmlElement(name = "valorInicialCampo")
	private String valorInicialCampo;

	@Valid
	@XmlElement(name = "mascaraCampo")
	private String mascaraCampo;

	List<ModeloNotaDinamicoMenuNotaDinamicoCamposOutputDTO> options;

}
