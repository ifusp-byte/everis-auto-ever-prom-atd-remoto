package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ONZE;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
public class DocumentoUtils {
	
	public String formataCpf(Object object) {

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

	public String formataCnpj(Object object) {

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
		return cpfCnpj.replace(".", "").replace("-", "").replace("/", "").trim().length() == ONZE; 
	}

}
