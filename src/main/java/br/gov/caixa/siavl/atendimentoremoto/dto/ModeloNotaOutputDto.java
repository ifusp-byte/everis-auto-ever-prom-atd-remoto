package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ModeloNotaOutputDto")
public class ModeloNotaOutputDto {

	@Valid
	@XmlElement(name = "numeroModeloNota")
	private String numeroModeloNota;

	@Valid
	@XmlElement(name = "numeroAcaoProduto")
	private String numeroAcaoProduto;

	@Valid
	@XmlElement(name = "descricaoAcaoProduto")
	private String descricaoAcaoProduto;
	
	@Valid
	@XmlElement(name = "numeroTipoNota")
	private String numeroTipoNota;	
	
	@Valid
	@XmlElement(name = "nomeTipoNota")
	private String nomeTipoNota;

	@Valid
	@JsonIgnore
	@XmlElement(name = "dataEscolhaFavorito")
	private Date dataEscolhaFavorito;

}
