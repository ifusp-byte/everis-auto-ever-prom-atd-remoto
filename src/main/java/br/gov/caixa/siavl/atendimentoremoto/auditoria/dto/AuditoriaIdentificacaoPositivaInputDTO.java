package br.gov.caixa.siavl.atendimentoremoto.auditoria.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

	@XmlElement(name = "cpfCnpj")
	private String cpfCnpj;

	@XmlElement(name = "versaoSistemaAgenciaVirtual")
	private String versaoSistemaAgenciaVirtual;

	@XmlElement(name = "statusCreated")
	private String statusCreated;
	
	@XmlElement(name = "numeroProtocolo")
	private String numeroProtocolo;
	
	@XmlElement(name = "cpfSocio")
	private String cpfSocio;
}
