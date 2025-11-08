package br.gov.caixa.siavl.atendimentoremoto.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
@Builder(toBuilder = true)
@Entity(name = "ComentarioAtendimento")
@Table(name = "AVLTB006_COMENTARIO_ATNTO", schema = "AVL")
public class ComentarioAtendimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NU_COMENTARIO_ATENDIMENTO")
	@SequenceGenerator(name = "AVLSQ005_NU_CMTRO_ATENDIMENTO", sequenceName = "AVLSQ005_NU_CMTRO_ATENDIMENTO", allocationSize = 1, schema = "AVL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVLSQ005_NU_CMTRO_ATENDIMENTO")
	private Long numeroComentario;

	@Column(name = "DE_COMENTARIO_ATENDIMENTO")
	private String descricaoComentario;

	@Column(name = "DT_COMENTARIO_ATENDIMENTO")
	private Date dataComentario;

	@Column(name = "NU_PROTOCOLO_ATNTO_CLNTE")
	private Long numeroProtocolo;

	@Column(name = "NU_MATRICULA_ATENDENTE")
	private Long matriculaAtendente;
}
