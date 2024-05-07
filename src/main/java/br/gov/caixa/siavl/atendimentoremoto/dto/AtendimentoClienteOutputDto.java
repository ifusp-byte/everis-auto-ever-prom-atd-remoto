package br.gov.caixa.siavl.atendimentoremoto.dto;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AtendimentoClienteOutputDto")
public class AtendimentoClienteOutputDto {

	@Valid
	@XmlElement(name = "cpfCliente")
	private String cpfCliente;

	@Valid
	@XmlElement(name = "cnpjCliente")
	private String cnpjCliente;

	@Valid
	@XmlElement(name = "nomeCliente")
	private String nomeCliente;

	@Valid
	@XmlElement(name = "produtoDescricao")
	private String produtoDescricao;

	@Valid
	@XmlElement(name = "dataCriacaoNota")
	private String dataCriacaoNota;

	@Valid
	@XmlElement(name = "idNegociacao")
	private String idNegociacao;

	@Valid
	@XmlElement(name = "descricaoNota")
	private String descricaoNota;

}
