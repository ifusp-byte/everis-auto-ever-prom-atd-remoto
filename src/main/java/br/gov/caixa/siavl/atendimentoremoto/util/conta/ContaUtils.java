package br.gov.caixa.siavl.atendimentoremoto.util.conta;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SIART;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SID01;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SIDEC;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SIIFX;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.OITO_NUMBER;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PERSON_TYPE_PF;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PERSON_TYPE_PJ;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PONTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.QUATRO_NUMBER;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REPLACE_CONTA_1;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REPLACE_CONTA_2;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REPLACE_IDENTIFICACAO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.SETE_NUMBER;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TRACO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.UM_NUMBER;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ZERO_CHAR;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ZERO_NUMBER;

import java.text.ParseException;
import java.util.List;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.dto.ContasOutputDTO;

@Component
@SuppressWarnings("all")
public class ContaUtils {

	/**
	 * este trecho de código foi descontinnuado 
	 * @param dtInicio
	 * @param sgSistema
	 * @param nuUnidade
	 * @param nuProduto
	 * @param coIdentificacao
	 * @param contasAtendimento
	 * @param tipoPessoa
	 * @return
	 */
	/*
	public String formataContaTotalOld(String dtInicio, String sgSistema, Object nuUnidade, Object nuProduto,
			Object coIdentificacao) {

		String contaFormatada = null;

		if (CONTA_SIART.equalsIgnoreCase(sgSistema)) {
			String identificacao = String.valueOf(coIdentificacao).replace(PONTO, StringUtils.EMPTY).replace(TRACO,
					StringUtils.EMPTY);
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);
			identificacao = identificacao.replace(unidade, StringUtils.EMPTY);
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length()) + unidade;
			String formataProduto = REPLACE_CONTA_1.substring(produto.length()) + produto;
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length()) + identificacao;
			contaFormatada = formataUnidade + formataProduto + formatIdentificacao;

		} else if (CONTA_SIDEC.equalsIgnoreCase(sgSistema)) {
			String identificacao = String.valueOf(coIdentificacao).replace(PONTO, StringUtils.EMPTY).replace(TRACO,
					StringUtils.EMPTY);
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);
			String formatProdutoReplace = REPLACE_CONTA_2.substring(produto.length()) + produto;
			identificacao = identificacao.replace(unidade + formatProdutoReplace, StringUtils.EMPTY);
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length()) + identificacao;
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length()) + unidade;
			String formataProduto = REPLACE_CONTA_1.substring(produto.length()) + produto;
			contaFormatada = formataUnidade + formataProduto + formatIdentificacao;

		} else if (CONTA_SIIFX.equalsIgnoreCase(sgSistema)) {
			String dataInicio = String.valueOf(dtInicio).replace(PONTO, StringUtils.EMPTY).replace(TRACO,
					StringUtils.EMPTY);
			String identificacao = String.valueOf(coIdentificacao).replace(PONTO, StringUtils.EMPTY).replace(TRACO,
					StringUtils.EMPTY);
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);
			identificacao = identificacao.replace(dataInicio, StringUtils.EMPTY);
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length()) + identificacao;
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length()) + unidade;
			String formataProduto = REPLACE_CONTA_1.substring(produto.length()) + produto;
			contaFormatada = formataUnidade + formataProduto + formatIdentificacao;

		} else {
			String identificacao = String.valueOf(coIdentificacao).replace(PONTO, StringUtils.EMPTY).replace(TRACO,
					StringUtils.EMPTY);
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length()) + unidade;
			String formataProduto = REPLACE_CONTA_1.substring(produto.length()) + produto;
			identificacao = identificacao.replace(formataUnidade + formataProduto, StringUtils.EMPTY);
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length()) + identificacao;
			contaFormatada = formataUnidade + formataProduto + formatIdentificacao;
		}

		return contaFormatada;

	}
	*/

	public List<ContasOutputDTO> formataContaTotalLista(String dtInicio, String sgSistema, Object nuUnidade,
			Object nuProduto, Object coIdentificacao, List<ContasOutputDTO> contasAtendimento, String tipoPessoa) {

		String contaFormatada = null;
		
		/**
		 * este trecho de código foi descontinuado - Sprint 18 - IB 22484999
		 */
		/*
		if (CONTA_SIDEC.equalsIgnoreCase(sgSistema)) {
			String identificacao = String.valueOf(coIdentificacao).replace(PONTO, StringUtils.EMPTY).replace(TRACO, StringUtils.EMPTY);
			if (identificacao.length() > SETE_NUMBER) {
				String unidade = identificacao.substring(ZERO_NUMBER, QUATRO_NUMBER);
				String produto = identificacao.substring(QUATRO_NUMBER, SETE_NUMBER);
				if (!validaProdutoSIDEC(tipoPessoa, produto)) {
					identificacao = identificacao.replace(unidade + produto, StringUtils.EMPTY);
					identificacao = StringUtils.stripStart(identificacao, ZERO_CHAR);
					if (identificacao.length() >= UM_NUMBER) {
						String formataUnidade = REPLACE_CONTA_1.substring(unidade.length()) + unidade;
						String formataProduto = REPLACE_CONTA_1.substring(produto.length()) + produto;
						String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length()) + identificacao;
						contaFormatada = mascaraConta(formataUnidade + formataProduto + formatIdentificacao);
						ContasOutputDTO conta = new ContasOutputDTO();
						conta.setConta(contaFormatada);
						contasAtendimento.add(conta);
					}
				}
			}
		}
		*/

		
		if (CONTA_SID01.equalsIgnoreCase(sgSistema)) {
			String identificacao = String.valueOf(coIdentificacao).replace(PONTO, StringUtils.EMPTY).replace(TRACO, StringUtils.EMPTY);
			if (identificacao.length() > OITO_NUMBER) {
				String unidade = String.valueOf(nuUnidade);
				String produto = String.valueOf(nuProduto);
				if (!validaProdutoSID01(tipoPessoa, produto)) {
					String formataUnidade = REPLACE_CONTA_1.substring(unidade.length()) + unidade;
					String formataProduto = REPLACE_CONTA_1.substring(produto.length()) + produto;
					identificacao = identificacao.replace(formataUnidade + formataProduto, StringUtils.EMPTY);
					identificacao = StringUtils.stripStart(identificacao, ZERO_CHAR);
					if (identificacao.length() >= UM_NUMBER) {
						String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length()) + identificacao;
						contaFormatada = mascaraConta(formataUnidade + formataProduto + formatIdentificacao);
						ContasOutputDTO conta = new ContasOutputDTO();
						conta.setConta(contaFormatada);
						contasAtendimento.add(conta);
					}
				}
			}
		}
		
		return contasAtendimento;

	}

	private Boolean validaProdutoSID01(String tipoPessoa, String produto) {

		boolean produtoValido = false;

		if (PERSON_TYPE_PF.equalsIgnoreCase(tipoPessoa)
				&& !ArrayUtils.contains(ContaSID01PFEnum.codigos(), Integer.parseInt(produto))) {
			produtoValido = true;
		}

		if (PERSON_TYPE_PJ.equalsIgnoreCase(tipoPessoa)
				&& !ArrayUtils.contains(ContaSID01PJEnum.codigos(), Integer.parseInt(produto))) {
			produtoValido = true;
		}
		return produtoValido;
	}

	private Boolean validaProdutoSIDEC(String tipoPessoa, String produto) {

		boolean produtoValido = false;

		if (PERSON_TYPE_PF.equalsIgnoreCase(tipoPessoa)
				&& !ArrayUtils.contains(ContaSIDECPFEnum.codigos(), Integer.parseInt(produto))) {
			produtoValido = true;
		}

		if (PERSON_TYPE_PJ.equalsIgnoreCase(tipoPessoa)
				&& !ArrayUtils.contains(ContaSIDECPJEnum.codigos(), Integer.parseInt(produto))) {
			produtoValido = true;
		}
		return produtoValido;
	}

	private String mascaraConta(String contaInput) {
		String contaFormatada = null;
		MaskFormatter contaMask = null;

		if (contaInput != null && StringUtils.isNotBlank(contaInput)) {
			try {
				contaMask = new MaskFormatter("####.####.###############-#");
				contaMask.setValueContainsLiteralCharacters(false);
				contaFormatada = contaMask.valueToString(contaInput);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return contaFormatada;
	}

}
