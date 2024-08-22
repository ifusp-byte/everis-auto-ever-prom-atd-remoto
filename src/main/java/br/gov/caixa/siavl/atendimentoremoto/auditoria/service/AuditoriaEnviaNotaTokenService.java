package br.gov.caixa.siavl.atendimentoremoto.auditoria.service;

@SuppressWarnings("all")
public interface AuditoriaEnviaNotaTokenService {

	void auditar(String dataRegistroNota, String token, String cpfCnpj, String matriculaAtendente,
			String statusRetornoSicli, String numeroProtocolo, String numeroContaAtendimento, String numeroNota,
			String versaoSistema, String produto, String cpfSocio, String assinaturaToken, String tokenValido, String tokenValidoTelefone);

}
