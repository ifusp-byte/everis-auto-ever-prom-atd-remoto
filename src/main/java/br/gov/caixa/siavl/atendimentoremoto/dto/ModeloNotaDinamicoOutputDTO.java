package br.gov.caixa.siavl.atendimentoremoto.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.validation.Valid;
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
@XmlRootElement(name = "ModeloNotaDinamicoOutputDTO")
public class ModeloNotaDinamicoOutputDTO {

	@Valid
	private ModeloNotaDinamicoMenuNotaDinamicoNotaProdutoOutputDTO menuNotaProduto;
	
	@Valid
	private ModeloNotaDinamicoMenuNotaNumeroOutputDTO menuNotaNumero;
	
	@Valid
	@JsonRawValue
	private String menuNotaDinamico;
	
	@Valid
	private String roteiroFechamento;

}
