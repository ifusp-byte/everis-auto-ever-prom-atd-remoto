package br.gov.caixa.siavl.atendimentoremoto.report.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
@XmlRootElement(name = "RoteiroNotaCampoDinamicoDTO")
public class RoteiroNotaCampoDinamicoDTO {

	private String idCampoDinamico;
	private String nomeCampoDinamico;

	public RoteiroNotaCampoDinamicoDTO(Long idCampoDinamico, String nomeCampoDinamico) {
		this.idCampoDinamico = String.valueOf(idCampoDinamico);
		this.nomeCampoDinamico = nomeCampoDinamico;
	}

}
