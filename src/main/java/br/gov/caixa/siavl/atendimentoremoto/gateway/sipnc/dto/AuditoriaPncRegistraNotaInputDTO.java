package br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AuditoriaPncRegistraNotaInputDTO")
public class AuditoriaPncRegistraNotaInputDTO {
	
	@Valid
	private String cpfCnpj;
	
	@Valid
	private String statusRetornoSicli;
	
	@Valid
	private String numeroProtocolo;
	
	@Valid
	private String numeroNota;
	
	@Valid
	private String dataRegistroNota;
    
    @Valid
	private String versaoSistema;
	
	@Valid
	private String produto;
	
	@Valid
	private String cpfSocio;
	
	@Valid
	private String nomeSocio; 

}
