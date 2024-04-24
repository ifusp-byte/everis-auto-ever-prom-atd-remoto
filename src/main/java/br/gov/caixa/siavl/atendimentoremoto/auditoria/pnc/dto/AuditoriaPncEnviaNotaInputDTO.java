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
@XmlRootElement(name = "AuditoriaPncRegistraNotaInputDTO")
public class AuditoriaPncEnviaNotaInputDTO {
	
	private String cpfCnpj;
	private String matriculaAtendente;
	private String statusRetornoSicli;
	private String statusRetornoIdPositiva;
	private String statusContratacao;
	private String dataEnvioNota;;
	private String numeroProtocolo;
	private String numeroContaAtendimento;
	private String numeroNota;
    private String transacaoSistema; 
	private String versaoSistema;
	private String ipUsuario;
	private String tipoPessoa;
	private String produto;

}
