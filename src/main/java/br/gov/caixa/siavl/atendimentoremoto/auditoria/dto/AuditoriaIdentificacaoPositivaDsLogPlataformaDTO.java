package br.gov.caixa.siavl.atendimentoremoto.auditoria.dto;

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

	private String cpfCnpj;
	
	private String cpfSocio;

	private String matriculaAtendente;

	private String statusIdentificacaoPositiva;

	private String dataCriacao;

	private String numeroProtocolo;

	private String versaoSistema;

	private String ipUsuario;

	private String tipoPessoa;

}
