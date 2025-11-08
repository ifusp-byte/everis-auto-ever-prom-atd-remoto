package br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
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
	private String statusIdentificacaoPositiva;

	@Valid
	private String versaoSistema;

	@Valid
	private String dataHoraTransacao;

	@Valid
	private String numeroProtocolo;

	private String cpfSocio;
}
