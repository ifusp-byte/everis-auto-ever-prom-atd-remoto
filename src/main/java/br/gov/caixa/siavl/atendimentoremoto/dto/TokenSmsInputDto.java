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
@XmlRootElement(name = "TokenSmsInputDto")
public class TokenSmsInputDto {

	@Valid
	private String numeroProtocolo;

	@Valid
	private String numeroNota;

	@Valid
	private String identificacaoToken;
	
	@Valid
	private String assinaturaToken;

	@Valid
	private String tokenValido;

	@Valid
	private String tokenTelefone;

}
