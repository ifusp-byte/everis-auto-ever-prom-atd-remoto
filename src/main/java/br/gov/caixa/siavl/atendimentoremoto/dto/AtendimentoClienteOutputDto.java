package br.gov.caixa.siavl.atendimentoremoto.dto;

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

	@XmlElement(name = "cpfCliente")
	private String cpfCliente;

	@XmlElement(name = "cnpjCliente")
	private String cnpjCliente;

	@XmlElement(name = "nomeCliente")
	private String nomeCliente;

	@XmlElement(name = "produtoDescricao")
	private String produtoDescricao;

	@XmlElement(name = "dataCriacaoNota")
	private String dataCriacaoNota;

	@XmlElement(name = "idNegociacao")
	private String idNegociacao;

	@XmlElement(name = "descricaoNota")
	private String descricaoNota;

}
