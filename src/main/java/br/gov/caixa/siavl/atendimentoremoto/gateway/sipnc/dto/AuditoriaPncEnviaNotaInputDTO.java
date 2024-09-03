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
@XmlRootElement(name = "AuditoriaPncEnviaNotaInputDTO")
public class AuditoriaPncEnviaNotaInputDTO {
	
	@Valid
	private String situacaoNota;	
	
	@Valid
	private String numeroProtocolo;
	
	@Valid
	private String numeroNota;
	
	@Valid
	private String versaoSistema;
	
	@Valid
	private String dataHoraTransacao; 
	
	@Valid
	private String possuiAnexo;
	
	@Valid 
	private String nomeAnexo;
	
	@Valid
	private String categoriaAnexo; 

}
