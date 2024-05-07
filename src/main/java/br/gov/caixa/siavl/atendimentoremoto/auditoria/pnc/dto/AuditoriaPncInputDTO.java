package br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaPncInputDTO {

	private String descricaoTransacao;
	private String ipTerminalUsuario;
	private String nomeMfe;
	private Long numeroUnidadeLotacaoUsuario;
	private String tipoDocumento;
	private Long numeroIdentificacaoCliente;
	private String ambienteAplicacao;
	private Long numeroUnidadeContaCliente;
	private Long numeroOperacaoProduto;
	private Long numeroConta;

}
