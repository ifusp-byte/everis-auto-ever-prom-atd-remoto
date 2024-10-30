package br.gov.caixa.siavl.atendimentoremoto.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.caixa.siavl.atendimentoremoto.model.TipoDocumentoCliente;
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
@XmlRootElement(name = "TipoDocumentoClienteOutputDTO")
public class TipoDocumentoClienteOutputDTO {

	private List<TipoDocumentoCliente> documentosOpcionais;

	private List<TipoDocumentoCliente> documentosObrigatorios;

}
