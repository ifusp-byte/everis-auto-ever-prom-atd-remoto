package br.gov.caixa.siavl.atendimentoremoto.siecm.dto;

import javax.validation.Valid;
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
@XmlRootElement(name = "CriaDossieOutputDto")
public class CriaDossieOutputDto {

	@Valid
	@XmlElement(name = "operacao")
	private String operacao;

	@Valid
	@XmlElement(name = "codigoRetorno")
	private String codigoRetorno;

	@Valid
	@XmlElement(name = "mensagem")
	private String mensagem;

}
