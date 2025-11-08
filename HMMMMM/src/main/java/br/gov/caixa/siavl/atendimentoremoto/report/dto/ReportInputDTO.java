package br.gov.caixa.siavl.atendimentoremoto.report.dto;

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
@XmlRootElement(name = "ReportInputDTO")
public class ReportInputDTO {

	@Valid
	@XmlElement(name = "nomeRelatorio")
	private Object nomeRelatorio;

	@Valid
	@XmlElement(name = "roteiroRelatorio")
	private Object roteiroRelatorio;

	@Valid
	@XmlElement(name = "parametrosRelatorio")
	private Object parametrosRelatorio;

}
