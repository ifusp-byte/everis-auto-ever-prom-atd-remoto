package br.gov.caixa.siavl.atendimentoremoto.auditoria.service;

import br.gov.caixa.siavl.atendimentoremoto.sicli.dto.ContaAtendimentoOutputDTO;

public interface AuditoriaRegistraNotaSicliService {
	
	void auditar(ContaAtendimentoOutputDTO contaAtendimento, String token, String cpfCnpj);

}
