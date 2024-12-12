package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TipoNota")
@Table(name = "AVLTB070_TIPO_ATENDIMENTO", schema = "AVL")
public class TipoNota implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_TIPO_ATENDIMENTO")
	private Long numeroTipoNota;

	@Column(name = "NO_TIPO_ATENDIMENTO")
	private String nomeTipoNota;

}
