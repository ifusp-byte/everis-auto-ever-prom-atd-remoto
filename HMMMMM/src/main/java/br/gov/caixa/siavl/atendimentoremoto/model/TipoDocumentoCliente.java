package br.gov.caixa.siavl.atendimentoremoto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TipoDocumentoCliente")
@Table(name = "AVLTB039_TIPO_DOCUMENTO_CLNTE", schema = "AVL")
public class TipoDocumentoCliente {

	@Id
	@Column(name = "NU_TIPO_DOCUMENTO_CLIENTE")
	private Long numeroTipoDocumento;

	@Column(name = "NO_TIPO_DOCUMENTO_CLIENTE")
	private String nomeTipoDocumento;

	@Column(name = "CO_CLASSE_DOCUMENTO_GED")
	private String classeDocumentoGed;

}
