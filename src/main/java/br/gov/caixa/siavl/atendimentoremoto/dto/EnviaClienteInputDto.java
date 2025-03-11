package br.gov.caixa.siavl.atendimentoremoto.dto;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
@XmlRootElement(name = "EnviaClienteInputDto")
public class EnviaClienteInputDto {

	@Valid
	private String versaoSistema;

	@Valid
	private String cpfCnpj;

	@Valid
	private String numeroProtocolo;

	@Valid
	private String numeroConta;

	@Valid
	private String produto;

	@Valid
	private String razaoSocial;

	@Valid
	private String cnpj;

	@Valid
	private String nomeSocio;

	@Valid
	private String cpfSocio;

	@Valid
	private String assinaturaToken;

	@Valid
	private String tokenValido;

	@Valid
	private String tokenValidoTelefone;

	@Valid
	private String tipoAssinatura;

}
