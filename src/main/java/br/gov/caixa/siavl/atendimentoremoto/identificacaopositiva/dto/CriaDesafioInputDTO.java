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
@XmlRootElement(name = "CriaDesafioInputDTO")
public class CriaDesafioInputDTO {
	
	@XmlElement(name = "cpf")
	private String cpf;
	
	@XmlElement(name = "nis")
	private String nis;
	
	@XmlElement(name = "outro-identificador")
	private String outroIdentificador;
	
	@XmlElement(name = "nome-servico")
	private String nomeServico;

}
