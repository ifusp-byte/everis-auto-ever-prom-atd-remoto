package br.gov.caixa.siavl.atendimentoremoto.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;

import br.gov.caixa.siavl.atendimentoremoto.doc.ModeloDTODoc;
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
@XmlRootElement(name = "modeloComCamposType")
public class ModeloDTO implements ModeloDTODoc {
	
	private Long idModelo;
	
	private Long idProduto;
	
	private String descProduto;
	
	private String tipoNota;
	
	@Valid
	@JsonRawValue
	private String campos;

}
