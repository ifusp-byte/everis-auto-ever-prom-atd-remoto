package br.gov.caixa.siavl.atendimentoremoto.report.dto;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
@XmlRootElement(name = "ReportInputDTO")
public class ReportInputDTO {

	@Valid
	@XmlElement(name = "numeroNota")
	private String nomeRelatorio;

	@Valid
	@XmlElement(name = "parametrosRelatorio")
	private String parametrosRelatorio;

}
