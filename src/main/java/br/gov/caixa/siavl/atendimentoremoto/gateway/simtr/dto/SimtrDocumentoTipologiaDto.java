package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement(name = "SimtrDocumentoTipologiaDto")
public class SimtrDocumentoTipologiaDto {

	@Valid
	@XmlElement(name = "identidade")
	private List<SimtrDocumentoDto> identidade;

	@Valid
	@XmlElement(name = "endereco")
	private List<SimtrDocumentoDto> endereco;

	@Valid
	@XmlElement(name = "renda")
	private List<SimtrDocumentoDto> renda;

	@Valid
	@XmlElement(name = "desconhecido")
	private List<SimtrDocumentoDto> desconhecido;

}
