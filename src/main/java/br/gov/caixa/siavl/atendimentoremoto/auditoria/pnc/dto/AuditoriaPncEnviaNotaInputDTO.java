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
@XmlRootElement(name = "AuditoriaPncRegistraNotaInputDTO")
public class AuditoriaPncEnviaNotaInputDTO {
	
	@Valid
	private String cpfCnpj;
	
	@Valid
	private String matriculaAtendente;
	
	@Valid
	private String statusRetornoSicli;
	
	@Valid
	private String statusRetornoIdPositiva;
	
	@Valid
	private String situacaoNota;
	
	@Valid
	private String dataEnvioNota;
	
	@Valid
	private String numeroProtocolo;
	
	@Valid
	private String numeroContaAtendimento;
	
	@Valid
	private String numeroNota;
	
	@Valid
	private String versaoSistema;
	
	@Valid
	private String ipUsuario;
	
	@Valid
	private String tipoPessoa;
	
	@Valid
	private String produto;
	
	@Valid
	private String dataHoraTransacao; 

}
