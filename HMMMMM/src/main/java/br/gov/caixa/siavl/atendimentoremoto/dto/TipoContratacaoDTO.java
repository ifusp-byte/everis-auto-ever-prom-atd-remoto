package br.gov.caixa.siavl.atendimentoremoto.dto;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
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
@SuppressWarnings("all")
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TipoContratacaoDTO")
public class TipoContratacaoDTO {

	@Valid
	@XmlElement(name = "id")
	private Long id;

	@Valid
	@XmlElement(name = "descricao")
	private String descricao;

}
