package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ONZE;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PERSON_TYPE_PF;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PERSON_TYPE_PJ;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import org.springframework.stereotype.Component;

import lombok.experimental.UtilityClass;

@Component
@SuppressWarnings("all")
public class DocumentoUtils {

	public static String formataCpf(Object object) {

		String cpfInput = null;
		String formatCpf = null;
		String cpf = null;
		MaskFormatter cpfMask = null;

		if (object != null) {
			cpfInput = String.valueOf(object).replace(".", "").replace("/", "").replace("/", "").replace("-", "");
			formatCpf = "00000000000".substring(cpfInput.length()) + cpfInput;
			try {
				cpfMask = new MaskFormatter("###.###.###-##");
				cpfMask.setValueContainsLiteralCharacters(false);
				cpf = cpfMask.valueToString(formatCpf);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return cpf;
	}

	public static String formataCnpj(Object object) {

		String cnpjInput = null;
		String formatCnpj = null;
		String cnpj = null;
		MaskFormatter cnpjMask = null;

		if (object != null) {
			cnpjInput = String.valueOf(object).replace(".", "").replace("/", "").replace("/", "").replace("-", "");
			formatCnpj = "00000000000000".substring(cnpjInput.length()) + cnpjInput;
			try {
				cnpjMask = new MaskFormatter("##.###.###/####-##");
				cnpjMask.setValueContainsLiteralCharacters(false);
				cnpj = cnpjMask.valueToString(formatCnpj);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return cnpj;
	}

	public String formataDocumento(String documento) {
		return documento.replace(".", "").replace("-", "").replace("/", "").trim();
	}

	public boolean retornaCpf(String cpfCnpj) {
		return formataDocumento(cpfCnpj).length() == ONZE;
	}

	public String tipoPessoa(String cpfCnpj) {
		return retornaCpf(cpfCnpj) ? PERSON_TYPE_PF : PERSON_TYPE_PJ;
	}

}
