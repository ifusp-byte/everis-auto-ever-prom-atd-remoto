package br.gov.caixa.siavl.atendimentoremoto.util.conta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.mccue.guava.primitives.Ints;

@SuppressWarnings("all")
public enum ContaSID01PJEnum {

	CONTA_CORRENTE_PESSOA_JURIDICA(1292, "Conta Corrente Pessoa Jurídica"),
	CONTA_CORRENTE_PJ_ENCERRADA_SALDO(1368, "Conta Corrente PJ Encerrada com Saldo"),
	POUPANCA_PJ_MENSAL_TRIBUTARIO_IRPF_COND_EDILICO(1388,
			"Poupança PJ com Remun. Mensal e Trib. de IRPF Condomínio Edilício/Determ Jud."),
	POUPANCA_PJ_PUBLICA(146, "Poupança PJ Pública"),
	POUPANCA_PJ_PUBLICA_SEM_FINS_LUCRATIVOS_ENCERRADA_SALDO(1366,
			"Poupança PJ Pública Sem Fins Lucrativos encerrada com saldo encerrada com saldo"),
	POUPANCA_PJ_SEM_FINS_LUCRATIVOS_ENCERRADA_SALDO(1389, "Poupança PJ Sem Fins Lucrativos encerrada com saldo"),
	POUPANCA_PESSOA_JURIDICA_CAIXA(3702, "Poupança Pessoa Jurídica Caixa"),
	POUPANCA_PJ_ENCERRADA_SALDO(1365, "Poupança PJ Encerrada com Saldo"),
	CONTA_PRESTACAO_CONTA_CORRESPONDENTE_CAIXA(4995,
			"Conta para Prestação de Conta Correspondente Caixa Aqui e a Caixa"),
	CHEQUE_ADMINISTRATIVO_PRONTO_PAGAMENTO(20, "Cheque Administrativo Pronto Pagamento"),
	CHEQUE_ORDEM_PAGAMENTO(93, "Cheque Ordem de Pagamento");

	private final int codigo;
	private final String descricao;

	ContaSID01PJEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static int[] codigos() {
		List<Integer> codigos = new ArrayList<>();
		Arrays.stream(ContaSID01PJEnum.values())
				.forEach(c -> codigos.add(Integer.parseInt(String.valueOf(c.getCodigo()))));
		return Ints.toArray(codigos);
	}

}
