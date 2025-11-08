package br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto;

import jakarta.validation.Valid;
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

	@Valid
	private String descricaoEnvioTransacao;

	@Valid
	private String descricaoTransacao;

	@Valid
	private String ipTerminalUsuario;

	@Valid
	private String nomeMfe;

	@Valid
	private Long numeroUnidadeLotacaoUsuario;

	@Valid
	private String tipoDocumento;

	@Valid
	private Long numeroIdentificacaoCliente;

	@Valid
	private String ambienteAplicacao;

	@Valid
	private Long numeroUnidadeContaCliente;

	@Valid
	private Long numeroOperacaoProduto;

	@Valid
	private Long numeroConta;

}
