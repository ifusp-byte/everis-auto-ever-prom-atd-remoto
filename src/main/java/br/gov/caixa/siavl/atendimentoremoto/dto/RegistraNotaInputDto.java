package br.gov.caixa.siavl.atendimentoremoto.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.JsonNode;

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
@XmlRootElement(name = "RegistraNotaInputDto")
public class RegistraNotaInputDto {
	
	private String cpfCnpj;
	private String nomeCliente;
	private String numeroProtocolo; 
	private String contaAtendimento;
	private String quantidadeMeta;
	private String valorMeta;
	private String valorCampoNota;
	private JsonNode relatorioNota; 

}
