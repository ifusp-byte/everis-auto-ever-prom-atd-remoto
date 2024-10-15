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

	public NotasByProtocoloOutputDTO(Long numeroNota, String nomeCliente, Long cpf, Long cnpj, String produto,
			String situacaoNota, Object relatorioNota) {
		this.numeroNota = String.valueOf(numeroNota);
		this.nomeCliente = nomeCliente;
		this.cpfCnpj = cnpj == null ? formataCpfFront(cpf) : formataCnpjFront(cnpj);
		this.produto = produto;
		this.situacaoNota = situacaoNota;
		this.valorMeta = valorMetaByRelatorioNota(relatorioNota);
	}

	public String valorMetaByRelatorioNota(Object relatorioNota) {

		int tamanho;
		String body = null;
		String valorMeta = StringUtils.EMPTY;
		Clob relatorioNota1 = (Clob) relatorioNota;

		if (relatorioNota != null) {

			try {
				tamanho = Integer.parseInt(String.valueOf(relatorioNota1.length()));
				body = String.valueOf(relatorioNota1.getSubString(1, tamanho));
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
