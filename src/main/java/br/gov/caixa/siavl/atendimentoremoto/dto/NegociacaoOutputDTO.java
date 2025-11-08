package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement(name = "NegociacaoOutputDTO")
public class NegociacaoOutputDTO {

	@Valid
	@XmlElement(name = "camposNota")
	private List<CamposNotaOutputDTO> camposNota;

	@Valid
	@XmlElement(name = "qtItemNotaNegociacao")
	private int qtItemNotaNegociacao;
}
