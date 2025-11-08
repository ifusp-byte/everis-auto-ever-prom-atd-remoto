package br.gov.caixa.siavl.atendimentoremoto.doc;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ModeloDTODoc {

	@Schema(description = "Identificador único do modelo de nota de negociação.", example = "9425")
	Long getIdModelo();

	@Schema(description = "Identificador único do produto.", example = "743")
	Long getIdProduto();

	@Schema(description = "Descrição do produto.", example = "CAP GANHADOR PM")
	String getDescProduto();

	@Schema(description = "Descrição do tipo de nota.", example = "TITULO DE CAPITALIZACAO CAP GANHADOR PAGAMENTO MENSAL")
	String getTipoNota();

}
