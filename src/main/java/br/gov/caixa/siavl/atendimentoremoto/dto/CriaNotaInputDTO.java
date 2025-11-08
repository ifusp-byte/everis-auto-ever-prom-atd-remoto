package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.List;

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
@XmlRootElement(name = "CriaNotaDTO")
public class CriaNotaInputDTO {

	@Valid
	@XmlElement(name = "idModeloNota")
	private Object idModeloNota;

	@Valid
	@XmlElement(name = "idProduto")
	private Object idProduto;

	@Valid
	@XmlElement(name = "tipoDocumento")
	private Object tipoDocumento;

	@Valid
	@XmlElement(name = "cpfCnpj")
	private Object cpfCnpj;

	@Valid
	@XmlElement(name = "nomeCliente")
	private Object nomeCliente;

	@Valid
	@XmlElement(name = "cpfSocio")
	private Object cpfSocio;

	@Valid
	@XmlElement(name = "nomeSocio")
	private Object nomeSocio;

	@Valid
	@XmlElement(name = "numeroConta")
	private Object numeroConta;

	@Valid
	@XmlElement(name = "quantidadeMeta")
	private Object quantidadeMeta;

	@Valid
	@XmlElement(name = "valorMeta")
	private Object valorMeta;

	@Valid
	@XmlElement(name = "campos")
	private List<Object> campos;

	@Hidden
	private String token;

	@Hidden
	private String numeroProtocolo;

	@Hidden
	private String numeroNota;

	@Hidden
	private RegistraNotaDto registraNotaDto;

}
