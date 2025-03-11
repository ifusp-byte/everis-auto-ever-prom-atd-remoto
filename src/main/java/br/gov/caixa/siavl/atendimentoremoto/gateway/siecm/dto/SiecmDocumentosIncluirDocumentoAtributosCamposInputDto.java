package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.dto;

import java.util.List;

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
@XmlRootElement(name = "SiecmDocumentosIncluirDocumentoAtributosCamposInputDto")
public class SiecmDocumentosIncluirDocumentoAtributosCamposInputDto {

	private String classe;
	private boolean gerarThumbnail;
	private String tipo;
	private String mimeType;
	private String nome;
	private List<?> campo;

}
