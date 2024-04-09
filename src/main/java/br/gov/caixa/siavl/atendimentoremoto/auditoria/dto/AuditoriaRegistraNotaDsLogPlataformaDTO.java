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
public class AuditoriaRegistraNotaDsLogPlataformaDTO {
	
	private String cpfCnpj;
	private String matriculaAtendente;
	private String statusRetornoSicli;
	private String numeroProtocolo;
	private String numeroContaAtendimento;
	private String numeroNota;
	private String dataRegistroNota;
	private String versaoSistema;
	private String ipUsuario;
	private String tipoPessoa;
	
}
