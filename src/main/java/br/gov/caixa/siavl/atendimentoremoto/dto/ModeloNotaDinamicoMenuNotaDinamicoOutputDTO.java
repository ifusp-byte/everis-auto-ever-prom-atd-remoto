package br.gov.caixa.siavl.atendimentoremoto.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

	@XmlElement(name = "idModeloNota")
	private String idModeloNota;

	@XmlElement(name = "idCampoModeloNota")
	private String idCampoModeloNota;

	@XmlElement(name = "numeroOrdemCampo")
	private String numeroOrdemCampo;

	@XmlElement(name = "nomeCampoModeloNota")
	private String nomeCampoModeloNota;

	@XmlElement(name = "campoDefinido")
	private String campoDefinido;

	@XmlElement(name = "campoEditavel")
	private String campoEditavel;

	@XmlElement(name = "campoObrigatorio")
	private String campoObrigatorio;

	@XmlElement(name = "espacoReservado")
	private String espacoReservado;

	@XmlElement(name = "tipoDadoCampo")
	private String tipoDadoCampo;

	@XmlElement(name = "tipoEntradaCampo")
	private String tipoEntradaCampo;

	@XmlElement(name = "descricaoCampo")
	private String descricaoCampo;

	@XmlElement(name = "quantidadeCaracterCampo")
	private String quantidadeCaracterCampo;

	@XmlElement(name = "valorInicialCampo")
	private String valorInicialCampo;

	@XmlElement(name = "mascaraCampo")
	private String mascaraCampo;

	@XmlElement(name = "numeroConteudoCampoMultiplo")
	private String numeroConteudoCampoMultiplo;

	@XmlElement(name = "descricaoConteudoCampoMultiplo")
	private String descricaoConteudoCampoMultiplo;

}
