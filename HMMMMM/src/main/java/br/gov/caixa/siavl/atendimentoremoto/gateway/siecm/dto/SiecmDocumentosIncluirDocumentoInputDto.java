package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
@XmlRootElement(name = "SiecmDocumentosIncluirDocumentoInputDto")
public class SiecmDocumentosIncluirDocumentoInputDto {

	private SiecmDocumentosIncluirDocumentoAtributosInputDto atributos;
	private String binario;

}
