package br.gov.caixa.siavl.atendimentoremoto.auditoria.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuditoriaIdentificacaoPositivaDsLogPlataformaDTO {

	@Valid
	private String cpfCnpj;

	@Valid
	private String cpfSocio;

	@Valid
	private String matriculaAtendente;

	@Valid
	private String statusIdentificacaoPositiva;

	@Valid
	private String dataCriacao;

	@Valid
	private String numeroProtocolo;

	@Valid
	private String versaoSistema;

	@Valid
	private String ipUsuario;

	@Valid
	private String tipoPessoa;

}
