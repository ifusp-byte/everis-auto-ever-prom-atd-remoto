package br.gov.caixa.siavl.atendimentoremoto.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ModeloType")
public class ModeloOutputDto {

	@XmlElement(name = "idModelo")
	private Long idModelo;

	@XmlElement(name = "isAutomatica")
	private boolean isAutomatica;

	@XmlElement(name = "idProduto")
	private Long idProduto;

	@XmlElement(name = "descProduto")
	private String descProduto;

}
