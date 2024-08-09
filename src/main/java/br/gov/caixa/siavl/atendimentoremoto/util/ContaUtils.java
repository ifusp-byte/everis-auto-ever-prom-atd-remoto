package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SIART;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SID01;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SIDEC;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CONTA_SIIFX;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PONTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REPLACE_CONTA_1;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REPLACE_CONTA_2;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REPLACE_IDENTIFICACAO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TRACO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TRES_NUMER;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ZERO_CHAR;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ZERO_NUMER;

import java.text.ParseException;
import java.util.List;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.dto.ContasOutputDTO;

@Component
@SuppressWarnings("all")
public class ContaUtils {

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

	public List<ContasOutputDTO> formataContaTotalLista(String dtInicio, String sgSistema, Object nuUnidade,
			Object nuProduto, Object coIdentificacao, List<ContasOutputDTO> contasAtendimento) {

		String contaFormatada = null;

		if (CONTA_SIDEC.equalsIgnoreCase(sgSistema)) {
			String identificacao = String.valueOf(coIdentificacao).replace(PONTO, StringUtils.EMPTY).replace(TRACO, StringUtils.EMPTY);
			String unidade = String.valueOf(nuUnidade);
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length()) + unidade;
			String produto = String.valueOf(nuProduto);
			String formatProdutoReplace = REPLACE_CONTA_2.substring(produto.length()) + produto;

			if (identificacao.contains(formataUnidade + formatProdutoReplace)) {
				identificacao = identificacao.replace(formataUnidade + formatProdutoReplace, StringUtils.EMPTY);
			} else {
				identificacao = identificacao.replace(formataUnidade, StringUtils.EMPTY);
				produto = identificacao.substring(ZERO_NUMER, TRES_NUMER);
				identificacao = identificacao.replace(produto, StringUtils.EMPTY);
			}

			String formataProduto = REPLACE_CONTA_1.substring(produto.length()) + produto;
			identificacao = StringUtils.stripStart(identificacao, ZERO_CHAR);
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length()) + identificacao;
			contaFormatada = mascaraConta(formataUnidade + formataProduto + formatIdentificacao);
			ContasOutputDTO conta = new ContasOutputDTO();
			conta.setConta(contaFormatada);
			contasAtendimento.add(conta);
		}

		if (CONTA_SID01.equalsIgnoreCase(sgSistema)) {
			String identificacao = String.valueOf(coIdentificacao).replace(PONTO, StringUtils.EMPTY).replace(TRACO, StringUtils.EMPTY);
			String unidade = String.valueOf(nuUnidade);
			String produto = String.valueOf(nuProduto);
			String formataUnidade = REPLACE_CONTA_1.substring(unidade.length()) + unidade;
			String formataProduto = REPLACE_CONTA_1.substring(produto.length()) + produto;
			identificacao = identificacao.replace(formataUnidade + formataProduto, StringUtils.EMPTY);
			identificacao = StringUtils.stripStart(identificacao, ZERO_CHAR);
			String formatIdentificacao = REPLACE_IDENTIFICACAO.substring(identificacao.length()) + identificacao;
			contaFormatada = mascaraConta(formataUnidade + formataProduto + formatIdentificacao);
			ContasOutputDTO conta = new ContasOutputDTO();
			conta.setConta(contaFormatada);
			contasAtendimento.add(conta);
		}

		return contasAtendimento;

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
