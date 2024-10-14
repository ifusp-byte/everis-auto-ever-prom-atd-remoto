package br.gov.caixa.siavl.atendimentoremoto.dto;

import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.StringToJson;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Objects;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

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
public class NotasByProtocoloOutputDTO {

	@Valid
	@XmlElement(name = "numeroNota")
	private String numeroNota;

	@Valid
	@XmlElement(name = "nomeCliente")
	private String nomeCliente;

	@Valid
	@XmlElement(name = "cpf")
	private String cpf;

	@Valid
	@XmlElement(name = "cnpj")
	private String cnpj;

	@Valid
	@XmlElement(name = "produto")
	private String produto;

	@Valid
	@XmlElement(name = "valor")
	private String valor;

	@Valid
	@XmlElement(name = "situacaoNota")
	private String situacaoNota;

	public NotasByProtocoloOutputDTO(String numeroNota, String nomeCliente, String cpf, String cnpj, String produto,
			String situacaoNota, Clob relatorioNota) {
		this.numeroNota = numeroNota;
		this.nomeCliente = nomeCliente;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.produto = produto;
		this.situacaoNota = situacaoNota;
		this.valor = valorMetaByRelatorioNota(relatorioNota);
	}

	public String valorMetaByRelatorioNota(Clob relatorioNota) {

		int tamanho;
		String body = null;
		String valorMeta = StringUtils.EMPTY;

		if (relatorioNota != null) {

			try {
				tamanho = Integer.parseInt(String.valueOf(relatorioNota.length()));
				body = String.valueOf(relatorioNota.getSubString(1, tamanho));
				valorMeta = Objects.requireNonNull(StringToJson(body).path("relatorioNota").path("Valor (meta)"))
						.asText();
			} catch (NumberFormatException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

		}

		return valorMeta;

	}

}
