package br.gov.caixa.siavl.atendimentoremoto.gateway.siipc.dto;

import java.util.List;

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
@XmlRootElement(name = "RespondeDesafioInputDTO")
public class RespondeDesafioInputDTO {

	@XmlElement(name = "listaResposta")
	private List<?> listaResposta;

	@XmlElement(name = "protocolo")
	private String protocolo;

	@XmlElement(name = "cpfSocio")
	private String cpfSocio;
}
