package br.gov.caixa.siavl.atendimentoremoto.siecm.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
@XmlRootElement(name = "SiecmDocumentosIncluirDocumentoAtributosCamposInputDto")
public class SiecmDocumentosIncluirDocumentoAtributosCamposInputDto {

	private String classe;
	private String gerarThumbnail;
	private String tipo;
	private String mimeType;
	private String nome;
	private String campo;

}
