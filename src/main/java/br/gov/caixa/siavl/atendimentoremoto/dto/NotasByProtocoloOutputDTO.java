package br.gov.caixa.siavl.atendimentoremoto.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
@XmlRootElement(name = "NotasByProtocoloOutputDTO")
public class NotasByProtocoloOutputDTO {
	
	private String numeroNota; 
	
	private String nomeCliente; 
	
	private String cpfCnpj; 
	
    private String produto; 
    
    private String valor; 
    
    private String situacaoNota;
    
}
