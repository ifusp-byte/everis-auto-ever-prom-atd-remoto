package br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
@XmlRootElement(name = "AuditoriaPncInputDTO")
public class AuditoriaPncInputDTO {

	@XmlElement(name = "descricaoTransacao")
	private String descricaoTransacao;

	@XmlElement(name = "ipTerminalUsuario")
	private String ipTerminalUsuario;

	@XmlElement(name = "nomeMfe")
	private String nomeMfe;

	@XmlElement(name = "numeroUnidadeLotacaoUsuario")
	private int numeroUnidadeLotacaoUsuario;

}
