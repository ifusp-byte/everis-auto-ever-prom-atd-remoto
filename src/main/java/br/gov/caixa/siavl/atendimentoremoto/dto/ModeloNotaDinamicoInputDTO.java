package br.gov.caixa.siavl.atendimentoremoto.dto;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
@XmlRootElement(name = "ModeloNotaDinamicoInputDTO")
public class ModeloNotaDinamicoInputDTO {

	@Valid
	private String cpfCnpj;
	
	@Valid
	private String protocolo;
	
	@Valid
	private String numeroAcaoProduto;

}
