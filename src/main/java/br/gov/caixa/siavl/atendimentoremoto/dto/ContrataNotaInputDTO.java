package br.gov.caixa.siavl.atendimentoremoto.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
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
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ContrataNotaDTO")
public class ContrataNotaInputDTO {

	@Valid
	@XmlElement(name = "numeroNota")
	private Object numeroNota;

	@Valid
	@XmlElement(name = "cpf")
	private Object cpf;

	@Valid
	@XmlElement(name = "cnpj")
	private Object cnpj;

	@Valid
	@XmlElement(name = "valor")
	private Object valor;

	@Valid
	@XmlElement(name = "tipoDocumento")
	private Object tipoDocumento;

	@Hidden
	@JsonIgnore
	private String token;

	@Hidden
	@JsonIgnore
	private String numeroProtocolo;

	@Hidden
	@JsonIgnore
	private String tipoPessoa;

	@Hidden
	@JsonIgnore
	private String numeroSituacaoNota;

	@Hidden
	@JsonIgnore
	private String descricaoSituacaoNota;

	@Hidden
	@JsonIgnore
	private String origemNota;

}
