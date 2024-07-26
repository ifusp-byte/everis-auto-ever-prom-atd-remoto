package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.dto.SociosOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GeraProtocoloOutputDTO")
public class GeraProtocoloOutputDTO {

	@Valid
	@XmlElement(name = "status")
	private Boolean status;

	@Valid
	@XmlElement(name = "numeroProtocolo")
	private String numeroProtocolo;
	
	private List<SociosOutputDTO> socios;
	
	@Valid
	@XmlElement(name = "razaoSocial")
	private String razaoSocial; 
	
	@Valid
	@XmlElement(name = "statusSicli")
	private String statusSicli; 
	
	@Valid
	@XmlElement(name = "mensagemSicli")
	private String mensagemSicli; 

}
