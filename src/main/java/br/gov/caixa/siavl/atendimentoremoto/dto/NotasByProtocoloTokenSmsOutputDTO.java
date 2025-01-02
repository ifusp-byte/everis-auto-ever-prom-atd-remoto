package br.gov.caixa.siavl.atendimentoremoto.dto;

import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.formataCnpjFront;
import static br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils.formataCpfFront;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.StringToJson;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Objects;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRawValue;
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
@SuppressWarnings("all")
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "NotasByProtocoloOutputDTO")
public class NotasByProtocoloTokenSmsOutputDTO {

	@Valid
	@XmlElement(name = "numeroNota")
	private String numeroNota;

	@Valid
	@XmlElement(name = "nomeCliente")
	private String nomeCliente;

	@Valid
	@XmlElement(name = "cpfCnpj")
	private String cpfCnpj;

	@Valid
	@XmlElement(name = "produto")
	private String produto;

	@Valid
	@XmlElement(name = "valorMeta")
	private String valorMeta;

	@Valid
	@XmlElement(name = "situacaoNota")
	private String situacaoNota;

	@Valid
	@XmlElement(name = "numeroModeloNota")
	private String numeroModeloNota;

	@Valid
	@XmlElement(name = "contaAtendimento")
	private String contaAtendimento;

	@Valid
	@XmlElement(name = "razaoSocial")
	private String razaoSocial;

	@Valid
	@XmlElement(name = "cnpj")
	private String cnpj;

	@Valid
	@XmlElement(name = "nomeSocio")
	private String nomeSocio;

	@Valid
	@XmlElement(name = "cpfSocio")
	private String cpfSocio;
	
	@Valid
	@XmlElement(name = "relatorioNota")
	private Object relatorioNota; 

	@Valid
	@XmlElement(name = "tipoAssinatura")
	private String tipoAssinatura;
	
	
	public NotasByProtocoloTokenSmsOutputDTO(Long numeroNota, String nomeCliente, Long cpf, Long cnpj, String produto,
			String situacaoNota, Object relatorioNota, Long numeroModeloNota) {
		this.numeroNota = String.valueOf(numeroNota);
		this.nomeCliente = nomeCliente;
		this.cpfCnpj = cnpj == null ? formataCpfFront(cpf) : formataCnpjFront(cnpj);
		this.produto = produto;
		this.situacaoNota = situacaoNota;
		this.numeroModeloNota = String.valueOf(numeroModeloNota);

		JsonNode relatorioNotaPath = relatorioNota(relatorioNota);

		this.valorMeta = relatorioNotaPath.path("relatorioNota").path("Valor (meta)").asText();
		this.contaAtendimento = relatorioNotaPath.path("contaAtendimento").asText();
		this.razaoSocial = relatorioNotaPath.path("razaoSocial").asText();
		this.cnpj = relatorioNotaPath.path("cnpj").asText();
		this.nomeSocio = relatorioNotaPath.path("nomeSocio").asText();
		this.cpfSocio = relatorioNotaPath.path("cpfSocio").asText();
		this.relatorioNota = relatorioNotaPath.path("relatorioNota");
		this.tipoAssinatura = "IP+Token";
	}

	public JsonNode relatorioNota(Object relatorioNota) {

		int tamanho;
		String relatorioNota1ToString = null;
		Clob relatorioNota1 = (Clob) relatorioNota;

		if (relatorioNota != null) {

			try {
				tamanho = Integer.parseInt(String.valueOf(relatorioNota1.length()));
				relatorioNota1ToString = String.valueOf(relatorioNota1.getSubString(1, tamanho));
			} catch (NumberFormatException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

		}
		return Objects.requireNonNull(StringToJson(relatorioNota1ToString));
	}
	
	public JsonNode logPlataforma(Object jsonLogPlataforma) {

		int tamanho;
		String logPlataformaToString = null;
		Clob logPlataforma = (Clob) jsonLogPlataforma;

		if (jsonLogPlataforma != null) {

			try {
				tamanho = Integer.parseInt(String.valueOf(logPlataforma.length()));
				logPlataformaToString = String.valueOf(logPlataforma.getSubString(1, tamanho));
			} catch (NumberFormatException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

		}
		return Objects.requireNonNull(StringToJson(logPlataformaToString));
	}

}
