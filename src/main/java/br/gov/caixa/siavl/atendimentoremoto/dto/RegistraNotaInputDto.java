package br.gov.caixa.siavl.atendimentoremoto.dto;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
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
	
	@Valid
	private String cpfCnpj;
	
	@Valid
	private String nomeCliente;
	
	@Valid
	private String numeroProtocolo; 
	
	@Valid
	private String contaAtendimento;
	
	@Valid
	private String quantidadeMeta;
	
	@Valid
	private String valorMeta;
	
	@Valid
	private String versaoSistema;
	
	@Valid
	private String produto;
	
	@Valid
	private String acaoProduto;
	
	@Valid
	private JsonNode relatorioNota; 
	
	@Valid
	private String numeroNota; 
	
	@Valid
	private String razaoSocial;
	
	@Valid
	private String cnpj;
	
	@Valid
	private String nomeSocio;
	
	@Valid
	private String cpfSocio; 

}
