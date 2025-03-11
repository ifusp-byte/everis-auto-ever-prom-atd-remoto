package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto;

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
@XmlRootElement(name = "SimtrDocumentoDto")
public class SimtrDocumentoDto {

	private String id;
	private String tipologia;
	private String acordeonMfe;
	private String nome;
	private String ativo;
	private String situacaoDocumento;
	private String dataHoraCaptura;
	private String mimeType;

}
