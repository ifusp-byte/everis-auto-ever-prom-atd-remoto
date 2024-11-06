package br.gov.caixa.siavl.atendimentoremoto.auditoria.dto;

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
public class AuditoriaEnvioNotaAnexoDTO {

	@Valid
	private String nomeAnexo;

	@Valid
	private String categoriaAnexo;

}
