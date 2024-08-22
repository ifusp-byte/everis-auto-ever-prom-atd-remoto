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
@XmlRootElement(name = "AuditoriaPncEnviaNotaTokenInputDTO")
public class AuditoriaPncEnviaNotaTokenInputDTO {
	
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
	private String assinaturaToken;

	@Valid
	private String tokenValido;

	@Valid
	private String tokenValidoTelefone;

}
