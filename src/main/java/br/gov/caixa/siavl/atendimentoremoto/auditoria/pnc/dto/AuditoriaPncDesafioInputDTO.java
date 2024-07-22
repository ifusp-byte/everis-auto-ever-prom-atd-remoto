package br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto;

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
@XmlRootElement(name = "AuditoriaPncDesafioInputDTO")
public class AuditoriaPncDesafioInputDTO {
	
	@Valid
	private String idDesafio;
	
	@Valid
	private String matriculaAtendente;
	
	@Valid
	private String statusValidacao; 
	
	@Valid
	private String tipoPessoa; 
	
	@Valid
	private String versaoSistema;
	
	@Valid
	private String dataHoraTransacao; 
	
	@Valid
	private String numeroProtocolo;
	
	private String cpfSocio;
}

