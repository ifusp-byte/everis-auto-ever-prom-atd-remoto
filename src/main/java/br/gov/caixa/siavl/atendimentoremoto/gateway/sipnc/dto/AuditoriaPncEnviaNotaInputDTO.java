package br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto;

import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaEnvioNotaAnexoDTO;
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
@XmlRootElement(name = "AuditoriaPncEnviaNotaInputDTO")
public class AuditoriaPncEnviaNotaInputDTO {

	@Valid
	private String situacaoNota;

	@Valid
	private String tipoAssinatura;

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

	private List<AuditoriaEnvioNotaAnexoDTO> anexos;

	@Valid
	private String tipoNota;

	@Valid
	private String cpfSocio;

	@Valid
	private String nomeSocio;

}
