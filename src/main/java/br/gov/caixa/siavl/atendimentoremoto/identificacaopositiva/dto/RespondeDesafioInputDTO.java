package br.gov.caixa.siavl.atendimentoremoto.identificacaopositiva.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RespondeDesafioInputDTO")
public class RespondeDesafioInputDTO {
	
	@XmlElement(name = "request")
	private String listaResposta;
	
	@XmlElement(name = "protocolo")
	private String protocolo;

}
