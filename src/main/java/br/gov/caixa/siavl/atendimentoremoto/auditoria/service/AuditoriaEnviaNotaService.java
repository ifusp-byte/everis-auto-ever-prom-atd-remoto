package br.gov.caixa.siavl.atendimentoremoto.auditoria.service;

@SuppressWarnings({ "squid:S107" })
public interface AuditoriaEnviaNotaService {

	void auditar(String dataRegistroNota, String token, String cpfCnpj, String matriculaAtendente,
			String statusRetornoSicli, String numeroProtocolo, String numeroContaAtendimento, String numeroNota,
			String versaoSistema, String produto, String cpfSocio);

}
