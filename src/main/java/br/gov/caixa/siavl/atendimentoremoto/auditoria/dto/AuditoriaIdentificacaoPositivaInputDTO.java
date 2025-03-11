package br.gov.caixa.siavl.atendimentoremoto.auditoria.dto;

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
@XmlRootElement(name = "AuditoriaIdentificacaoPositivaInputDTO")
public class AuditoriaIdentificacaoPositivaInputDTO {

	@Valid
	@XmlElement(name = "cpfCnpj")
	private String cpfCnpj;

	@Valid
	@XmlElement(name = "versaoSistemaAgenciaVirtual")
	private String versaoSistemaAgenciaVirtual;

	@Valid
	@XmlElement(name = "statusCreated")
	private String statusCreated;
	
	@Valid
	@XmlElement(name = "numeroProtocolo")
	private String numeroProtocolo;
	
	@Valid
	@XmlElement(name = "cpfSocio")
	private String cpfSocio;
}
