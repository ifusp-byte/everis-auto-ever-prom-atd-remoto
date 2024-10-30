package br.gov.caixa.siavl.atendimentoremoto.auditoria.dto;

import java.util.List;

import javax.validation.Valid;

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
public class AuditoriaEnvioNotaDsLogPlataformaDTO {

	@Valid
	private String cpfCnpj;

	@Valid
	private String cpfSocio;

	@Valid
	private String matriculaAtendente;

	@Valid
	private String statusRetornoSicli;

	@Valid
	private String numeroProtocolo;

	@Valid
	private String numeroContaAtendimento;

	@Valid
	private String numeroNota;

	@Valid
	private String dataRegistroNota;

	@Valid
	private String versaoSistema;

	@Valid
	private String ipUsuario;

	@Valid
	private String tipoPessoa;

	@Valid
	private Long transacaoSistema;

	@Valid
	private String produto;
	
	@Valid
	private String tipoAssinatura;

	@Valid
	private String possuiAnexo;

	private List<AuditoriaEnvioNotaDsLogPlataformaAnexoDTO> anexos;

}
