package br.gov.caixa.siavl.atendimentoremoto.auditoria.dto;

import br.gov.caixa.siavl.atendimentoremoto.sicli.dto.ContaAtendimentoOutputDTO;
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
public class AuditoriaRegistraNotaSicliDsLogPlataformaDTO {
	
	ContaAtendimentoOutputDTO contaAtendimento;
	private String cpfCnpj;
	private String matriculaAtendente;
	private String dataChamadaSicli;
	private String versaoSistema;
	private String ipUsuario;
	private String tipoPessoa;

}
