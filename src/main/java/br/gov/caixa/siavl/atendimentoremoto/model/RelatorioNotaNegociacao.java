package br.gov.caixa.siavl.atendimentoremoto.model;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RelatorioNotaNegociacao")
@Table(name = "AVLTB065_RLTRO_NOTA_NEGOCIACAO", schema = "AVL")
public class RelatorioNotaNegociacao {
	
	@Id
	@Column(name = "NU_RELATORIO_NOTA_NEGOCIACAO")
	@SequenceGenerator(name = "AVLSQ065_RLTRO_NOTA_NEGOCIACAO", sequenceName = "AVLSQ065_RLTRO_NOTA_NEGOCIACAO", allocationSize = 1, schema = "AVL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVLSQ065_RLTRO_NOTA_NEGOCIACAO")
	private Long numeroRelatorioNota;
	

	@Column(name = "NU_EQUIPE_ATENDIMENTO")
	private Long numeroEquipe;
	
	@Column(name = "DE_RELATORIO_NOTA_NEGOCIACAO")
	private Clob relatorioNota;

}
