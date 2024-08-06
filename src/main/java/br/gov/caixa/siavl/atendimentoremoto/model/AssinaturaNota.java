package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;
import java.util.Date;
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
@Entity(name = "AssinaturaNota")
@Table(name = "AVLTB054_ASSINATURA_NOTA_NGCCO", schema = "AVL")
public class AssinaturaNota implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_NOTA_NEGOCIACAO")
	private Long numeroNota;

	@Column(name = "NU_CPF_CLIENTE_ASSINANTE_NOTA")
	private Long cpfClienteAssinante;

	@Column(name = "IC_TIPO_ASSINATURA_NOTA")
	private char tipoAssinatura;

	@Column(name = "IC_ORIGEM_ASSINATURA_NOTA")
	private char origemAssinatura;

	@Column(name = "TS_ASSINATURA_NOTA_NEGOCIACAO")
	private Date dtAssinatura;

}
