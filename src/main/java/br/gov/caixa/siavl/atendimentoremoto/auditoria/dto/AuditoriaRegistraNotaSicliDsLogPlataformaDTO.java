package br.gov.caixa.siavl.atendimentoremoto.auditoria.dto;

import javax.validation.Valid;

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
	
	@Valid
	ContaAtendimentoOutputDTO contaAtendimento;
	
	@Valid
	private String cpfCnpj;
	
	@Valid
	private String matriculaAtendente;
	
	@Valid
	private String dataChamadaSicli;
	
	@Valid
	private String versaoSistema;
	
	@Valid
	private String ipUsuario;
	
	@Valid
	private String tipoPessoa;

}
