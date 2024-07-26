package br.gov.caixa.siavl.atendimentoremoto.auditoria.service;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaIdentificacaoPositivaInputDTO;

@SuppressWarnings("all")
public interface AuditoriaIdentificacaoPositivaService {
	Boolean auditar(String token,
			AuditoriaIdentificacaoPositivaInputDTO auditoriaIdentificacaoPositivaInputDTO);
}
