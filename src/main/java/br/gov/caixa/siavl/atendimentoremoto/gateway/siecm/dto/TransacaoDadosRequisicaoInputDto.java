package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement(name = "DossieDadosRequisicaoInputDto")
public class TransacaoDadosRequisicaoInputDto {

	@Valid
	@XmlElement(name = "localArmazenamento")
	private String localArmazenamento;

	@Valid
	@XmlElement(name = "ipUsuarioFinal")
	private String ipUsuarioFinal;

}
