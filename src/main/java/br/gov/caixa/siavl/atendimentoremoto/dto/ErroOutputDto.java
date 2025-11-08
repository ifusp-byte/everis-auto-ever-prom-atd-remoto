package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.List;

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
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ErroOutputDto")
public class ErroOutputDto {
	
	@XmlElement(name = "codErro")
	private int codErro;
	
	@XmlElement(name = "descricao")
	private String descricao;
	
	@XmlElement(name = "violacoes")
	private List<ViolacaoDto> violacoes;

}
