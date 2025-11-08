package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;
import java.util.Date;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@SuppressWarnings("all")
public class DocumentoNotaNegociacaoId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoPessoa;
	private Long numeroNota;
	private Long cpfCnpjCliente;
	private Long tipoDocumentoCliente;
	private Date inclusaoDocumento;
	public DocumentoNotaNegociacaoId(String tipoPessoa, Long numeroNota, Long cpfCnpjCliente, Long tipoDocumentoCliente,
			Date inclusaoDocumento) {
		this.tipoPessoa = tipoPessoa;
		this.numeroNota = numeroNota;
		this.cpfCnpjCliente = cpfCnpjCliente;
		this.tipoDocumentoCliente = tipoDocumentoCliente;
		this.inclusaoDocumento = inclusaoDocumento;
	}
	
	

}
