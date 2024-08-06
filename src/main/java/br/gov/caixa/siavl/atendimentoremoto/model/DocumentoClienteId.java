package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;
import java.util.Date;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DocumentoClienteId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date inclusaoDocumento;
	private String tipoPessoa;
	private Long cpfCnpjCliente;
	private Long tipoDocumentoCliente;

	public DocumentoClienteId(Date inclusaoDocumento, String tipoPessoa, Long cpfCnpjCliente,
			Long tipoDocumentoCliente) {
		this.inclusaoDocumento = inclusaoDocumento;
		this.tipoPessoa = tipoPessoa;
		this.cpfCnpjCliente = cpfCnpjCliente;
		this.tipoDocumentoCliente = tipoDocumentoCliente;
	}

}
