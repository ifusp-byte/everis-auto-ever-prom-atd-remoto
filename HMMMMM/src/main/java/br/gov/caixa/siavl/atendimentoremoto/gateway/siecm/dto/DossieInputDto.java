package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto;

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
@XmlRootElement(name = "DossieInputDto")
public class DossieInputDto {

	@Valid
	@XmlElement(name = "idDossie")
	private String idDossie;

	@Valid
	@XmlElement(name = "dadosRequisicao")
	private DossieDadosRequisicaoInputDto dadosRequisicao;

}
