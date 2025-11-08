package br.gov.caixa.siavl.atendimentoremoto.dto;

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
@XmlRootElement(name = "RegistraNotaDto")
public class RegistraNotaDto {

	private String cpfCnpj;

	private String nomeCliente;

	private String numeroProtocolo;

	private String contaAtendimento;

	private String quantidadeMeta;

	private String valorMeta;

	private String versaoSistema;

	private String produto;

	private String acaoProduto;

	private Object relatorioNota;

	private String numeroNota;

	private String razaoSocial;

	private String cnpj;

	private String nomeSocio;

	private String cpfSocio;

}
