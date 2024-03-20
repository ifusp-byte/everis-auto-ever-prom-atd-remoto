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
@XmlRootElement(name = "AuditoriaPncProtocoloInputDTO")
public class AuditoriaPncProtocoloInputDTO {

	private String cpfCnpj;
	private String canal;
	private String numeroProtocolo;
	private String dataInicioAtendimento;
	private String matriculaAtendente;
	private String transacaoSistema;

}
