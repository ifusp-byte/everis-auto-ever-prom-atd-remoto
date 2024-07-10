package br.gov.caixa.siavl.atendimentoremoto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "DocumentoNotaNegociacao")
@Table(name = "AVLTB034_DCMNTO_NOTA_NGCCO", schema = "AVL")
public class DocumentoNotaNegociacao {

	@Column(name = "IC_TIPO_PESSOA", columnDefinition = "CHAR(2)")
	private String tipoPessoa;

	@Id
	@Column(name = "NU_NOTA_NEGOCIACAO")
	private Long numeroNota;

	@Column(name = "NU_CPF_CNPJ_CLIENTE")
	private Long cpfCnpjCliente;

	@Column(name = "NU_TIPO_DOCUMENTO_CLIENTE")
	private Long tipoDocumentoCliente;

	@Column(name = "TS_INCLUSAO_DOCUMENTO")
	private Date inclusaoDocumento;

	@PrePersist
	public void prePersist() {
		if (inclusaoDocumento == null) {
			inclusaoDocumento = new Date();
		}
	}

}
