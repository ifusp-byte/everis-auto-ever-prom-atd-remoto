package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ONZE;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PERSON_TYPE_PF;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PERSON_TYPE_PJ;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaEnvioNotaAnexoDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.dto.AuditoriaEnvioNotaDsLogPlataformaDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncEnviaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.repository.DocumentoClienteRepository;

@Component
@SuppressWarnings("all")
public class DocumentoUtils {

	private static Long PUBLICO_ALVO_PF = 1L;
	private static Long PUBLICO_ALVO_PJ = 2L;

	@Autowired
	DocumentoClienteRepository documentoClienteRepository;

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

	public static String formataCpfFront(Object object) {

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

	public static String formataCnpjFront(Object object) {

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

	public Long retornaPublicoAlvo(String cpfCnpj) {
		Long publicoAlvo = null;
		if (retornaCpf(cpfCnpj)) {
			publicoAlvo = PUBLICO_ALVO_PJ;
		} else {
			publicoAlvo = PUBLICO_ALVO_PF;
		}
		return publicoAlvo;
	}

	public Object anexosAuditoria(AuditoriaEnvioNotaDsLogPlataformaDTO dsLogPlataformaDTO,
			AuditoriaPncEnviaNotaInputDTO auditoriaPncEnviaNotaInputDTO, Long numeroNota) {

		List<AuditoriaEnvioNotaAnexoDTO> anexos = new ArrayList<>();
		List<Object[]> numeroNomeDocumento = documentoClienteRepository.findNomeDocumentoNomeAnexo(numeroNota);

		if (auditoriaPncEnviaNotaInputDTO == null) {
			if (!numeroNomeDocumento.isEmpty()) {
				dsLogPlataformaDTO.setPossuiAnexo("sim");
				numeroNomeDocumento.stream().forEach(documento -> {
					AuditoriaEnvioNotaAnexoDTO anexo = new AuditoriaEnvioNotaAnexoDTO();
					anexo.setCategoriaAnexo(String.valueOf(documento[0]));
					anexo.setNomeAnexo(String.valueOf(documento[1]));
					anexos.add(anexo);
				});
			} else {
				dsLogPlataformaDTO.setPossuiAnexo("não");
				AuditoriaEnvioNotaAnexoDTO anexo = new AuditoriaEnvioNotaAnexoDTO();
				anexos.add(anexo);
			}

			dsLogPlataformaDTO.setAnexos(anexos);

			return dsLogPlataformaDTO;
		}

		if (dsLogPlataformaDTO == null) {
			if (!numeroNomeDocumento.isEmpty()) {
				auditoriaPncEnviaNotaInputDTO.setPossuiAnexo("sim");
				numeroNomeDocumento.stream().forEach(documento -> {
					AuditoriaEnvioNotaAnexoDTO anexo = new AuditoriaEnvioNotaAnexoDTO();
					anexo.setCategoriaAnexo(String.valueOf(documento[0]));
					anexo.setNomeAnexo(String.valueOf(documento[1]));
					anexos.add(anexo);
				});
			} else {
				auditoriaPncEnviaNotaInputDTO.setPossuiAnexo("não");
				AuditoriaEnvioNotaAnexoDTO anexo = new AuditoriaEnvioNotaAnexoDTO();
				anexos.add(anexo);
			}

			auditoriaPncEnviaNotaInputDTO.setAnexos(anexos);

			return auditoriaPncEnviaNotaInputDTO;
		}

		return null;

	}

}
