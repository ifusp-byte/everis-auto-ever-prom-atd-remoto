package br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.Hidden;
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
@XmlRootElement(name = "ValidaDesafioDTO")
public class ValidaDesafioDTO {

	@XmlElement(name = "response")
	private String response;

	@XmlElement(name = "canal")
	private String canal;

	@XmlElement(name = "tsAtualizacao")
	private String tsAtualizacao;

	@XmlElement(name = "status")
	private String status;

	@XmlElement(name = "desafioExpirado")
	private Boolean desafioExpirado;

	@XmlElement(name = "mensagem")
	private String mensagem;

	@XmlElement(name = "statusCode")
	private String statusCode;

	@XmlElement(name = "statusMessage")
	private String statusMessage;

	@Hidden
	@XmlElement(name = "statusCreated")
	private boolean statusCreated;

	@XmlElement(name = "dataCreated")
	private String dataCreated;

}
