package br.gov.caixa.siavl.atendimentoremoto.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRawValue;

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

	private ModeloNotaDinamicoMenuNotaDinamicoNotaProdutoOutputDTO menuNotaProduto;
	private ModeloNotaDinamicoMenuNotaNumeroOutputDTO menuNotaNumero;
	
	@JsonRawValue
	private String menuNotaDinamico;
	
	private String roteiroFechamento;

}
