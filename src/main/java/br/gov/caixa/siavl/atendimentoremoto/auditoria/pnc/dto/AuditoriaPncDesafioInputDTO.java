package br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto;

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
	
	private String idDesafio;
	private String matriculaAtendente;
	private String statusValidacao; 
	private String tipoPessoa; 
	private String transacaoSistema;
	
}

