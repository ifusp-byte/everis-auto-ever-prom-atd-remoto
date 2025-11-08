package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
	@XmlElement(name = "tipoAutenticacao")
	private List<TipoContratacaoDTO> tipoAutenticacao;

	@Valid
	@JsonIgnore
	@XmlElement(name = "dataEscolhaFavorito")
	private Date dataEscolhaFavorito;

}
