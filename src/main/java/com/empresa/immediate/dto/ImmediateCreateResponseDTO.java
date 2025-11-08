package com.empresa.immediate.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Validated
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ImmediateCreateResponseDTO")
public class ImmediateCreateResponseDTO {

	@XmlElement(name = "calendario")
	private CalendarioDTO calendario;

	@XmlElement(name = "txid")
	private String txid;

	@XmlElement(name = "revisao")
	private int revisao;

	@XmlElement(name = "loc")
	private LocDTO loc;

	@Valid
	@Size(max = 31)
	@XmlElement(name = "status")
	private String status;

	@XmlElement(name = "devedor")
	private DevedorDTO devedor;

	@XmlElement(name = "valor")
	private ValorDTO valor;

	@Valid
	@Size(max = 512)
	@XmlElement(name = "pixCopiaECola")
	private String pixCopiaECola;

	@XmlElement(name = "chave")
	private String chave;

	@XmlElement(name = "solicitacaoPagador")
	private String solicitacaoPagador;

	@XmlElement(name = "infoAdicionais")
	private List<InfoAdicionalDTO> infoAdicionais;

}
